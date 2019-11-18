package com.cokreates.grp.ast.comacq.tempitem.util;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;

import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.util.constant.Table;
import com.google.common.collect.Lists;

import lombok.Synchronized;

public class GetQcReportQuery {

	@Synchronized
	public static ImmutablePair<String, Object[]> getTemporaryItemSql(AuthUser user, String oid) {
		
		String sql = "select qc_report_path "
			+ " from " + Table.SCHEMA_AST + Table.TEMP_ITEM
			+ " where 1 = 1 and oid = ? and office_oid = ?";
		
		List<Object> data = Lists.newArrayList(oid, user.getOfficeOid());
		
		Object[] aParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, aParam);
	}	

}

