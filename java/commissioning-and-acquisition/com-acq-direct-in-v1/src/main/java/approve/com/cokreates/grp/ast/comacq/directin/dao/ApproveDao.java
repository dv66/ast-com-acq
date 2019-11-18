package com.cokreates.grp.ast.comacq.directin.dao;

import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cokreates.grp.ast.comacq.directin.model.ApproveDirectInDetail;
import com.cokreates.grp.ast.comacq.directin.model.GetDirectIn;
import com.cokreates.grp.ast.comacq.directin.request.ApproveRequest;
import com.cokreates.grp.ast.comacq.directin.util.ApproveDirectInRowMapper;
import com.cokreates.grp.ast.comacq.directin.util.ApproveQuery;
import com.cokreates.grp.ast.comacq.directin.util.ApproveRowMapper;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.util.IdGenerator;
import com.cokreates.grp.util.constant.Constant;

@Repository("comAcqDirectInV1ApproveDao")
public class ApproveDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("idGenerator")
	private IdGenerator idGenerator;

	@Transactional
	public int updateDirectIn(AuthUser user, ApproveRequest request, List<ApproveDirectInDetail> list, GetDirectIn directIn) throws Exception {

		ImmutablePair<String, Object[]> query1 = ApproveQuery.updateDirectInSql(user, request);
		int result1 = jdbcTemplate.update(query1.getLeft(), query1.getRight());

		if (result1 != 1) {
			throw new Exception("No record updated into Direct In !!!");
		}
		
		if(directIn.getDirectInType().equals(Constant.DI_TYPE_IN))
		{
			for (ApproveDirectInDetail ti : list) {
				String uniqueAssetCode = getUniqueAssetId();
				String assetOid = idGenerator.generateId();
				
				ImmutablePair<String, Object[]> query2 = ApproveQuery.saveAssetInfoSql(user, ti.getItemOid(), assetOid, uniqueAssetCode, Constant.AI_STATUS_AVAILABLE);
				int result2 = jdbcTemplate.update(query2.getLeft(), query2.getRight());
	            if(result2 != 1) {
	            	throw new Exception("No record Asset Information inserted!!!");
	            }
	            
	            ImmutablePair<String, Object[]> query4 = ApproveQuery.saveAssetInfoDetailSql(idGenerator.generateId(), assetOid, ti);
				result2 = jdbcTemplate.update(query4.getLeft(), query4.getRight());
	            if(result2 != 1) {
	            	throw new Exception("No record Asset Information Detail inserted!!!");
	            }
	            
	            ImmutablePair<String, Object[]> query5 = ApproveQuery.updateDirectInDetailSql(ti.getOid(), assetOid, null);
				result2 = jdbcTemplate.update(query5.getLeft(), query5.getRight());
	            if(result2 != 1) {
	            	throw new Exception("No record Direct In Detail inserted!!!");
	            }
			}
			
		}
		else {
			
			for (ApproveDirectInDetail ti : list) {
				String uniqueAssetCode = getUniqueAssetId();
				String assetOid = idGenerator.generateId();
				
				ImmutablePair<String, Object[]> query2 = ApproveQuery.saveAssetInfoSql(user, ti.getItemOid(), assetOid, uniqueAssetCode, Constant.AI_STATUS_IN_USE);
				int result2 = jdbcTemplate.update(query2.getLeft(), query2.getRight());
	            if(result2 != 1) {
	            	throw new Exception("No record Asset Information inserted!!!");
	            }
	            
	            ImmutablePair<String, Object[]> query4 = ApproveQuery.saveAssetInfoDetailSql(idGenerator.generateId(), assetOid, ti);
				result2 = jdbcTemplate.update(query4.getLeft(), query4.getRight());
	            if(result2 != 1) {
	            	throw new Exception("No record Asset Information Detail inserted!!!");
	            }
	            
	            
	            String assetAllocOid = idGenerator.generateId();
	            String uniqueAllocCode = getUniqueAllocationId();
	            
	            ImmutablePair<String, Object[]> query6 = ApproveQuery.saveAssetAllocationSql(user, ti, assetAllocOid, uniqueAllocCode, Constant.AA_STATUS_FULLY_RECEIVED, directIn);
	    		int result3 = jdbcTemplate.update(query6.getLeft(), query6.getRight());

	    		if (result3 != 1) {
	    			throw new Exception("No record updated into Asset Allocation!!!");
	    		}
	    		
	    		ImmutablePair<String, Object[]> query7 = ApproveQuery.saveAssetAllocationDetailSql(ti, idGenerator.generateId(), assetAllocOid, assetOid);
				result3 = jdbcTemplate.update(query7.getLeft(), query7.getRight());
				if (result3 != 1) {
					throw new Exception("No record Asset alloc detail insert!!!");
				}
				
				ImmutablePair<String, Object[]> query5 = ApproveQuery.updateDirectInDetailSql(ti.getOid(), assetOid, assetAllocOid);
				result2 = jdbcTemplate.update(query5.getLeft(), query5.getRight());
	            if(result2 != 1) {
	            	throw new Exception("No record Direct In Detail inserted!!!");
	            }
			}
			
		}
		
		return result1;
	}
	
	 @Transactional(readOnly=true)
	    public List<ApproveDirectInDetail> getDirectInDetailByOid(String oid) {
	    	ImmutablePair<String, Object[]> query = ApproveQuery.getDirectInLineSql(oid);
	        List<ApproveDirectInDetail> result = jdbcTemplate.query(query.getLeft(), query.getRight(), new ApproveRowMapper());
	        return ListUtils.emptyIfNull(result);
	    }
	 
	 @Transactional(readOnly=true)
	    public GetDirectIn getDirectInByOid(String oid) {
	    	ImmutablePair<String, Object[]> query = ApproveQuery.getDirectInSql(oid);
	    	GetDirectIn result = jdbcTemplate.queryForObject(query.getLeft(), query.getRight(), new ApproveDirectInRowMapper());
	        return result;
	    }
	 
	 @Transactional
		public String getUniqueAssetId() throws Exception {
			String uniqueId = null;
			while (true) {
				uniqueId = "AST-" + idGenerator.uniqueId();
				ImmutablePair<String, Object[]> query = ApproveQuery.getAssetUniqueCode(uniqueId);
				long count = jdbcTemplate.queryForObject(query.getLeft(), query.getRight(), Long.class);
				if(count == 0) {
					break;
				}
			}
			return uniqueId;
		}
	 
		@Transactional
		public String getUniqueAllocationId() throws Exception {
			String uniqueId = null;
			while (true) {
				uniqueId = "AA-" + idGenerator.uniqueId();
				ImmutablePair<String, Object[]> query = ApproveQuery.getAllocationUniqueCode(uniqueId);
				long count = jdbcTemplate.queryForObject(query.getLeft(), query.getRight(), Long.class);
				if(count == 0) {
					break;
				}
			}
			return uniqueId;
		}


}
