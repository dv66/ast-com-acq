package com.cokreates.grp.ast.comacq.tempitem.dao;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cokreates.grp.ast.comacq.tempitem.model.DraftSubmitTempItem;
import com.cokreates.grp.ast.comacq.tempitem.model.DraftSubmitTempItemDetail;
import com.cokreates.grp.ast.comacq.tempitem.model.DraftSubmitWorkOrderDetail;
import com.cokreates.grp.ast.comacq.tempitem.response.GetByOidDraftSubmitResponseBody;
import com.cokreates.grp.ast.comacq.tempitem.util.DraftSubmitQuery;
import com.cokreates.grp.ast.comacq.tempitem.util.DraftSubmitWorkOrderRowMapper;
import com.cokreates.grp.ast.comacq.tempitem.util.GetByOidTemItemDraftSubmitRowMapper;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.util.IdGenerator;
import com.cokreates.grp.util.constant.Constant;

@Repository("comacqTempitemV1DraftSubmitDao")
public class DraftSubmitDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("idGenerator")
    private IdGenerator idGenerator;
    
    @Transactional
	public boolean checkReceivedQuantity(DraftSubmitTempItem request) throws Exception {

		List<DraftSubmitTempItemDetail> detail = request.getDraftSubmitTempItemDetail();
		
		for (DraftSubmitTempItemDetail ti : detail) {
			if (ti.getReceivedQuantity() <= 0) {
				return false;
			}
		}
		
		return true;
	}
    
    @Transactional(readOnly=true)
    public GetByOidDraftSubmitResponseBody getTemporaryItemByOid(String oid) {
    	ImmutablePair<String, Object[]> query = DraftSubmitQuery.getTemporaryItemSql(oid);
        return jdbcTemplate.queryForObject(query.getLeft(), query.getRight(), new GetByOidTemItemDraftSubmitRowMapper());
    }

    @Transactional
    public void draftSubmitTemporaryItemDetails(AuthUser user, DraftSubmitTempItem body, List<DraftSubmitTempItemDetail> tempItemDetails, String uniqueCode) throws Exception {
    	ImmutablePair<String, Object[]> query = DraftSubmitQuery.draftSubmitTemporaryItemSql(user, body, uniqueCode);
        int result = jdbcTemplate.update(query.getLeft(), query.getRight());
        if(result != 1) {
        	throw new Exception("No record Submitted into Temporary Item!!!");
        }
        
        query = DraftSubmitQuery.deleteTemporaryItemDetailsSql(body.getOid());
        jdbcTemplate.update(query.getLeft(), query.getRight());
        
    	for(DraftSubmitTempItemDetail ti : tempItemDetails) {
	    	ImmutablePair<String, Object[]> query2 = DraftSubmitQuery.saveTempItemDetailSql(ti, idGenerator.generateId(), body.getOid());
	        result = jdbcTemplate.update(query2.getLeft(), query2.getRight());
	        if(result != 1) {
	        	throw new Exception("No record submitted into Temporary Item Detail!!!");
	        }
    	}
    	
		if(body.getProcurementMethod().equals(Constant.TI_PRC_METHOD_DM))
		{
			ImmutablePair<String, Object[]> query3 = DraftSubmitQuery.getWorkOrderDetail(body);
			List<DraftSubmitWorkOrderDetail> workOrderDetail = jdbcTemplate.query(query3.getLeft(), query3.getRight(), new DraftSubmitWorkOrderRowMapper());

			for (DraftSubmitTempItemDetail ti : body.getDraftSubmitTempItemDetail()) {		
				for ( int i = 0; i< workOrderDetail.size(); i++ )
				{
					if(ti.getWorkOrderDetailOid().equals(workOrderDetail.get(i).getOid())) {
						int value = workOrderDetail.get(i).getPreviosulyReceivedQuantity() + ti.getReceivedQuantity();
						workOrderDetail.get(i).setPreviosulyReceivedQuantity(value);
						ImmutablePair<String, Object[]> query5 = DraftSubmitQuery.updateWorkOrderDetailSql(workOrderDetail.get(i).getPreviosulyReceivedQuantity(), workOrderDetail.get(i).getOid());
						int result5 = jdbcTemplate.update(query5.getLeft(), query5.getRight());
						if (result5 != 1) {
							throw new Exception("No record updated in Work Order Detail!!!");
						}
						break;
					}
				}
			}
			
			String status = Constant.WO_FULLY_RECEIVED;
			
			for ( int i = 0; i< workOrderDetail.size(); i++ ) {
				if(workOrderDetail.get(i).getOrderedQuantity() > workOrderDetail.get(i).getPreviosulyReceivedQuantity())
				{
					status = Constant.WO_PARTIALLY_RECEIVED;
					break;
				}
			}
			
			ImmutablePair<String, Object[]> query4 = DraftSubmitQuery.updateWorkOrderInfoSql(user, status, body.getWorkOrderOid());
	    	result = jdbcTemplate.update(query4.getLeft(), query4.getRight());
	        if(result != 1) {
	        	throw new Exception("No record updated into Work Order Info!!!");
	        }
			
		}
    }
    
    @Transactional
	public String getUniqueId() throws Exception {
		String uniqueId = null;
		while (true) {
			uniqueId = "TI-" + idGenerator.uniqueId();
			ImmutablePair<String, Object[]> query = DraftSubmitQuery.getAllUniqueCode(uniqueId);
			long count = jdbcTemplate.queryForObject(query.getLeft(), query.getRight(), Long.class);
			if(count == 0) {
				break;
			}
		}
		return uniqueId;
	}

}
