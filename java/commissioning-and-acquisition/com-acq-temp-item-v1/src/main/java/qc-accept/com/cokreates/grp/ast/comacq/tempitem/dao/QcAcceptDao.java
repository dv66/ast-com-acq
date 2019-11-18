package com.cokreates.grp.ast.comacq.tempitem.dao;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cokreates.grp.ast.comacq.tempitem.model.QcAcceptTempItemDetail;
import com.cokreates.grp.ast.comacq.tempitem.request.QcAcceptRequestBody;
import com.cokreates.grp.ast.comacq.tempitem.util.QcAcceptQuery;
import com.cokreates.grp.ast.comacq.tempitem.util.QcAcceptRowMapper;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.util.IdGenerator;

@Repository("comacqTempitemV1QcAcceptDao")
public class QcAcceptDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
    @Qualifier("idGenerator")
    private IdGenerator idGenerator;


	@Transactional
	public void updateAcceptedTemporaryItem(AuthUser user, QcAcceptRequestBody body, String status) throws Exception {
		ImmutablePair<String, Object[]> query = QcAcceptQuery.updateAcceptedTemporaryItemSql(user, body, status);
		int result = jdbcTemplate.update(query.getLeft(), query.getRight());
		if (result != 1) {
			throw new Exception("No record updated into Temporary Item!!!");
		}
		
		ImmutablePair<String, Object[]> query1 = QcAcceptQuery.getTemporaryItemDetailSql(body.getData().getOid());
        List<QcAcceptTempItemDetail> result1 = jdbcTemplate.query(query1.getLeft(), query1.getRight(), new QcAcceptRowMapper());
        
        for (QcAcceptTempItemDetail ti : result1) {
			if (Double.parseDouble(ti.getQualifiedQuantity()) != 0.0) {
				for( int i = 0; i < (int)Double.parseDouble(ti.getQualifiedQuantity()); i++ ) {
					String uniqueCode = getUniqueId();
					String assetOid = idGenerator.generateId();
					ImmutablePair<String, Object[]> query3 = QcAcceptQuery.saveAssetInfoSql(user, ti, assetOid, uniqueCode, body.getData().getOid());
					result = jdbcTemplate.update(query3.getLeft(), query3.getRight());
		            if(result != 1) {
		            	throw new Exception("No record Asset Information inserted!!!");
		            }
		            
		            ImmutablePair<String, Object[]> query4 = QcAcceptQuery.saveAssetInfoDetailSql(idGenerator.generateId(), assetOid);
					result = jdbcTemplate.update(query4.getLeft(), query4.getRight());
		            if(result != 1) {
		            	throw new Exception("No record Asset Information Detail inserted!!!");
		            }
				}				
			}
		}
	}
	
	@Transactional
	public void updateRejectedTemporaryItem(AuthUser user, QcAcceptRequestBody body, String status) throws Exception {
		ImmutablePair<String, Object[]> query = QcAcceptQuery.updateRejectedTemporaryItemSql(user, body, status);
		int result = jdbcTemplate.update(query.getLeft(), query.getRight());
		if (result != 1) {
			throw new Exception("No record updated into Temporary Item!!!");
		}
		
		
	}
	
	@Transactional
    public String checkTemporaryItemStatus(AuthUser user, String oid) throws Exception {
    	
    	ImmutablePair<String, Object[]> tempQuery = QcAcceptQuery.getTemporaryItemStatusSql(user, oid);
    	String tempStatus =jdbcTemplate.queryForObject(tempQuery.getLeft(),tempQuery.getRight(), String.class);
        return tempStatus;
    	
    }
	
	@Transactional
    public String getQualifiedQuantity(AuthUser user, String oid) throws Exception {
    	
    	ImmutablePair<String, Object[]> tempQuery = QcAcceptQuery.getQualifiedQuantity(user, oid);
    	String qualifiedQuantitySum =jdbcTemplate.queryForObject(tempQuery.getLeft(),tempQuery.getRight(), String.class);
        
    	return qualifiedQuantitySum;
    	
    }
	
	@Transactional
	public String getUniqueId() throws Exception {
		String uniqueId = null;
		while (true) {
			uniqueId = "AST-" + idGenerator.uniqueId();
			ImmutablePair<String, Object[]> query = QcAcceptQuery.getAllUniqueCode(uniqueId);
			long count = jdbcTemplate.queryForObject(query.getLeft(), query.getRight(), Long.class);
			if(count == 0) {
				break;
			}
		}
		return uniqueId;
	}

}
