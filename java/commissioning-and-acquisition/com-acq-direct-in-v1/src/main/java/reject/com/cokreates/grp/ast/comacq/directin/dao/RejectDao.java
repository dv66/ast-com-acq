package com.cokreates.grp.ast.comacq.directin.dao;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cokreates.grp.ast.comacq.directin.request.RejectRequest;
import com.cokreates.grp.ast.comacq.directin.util.RejectQuery;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.util.IdGenerator;

@Repository("comAcqDirectInV1RejectDao")
public class RejectDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("idGenerator")
	private IdGenerator idGenerator;

	@Transactional
	public int updateDirectIn(AuthUser user, RejectRequest request) throws Exception {

		ImmutablePair<String, Object[]> query1 = RejectQuery.updateDirectInSql(user, request);
		int result1 = jdbcTemplate.update(query1.getLeft(), query1.getRight());

		if (result1 != 1) {
			throw new Exception("No record updated into Direct In !!!");
		}
		
  
		return result1;
	}


}
