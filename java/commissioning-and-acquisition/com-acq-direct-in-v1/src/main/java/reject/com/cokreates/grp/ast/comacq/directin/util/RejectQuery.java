package com.cokreates.grp.ast.comacq.directin.util;

import java.util.List;


import org.apache.commons.lang3.tuple.ImmutablePair;
import org.joda.time.DateTime;


import com.cokreates.grp.ast.comacq.directin.request.RejectRequest;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.util.constant.Constant;
import com.cokreates.grp.util.constant.Table;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import lombok.Synchronized;

public class RejectQuery {
	
	@Synchronized
	public static ImmutablePair<String, Object[]> updateDirectInSql(AuthUser user, RejectRequest request) {
		
		ImmutablePair<String, String> now = Constant.getDateTime(new DateTime());
		List<String> cols = Lists.newArrayList("status = ?",  "decision_by = ?", "decision_on = " + now.getLeft()
				+ ", updated_by = ?" , "updated_on = " + now.getLeft());
		
		List<Object> data = Lists.newArrayList(Constant.DI_REJECTED, user.getUserOid(),now.getRight(), user.getUserOid(), now.getRight());
		
		
		String sCols = Joiner.on(",").join(cols);
		String sql = "update " + Table.SCHEMA_AST + Table.DIRECT_IN + " set " + sCols + " where 1 = 1 and oid = ? and office_oid = ? and status = ? ";
		data.add(request.getBody().getData().getOid());
		data.add(user.getOfficeOid());
		data.add(Constant.DI_PENDING);
		
		Object[] queryParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, queryParam);
	}

}
