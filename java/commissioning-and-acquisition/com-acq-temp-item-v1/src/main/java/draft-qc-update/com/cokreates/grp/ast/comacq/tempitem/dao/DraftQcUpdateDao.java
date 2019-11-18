package com.cokreates.grp.ast.comacq.tempitem.dao;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cokreates.grp.ast.comacq.tempitem.model.DraftTemporaryItemDetail;
import com.cokreates.grp.ast.comacq.tempitem.request.DraftQcUpdateRequestBody;
import com.cokreates.grp.ast.comacq.tempitem.response.GetByOidDraftQcUpdateResponseBody;
import com.cokreates.grp.ast.comacq.tempitem.util.DraftQcUpdateQuery;
import com.cokreates.grp.ast.comacq.tempitem.util.GetByOidDraftQcUpdateRowMapper;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.util.constant.Constant;

@Repository("comacqTempitemV1DraftQcUpdateDao")
public class DraftQcUpdateDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional(readOnly = true)
	public boolean checkStatus(String oid) throws Exception {
		ImmutablePair<String, Object[]> tempQuery = DraftQcUpdateQuery.getTemporaryItemSql(oid);
    	String tempStatus =jdbcTemplate.queryForObject(tempQuery.getLeft(),tempQuery.getRight(), String.class);
    	if((tempStatus.equals(Constant.TI_STATUS_QC_ONGOING)) || (tempStatus.equals(Constant.TI_STATUS_READY_FOR_QC)) || (tempStatus.equals(Constant.TI_STATUS_RE_QC))) {
    		return true;
    	}
		return false;
	}
	
	@Transactional(readOnly = true)
	public boolean isAvailable(List<DraftTemporaryItemDetail> tempItemDetails) throws Exception {
		for (DraftTemporaryItemDetail ti : tempItemDetails) {
			ImmutablePair<String, Object[]> query = DraftQcUpdateQuery.getTemporaryItemDetailsSql(ti.getOid());
			GetByOidDraftQcUpdateResponseBody recQuan = jdbcTemplate.queryForObject(query.getLeft(), query.getRight(), new GetByOidDraftQcUpdateRowMapper());
			int total = ti.getDisqualifiedQuantity() + ti.getQualifiedQuantity();
			if (recQuan.getReceivedQuantity() != total) {
				return false;
			}
		}
		return true;
	}

	@Transactional
	public void updateTemporaryItemDetails(AuthUser user, DraftQcUpdateRequestBody body, List<DraftTemporaryItemDetail> tempItemDetails) throws Exception {
		ImmutablePair<String, Object[]> query = DraftQcUpdateQuery.updateTemporaryItemSql(user, body);
		int result = jdbcTemplate.update(query.getLeft(), query.getRight());
		if (result != 1) {
			throw new Exception("No record updated into Temporary Item!!!");
		}
		for (DraftTemporaryItemDetail ti : tempItemDetails) {
			ImmutablePair<String, Object[]> query2 = DraftQcUpdateQuery.updateTemporaryItemDetailsSql(ti);
			result = jdbcTemplate.update(query2.getLeft(), query2.getRight());
			if (result != 1) {
				throw new Exception("No record updated into Temporary Item Detail!!!");
			}
		}
	}

}
