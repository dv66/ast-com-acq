package com.cokreates.grp.ast.comacq.directin.dao;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cokreates.grp.ast.comacq.directin.model.SaveDirectInDetail;
import com.cokreates.grp.ast.comacq.directin.request.SaveRequest;
import com.cokreates.grp.ast.comacq.directin.util.SaveQuery;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.util.IdGenerator;

@Repository("comAcqDirectInV1SaveDao")
public class SaveDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("idGenerator")
	private IdGenerator idGenerator;

	@Transactional
	public int saveDirectIn(AuthUser user, SaveRequest request, String uniqueId) throws Exception {

		String directInOid = idGenerator.generateId();

		ImmutablePair<String, Object[]> query1 = SaveQuery.saveDirectInSql(user, request, directInOid, uniqueId);
		int result1 = jdbcTemplate.update(query1.getLeft(), query1.getRight());

		if (result1 != 1) {
			throw new Exception("No record updated into Direct In !!!");
		}
		
		for (SaveDirectInDetail ti : request.getBody().getData().getSaveDirectInDetail()) {
			ImmutablePair<String, Object[]> query2 = SaveQuery.saveDirectInDetailSql(ti, idGenerator.generateId(), directInOid);
			int result2 = jdbcTemplate.update(query2.getLeft(), query2.getRight());
			if (result2 != 1) {
				throw new Exception("No record Direct In Detail inserted!!!");
			}
			
		}
  
		return result1;
	}

	@Transactional
	public String getUniqueId() throws Exception {
		String uniqueId = null;
		while (true) {
			uniqueId = "DI-" + idGenerator.uniqueId();
			ImmutablePair<String, Object[]> query = SaveQuery.getAllUniqueCode(uniqueId);
			long count = jdbcTemplate.queryForObject(query.getLeft(), query.getRight(), Long.class);
			if(count == 0) {
				break;
			}
		}
		return uniqueId;
	}

}
