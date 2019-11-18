package com.cokreates.grp.ast.comacq.tempitem.dao;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cokreates.grp.ast.comacq.tempitem.model.SaveTempItemDetail;
import com.cokreates.grp.ast.comacq.tempitem.model.WorkOrderDetail;
import com.cokreates.grp.ast.comacq.tempitem.request.SaveRequest;
import com.cokreates.grp.ast.comacq.tempitem.util.SaveQuery;
import com.cokreates.grp.ast.comacq.tempitem.util.WorkOrderRowMapper;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.util.IdGenerator;
import com.cokreates.grp.util.constant.Constant;

@Repository("comAcqTempItemV1SaveDao")
public class SaveDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("idGenerator")
	private IdGenerator idGenerator;

	@Transactional
	public boolean checkReceivedQuantity(SaveRequest request) throws Exception {

		List<SaveTempItemDetail> detail = request.getBody().getData().getSaveTempItemDetail();

		for (SaveTempItemDetail ti : detail) {
			if (ti.getReceivedQuantity() <= 0) {
				return false;
			}
		}

		return true;
	}

	@Transactional
	public int saveTemporaryItem(AuthUser user, SaveRequest request, String uniqueId) throws Exception {

		String tempItemOid = idGenerator.generateId();

		ImmutablePair<String, Object[]> query1 = SaveQuery.saveTemporaryItemSql(user, request, tempItemOid, uniqueId);
		int result1 = jdbcTemplate.update(query1.getLeft(), query1.getRight());

		if (result1 != 1) {
			throw new Exception("No record updated into Temporary Item Detail!!!");
		}
		
		for (SaveTempItemDetail ti : request.getBody().getData().getSaveTempItemDetail()) {
			ImmutablePair<String, Object[]> query2 = SaveQuery.saveTempItemDetailSql(ti, idGenerator.generateId(), tempItemOid);
			int result2 = jdbcTemplate.update(query2.getLeft(), query2.getRight());
			if (result2 != 1) {
				throw new Exception("No record insert!!!");
			}
			
		}
		
		if(request.getBody().getData().getProcurementMethod().equals(Constant.TI_PRC_METHOD_DM))
		{
			ImmutablePair<String, Object[]> query3 = SaveQuery.getWorkOrderDetail(request);
			List<WorkOrderDetail> workOrderDetail = jdbcTemplate.query(query3.getLeft(), query3.getRight(), new WorkOrderRowMapper());

			for (SaveTempItemDetail ti : request.getBody().getData().getSaveTempItemDetail()) {		
				for ( int i = 0; i< workOrderDetail.size(); i++ )
				{
					if(ti.getWorkOrderDetailOid().equals(workOrderDetail.get(i).getOid())) {
						int value = workOrderDetail.get(i).getPreviosulyReceivedQuantity() + ti.getReceivedQuantity();
						workOrderDetail.get(i).setPreviosulyReceivedQuantity(value);
						ImmutablePair<String, Object[]> query5 = SaveQuery.updateWorkOrderDetailSql(workOrderDetail.get(i).getPreviosulyReceivedQuantity(), workOrderDetail.get(i).getOid());
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
			
			ImmutablePair<String, Object[]> query4 = SaveQuery.updateWorkOrderInfoSql(user, status, request.getBody().getData().getWorkOrderOid());
	    	int result = jdbcTemplate.update(query4.getLeft(), query4.getRight());
	        if(result != 1) {
	        	throw new Exception("No record updated into Work Order Info!!!");
	        }
			
		}
  
		return result1;
	}

	@Transactional
	public String getUniqueId() throws Exception {
		String uniqueId = null;
		while (true) {
			uniqueId = "TI-" + idGenerator.uniqueId();
			ImmutablePair<String, Object[]> query = SaveQuery.getAllUniqueCode(uniqueId);
			long count = jdbcTemplate.queryForObject(query.getLeft(), query.getRight(), Long.class);
			if(count == 0) {
				break;
			}
		}
		return uniqueId;
	}

}
