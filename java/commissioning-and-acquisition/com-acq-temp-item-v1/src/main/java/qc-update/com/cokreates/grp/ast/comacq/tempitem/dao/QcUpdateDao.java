package com.cokreates.grp.ast.comacq.tempitem.dao;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cokreates.grp.ast.comacq.tempitem.model.QcTemporaryItemDetail;
import com.cokreates.grp.ast.comacq.tempitem.request.QcUpdateRequestBody;
import com.cokreates.grp.ast.comacq.tempitem.response.GetByOidQcUpdateResponseBody;
import com.cokreates.grp.ast.comacq.tempitem.util.GetByOidQcUpdateRowMapper;
import com.cokreates.grp.ast.comacq.tempitem.util.QcUpdateQuery;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.util.IdGenerator;

@Repository("comacqTempitemV1QcUpdateDao")
public class QcUpdateDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
    @Qualifier("idGenerator")
    private IdGenerator idGenerator;

	@Transactional(readOnly = true)
	public boolean isAvailable(List<QcTemporaryItemDetail> tempItemDetails) throws Exception {
		for (QcTemporaryItemDetail ti : tempItemDetails) {
			ImmutablePair<String, Object[]> query = QcUpdateQuery.getTemporaryItemSql(ti.getOid());
			GetByOidQcUpdateResponseBody recQuan = jdbcTemplate.queryForObject(query.getLeft(), query.getRight(), new GetByOidQcUpdateRowMapper());
			int total = ti.getDisqualifiedQuantity() + ti.getQualifiedQuantity();
			if (recQuan.getReceivedQuantity() != total) {
				return false;
			}
		}
		return true;
	}

	@Transactional
	public void updateTemporaryItemDetails(AuthUser user, QcUpdateRequestBody body, List<QcTemporaryItemDetail> tempItemDetails, String status) throws Exception {
		ImmutablePair<String, Object[]> query = QcUpdateQuery.updateTemporaryItemSql(user, body, status);
		int result = jdbcTemplate.update(query.getLeft(), query.getRight());
		if (result != 1) {
			throw new Exception("No record updated into Temporary Item!!!");
		}
		for (QcTemporaryItemDetail ti : tempItemDetails) {
			ImmutablePair<String, Object[]> query2 = QcUpdateQuery.updateTemporaryItemDetailsSql(ti);
			result = jdbcTemplate.update(query2.getLeft(), query2.getRight());
			if (result != 1) {
				throw new Exception("No record updated into Temporary Item Detail!!!");
			}
		}
	}
	
	@Transactional
    public String checkTemporaryItemStatus(AuthUser user, String oid) throws Exception {
    	
    	ImmutablePair<String, Object[]> tempQuery = QcUpdateQuery.getTemporaryItemStatusSql(user, oid);
    	String tempStatus =jdbcTemplate.queryForObject(tempQuery.getLeft(),tempQuery.getRight(), String.class);
        return tempStatus;
    	
    }

}
