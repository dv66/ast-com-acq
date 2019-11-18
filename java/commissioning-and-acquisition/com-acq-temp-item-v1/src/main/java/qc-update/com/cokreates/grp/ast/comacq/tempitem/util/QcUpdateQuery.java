package com.cokreates.grp.ast.comacq.tempitem.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.joda.time.DateTime;

import com.cokreates.grp.ast.comacq.tempitem.model.QcTemporaryItemDetail;
import com.cokreates.grp.ast.comacq.tempitem.request.QcUpdateRequestBody;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.util.constant.Constant;
import com.cokreates.grp.util.constant.Table;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import lombok.Synchronized;

public class QcUpdateQuery {

	@Synchronized
	public static ImmutablePair<String, Object[]> updateTemporaryItemSql(AuthUser user, QcUpdateRequestBody body, String status) {

		ImmutablePair<String, String> now = Constant.getDateTime(new DateTime());
		List<String> cols = Lists.newArrayList("status = ?", "updated_by = ?", "updated_on = " + now.getLeft(), "qc_by = ?", "qc_on = " + now.getLeft());
		List<Object> data = Lists.newArrayList(status, user.getUserOid(), now.getRight(), user.getUserOid(), now.getRight());

		String sCols = Joiner.on(",").join(cols);
		String sql = "update " + Table.SCHEMA_AST + Table.TEMP_ITEM + " set " + sCols + " where 1 = 1 and oid = ? and office_oid = ?";
		data.add(body.getData().getOid());
		data.add(user.getOfficeOid());
		
		Object[] queryParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, queryParam);
	}

	@Synchronized
	public static ImmutablePair<String, Object[]> updateTemporaryItemDetailsSql(QcTemporaryItemDetail tempItemDetails) {

		List<String> cols = Lists.newArrayList("qualified_quantity = ?", "disqualified_quantity = ?");
		List<Object> data = Lists.newArrayList(tempItemDetails.getQualifiedQuantity(),
			tempItemDetails.getDisqualifiedQuantity());

		if (StringUtils.isNotBlank(tempItemDetails.getRemarks())) {
			cols.add("remarks = ?");
			data.add(tempItemDetails.getRemarks());
		}

		String sCols = Joiner.on(",").join(cols);
		String sql = "update " + Table.SCHEMA_AST + Table.TEMP_ITEM_DETAILS + " set " + sCols + " where 1 = 1 and oid = ?";
		data.add(tempItemDetails.getOid());
		
		Object[] queryParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, queryParam);
	}
	
	
	@Synchronized
	public static ImmutablePair<String, Object[]> getTemporaryItemSql(String oid) {
		
		String sql = "select received_quantity "
			+ " from " + Table.SCHEMA_AST + Table.TEMP_ITEM_DETAILS
			+ " where 1 = 1 and oid = ?";
		
		List<Object> data = Lists.newArrayList(oid);
		
		Object[] aParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, aParam);
	}
	
	
	
	@Synchronized
	public static ImmutablePair<String, Object[]> getTemporaryItemStatusSql(AuthUser user, String oid) {
		
		String sql = "select t.status as Status"
				+ " from " + Table.SCHEMA_AST + Table.TEMP_ITEM
				+ " t where 1 = 1 and t.oid = ? and t.office_oid = ?";
			
			List<Object> data = Lists.newArrayList(oid, user.getOfficeOid());
			
			Object[] aParam = data.toArray(new Object[data.size()]);
			return new ImmutablePair<String, Object[]>(sql, aParam);
		
	}
	

}
