package com.cokreates.grp.ast.comacq.tempitem.dao;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cokreates.grp.ast.comacq.tempitem.request.AddQcReportRequestBody;
import com.cokreates.grp.ast.comacq.tempitem.util.QcReportQuery;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.util.IdGenerator;

@Repository("comacqTempitemV1QcReportDao")
public class QcReportDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
    @Qualifier("idGenerator")
    private IdGenerator idGenerator;

	@Transactional
	public void updateTemporaryItem(AuthUser user, AddQcReportRequestBody body, String fileName) throws Exception {
		ImmutablePair<String, Object[]> query = QcReportQuery.updateTemporaryItemSql(user, body, fileName);
		int result = jdbcTemplate.update(query.getLeft(), query.getRight());
		if (result != 1) {
			throw new Exception("No record updated into Temporary Item!!!");
		}
		
		
	}
	


}
