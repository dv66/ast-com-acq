package com.cokreates.grp.ast.comacq.directout.dao;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cokreates.grp.ast.comacq.directout.model.SaveDirectOutDetail;
import com.cokreates.grp.ast.comacq.directout.request.SaveRequest;
import com.cokreates.grp.ast.comacq.directout.util.SaveQuery;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.util.IdGenerator;

@Repository("comAcqDirectOutV1SaveDao")
public class SaveDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("idGenerator")
	private IdGenerator idGenerator;

	@Transactional
	public int saveDirectOut(AuthUser user, SaveRequest request, String uniqueId) throws Exception {

		String directOutOid = idGenerator.generateId();

		ImmutablePair<String, Object[]> query1 = SaveQuery.saveDirectOutSql(user, request, directOutOid, uniqueId);
		int result1 = jdbcTemplate.update(query1.getLeft(), query1.getRight());

		if (result1 != 1) {
			throw new Exception("No record updated into Direct Out !!!");
		}
		
		for (SaveDirectOutDetail ti : request.getBody().getData().getSaveDirectOutDetail()) {
			ImmutablePair<String, Object[]> query2 = SaveQuery.saveDirectOutDetailSql(ti, idGenerator.generateId(), directOutOid);
			int result2 = jdbcTemplate.update(query2.getLeft(), query2.getRight());
			if (result2 != 1) {
				throw new Exception("No record Direct Out Detail inserted!!!");
			}
			
			ImmutablePair<String, Object[]> query3 = SaveQuery.updateAssetInfoSql(user, ti.getAssetOid());
			int result3 = jdbcTemplate.update(query3.getLeft(), query3.getRight());
			if (result3 != 1) {
				throw new Exception("Asset not updated!!!");
			}
			
		}
  
		return result1;
	}

	@Transactional
	public String getUniqueId() throws Exception {
		String uniqueId = null;
		while (true) {
			uniqueId = "DO-" + idGenerator.uniqueId();
			ImmutablePair<String, Object[]> query = SaveQuery.getAllUniqueCode(uniqueId);
			long count = jdbcTemplate.queryForObject(query.getLeft(), query.getRight(), Long.class);
			if(count == 0) {
				break;
			}
		}
		return uniqueId;
	}

}
