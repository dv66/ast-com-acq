package com.cokreates.grp.ast.comacq.directout.dao;

import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cokreates.grp.ast.comacq.directout.model.ApproveDirectOut;
import com.cokreates.grp.ast.comacq.directout.model.ApproveDirectOutDetail;
import com.cokreates.grp.ast.comacq.directout.request.ApproveRequest;
import com.cokreates.grp.ast.comacq.directout.util.ApproveDetailRowMapper;
import com.cokreates.grp.ast.comacq.directout.util.ApproveQuery;
import com.cokreates.grp.ast.comacq.directout.util.ApproveRowMapper;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.util.IdGenerator;
import com.cokreates.grp.util.constant.Constant;

@Repository("comAcqDirectOutV1ApproveDao")
public class ApproveDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("idGenerator")
	private IdGenerator idGenerator;

	@Transactional
	public int updateDirectOut(AuthUser user, ApproveRequest request, List<ApproveDirectOutDetail> list, ApproveDirectOut body) throws Exception {

		String assetAllocOid = idGenerator.generateId();
        String uniqueAllocCode = getUniqueAllocationId();
        
        ImmutablePair<String, Object[]> query6 = ApproveQuery.saveAssetAllocationSql(user, body, assetAllocOid, uniqueAllocCode, Constant.AA_STATUS_FULLY_RECEIVED);
		int result3 = jdbcTemplate.update(query6.getLeft(), query6.getRight());

		if (result3 != 1) {
			throw new Exception("No record updated into Asset Allocation!!!");
		}
		
		ImmutablePair<String, Object[]> query1 = ApproveQuery.updateDirectOutSql(user, request, assetAllocOid);
		int result1 = jdbcTemplate.update(query1.getLeft(), query1.getRight());

		if (result1 != 1) {
			throw new Exception("No record updated into Direct In !!!");
		}
		
		for (ApproveDirectOutDetail ti : list) {
	    		
    		ImmutablePair<String, Object[]> query7 = ApproveQuery.saveAssetAllocationDetailSql(ti, idGenerator.generateId(), assetAllocOid, ti.getAssetOid());
			result3 = jdbcTemplate.update(query7.getLeft(), query7.getRight());
			if (result3 != 1) {
				throw new Exception("No record Asset alloc detail insert!!!");
			}
			
			ImmutablePair<String, Object[]> query8 = ApproveQuery.updateAssetInfoSql(user, ti.getAssetOid());
			result3 = jdbcTemplate.update(query8.getLeft(), query8.getRight());
			if (result3 != 1) {
				throw new Exception("No record Asset info updated!!!");
			}

		}
		
		return result1;
	}
	
	 @Transactional(readOnly=true)
	    public List<ApproveDirectOutDetail> getDirectOutDetailByOid(String oid) {
	    	ImmutablePair<String, Object[]> query = ApproveQuery.getDirectOutDetailSql(oid);
	        List<ApproveDirectOutDetail> result = jdbcTemplate.query(query.getLeft(), query.getRight(), new ApproveDetailRowMapper());
	        return ListUtils.emptyIfNull(result);
	    }
	 
	 @Transactional(readOnly=true)
	    public ApproveDirectOut getDirectOutByOid(String oid) {
	    	ImmutablePair<String, Object[]> query = ApproveQuery.getDirectOutSql(oid);
	        return jdbcTemplate.queryForObject(query.getLeft(), query.getRight(), new ApproveRowMapper());
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
