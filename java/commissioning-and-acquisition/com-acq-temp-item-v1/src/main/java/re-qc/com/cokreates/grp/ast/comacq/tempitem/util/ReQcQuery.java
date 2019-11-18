package com.cokreates.grp.ast.comacq.tempitem.util;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.joda.time.DateTime;

import com.cokreates.grp.ast.comacq.tempitem.model.ReQc;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.util.constant.Constant;
import com.cokreates.grp.util.constant.Table;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import lombok.Synchronized;

public class ReQcQuery {

	@Synchronized
	public static ImmutablePair<String, Object[]> updateTemporaryItemSql(AuthUser user, ReQc body) {
		
		ImmutablePair<String, String> now = Constant.getDateTime(new DateTime());
		List<String> cols = Lists.newArrayList("status = ?", "decision_on_qc_note = ?", "decision_on_qc_by = ?", "decision_on_qc_date = " + now.getLeft()
				+ ", updated_by = ?" , "updated_on = " + now.getLeft());
		
		List<Object> data = Lists.newArrayList(Constant.TI_STATUS_RE_QC, body.getDecisionOnQcNote(), user.getUserOid(),now.getRight(), user.getUserOid(), now.getRight());
		
		
		String sCols = Joiner.on(",").join(cols);
		String sql = "update " + Table.SCHEMA_AST + Table.TEMP_ITEM + " set " + sCols + " where 1 = 1 and oid = ? and office_oid = ?";
		data.add(body.getOid());
		data.add(user.getOfficeOid());
		
		Object[] queryParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, queryParam);
	}

	
	
	@Synchronized
	public static ImmutablePair<String, Object[]> getTemporaryItemStatusSql(AuthUser user, String oid) {
		
//		String sql = "select t.status as Status"
//				+ " from " + Table.SCHEMA_AST + Table.TEMP_ITEM
//				+ " t where 1 = 1 and t.oid = ? and t.office_oid = ?";
		
		String sql =  "select count(*) from " + Table.SCHEMA_AST + Table.TEMP_ITEM 
				+ " t where 1 = 1 and t.oid = ? and t.office_oid = ? and t.status = ?";
			
		List<Object> data = Lists.newArrayList(oid, user.getOfficeOid(), Constant.TI_STATUS_QC_DONE);
			
		Object[] aParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, aParam);
		
	}

}
