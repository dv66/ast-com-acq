package com.cokreates.grp.ast.comacq.directout.dao;

import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cokreates.grp.ast.comacq.directout.model.RejectDirectOutDetail;
import com.cokreates.grp.ast.comacq.directout.request.RejectRequest;
import com.cokreates.grp.ast.comacq.directout.util.RejectQuery;
import com.cokreates.grp.ast.comacq.directout.util.RejectRowMapper;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.util.IdGenerator;

@Repository("comAcqDirectOutV1RejectDao")
public class RejectDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("idGenerator")
	private IdGenerator idGenerator;

	@Transactional
	public int updateDirectOut(AuthUser user, RejectRequest request, List<RejectDirectOutDetail> body) throws Exception {

		ImmutablePair<String, Object[]> query1 = RejectQuery.updateDirectOutSql(user, request);
		int result1 = jdbcTemplate.update(query1.getLeft(), query1.getRight());

		if (result1 != 1) {
			throw new Exception("No record updated into Direct Out !!!");
		}
		
		for (RejectDirectOutDetail ti : body) {
			
			ImmutablePair<String, Object[]> query8 = RejectQuery.updateAssetInfoSql(user, ti.getAssetOid());
			int result3 = jdbcTemplate.update(query8.getLeft(), query8.getRight());
			if (result3 != 1) {
				throw new Exception("No record Asset info updated!!!");
			}

		}
		
		return result1;
	}
	
	 @Transactional(readOnly=true)
	    public List<RejectDirectOutDetail> getDirectOutDetailByOid(String oid) {
	    	ImmutablePair<String, Object[]> query = RejectQuery.getDirectOutDetailSql(oid);
	        List<RejectDirectOutDetail> result = jdbcTemplate.query(query.getLeft(), query.getRight(), new RejectRowMapper());
	        return ListUtils.emptyIfNull(result);
	    }


}
