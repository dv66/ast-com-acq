package com.cokreates.grp.ast.comacq.tempitem.util;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.joda.time.DateTime;

import com.cokreates.grp.ast.comacq.tempitem.model.QcAcceptTempItemDetail;
import com.cokreates.grp.ast.comacq.tempitem.request.QcAcceptRequestBody;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.util.constant.Constant;
import com.cokreates.grp.util.constant.Table;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import lombok.Synchronized;

public class QcAcceptQuery {

	@Synchronized
	public static ImmutablePair<String, Object[]> updateAcceptedTemporaryItemSql(AuthUser user, QcAcceptRequestBody body, String status) {

		ImmutablePair<String, String> now = Constant.getDateTime(new DateTime());
		List<String> cols = Lists.newArrayList("status = ?", "updated_by = ?", "updated_on = " + now.getLeft(), "decision_on_qc_note = ?", "decision_on_qc_by = ?", "decision_on_qc_date = " + now.getLeft());
		List<Object> data = Lists.newArrayList(status, user.getUserOid(), now.getRight(),body.getData().getNote(), user.getUserOid(), now.getRight());

		String sCols = Joiner.on(",").join(cols);
		String sql = "update " + Table.SCHEMA_AST + Table.TEMP_ITEM + " set " + sCols + " where 1 = 1 and oid = ? and office_oid = ?";
		data.add(body.getData().getOid());
		data.add(user.getOfficeOid());
		
		Object[] queryParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, queryParam);
	}
	
	@Synchronized
	public static ImmutablePair<String, Object[]> updateRejectedTemporaryItemSql(AuthUser user, QcAcceptRequestBody body, String status) {

		ImmutablePair<String, String> now = Constant.getDateTime(new DateTime());
		List<String> cols = Lists.newArrayList("status = ?", "updated_by = ?", "updated_on = " + now.getLeft(), "decision_on_qc_note = ?", "decision_on_qc_by = ?", "decision_on_qc_date = " + now.getLeft());
		List<Object> data = Lists.newArrayList(status, user.getUserOid(), now.getRight(),body.getData().getNote(), user.getUserOid(), now.getRight());

		String sCols = Joiner.on(",").join(cols);
		String sql = "update " + Table.SCHEMA_AST + Table.TEMP_ITEM + " set " + sCols + " where 1 = 1 and oid = ? and office_oid = ?";
		data.add(body.getData().getOid());
		data.add(user.getOfficeOid());
		
		Object[] queryParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, queryParam);
	}
	
	
	
	@Synchronized
	public static ImmutablePair<String, Object[]> saveAssetInfoSql(AuthUser user, QcAcceptTempItemDetail tid, String oid, String code, String temporaryItemOid) {

		ImmutablePair<String, String> now = Constant.getDateTime(new DateTime());
		
		List<String> cols = Lists.newArrayList("oid", "code", "status", "temporary_item_oid", "item_oid", "created_by", "created_on", "office_oid");
		List<String> param = Lists.newArrayList("?", "?", "?", "?", "?", "?", now.getLeft(), "?");
		List<Object> data = Lists.newArrayList(oid, code, Constant.AI_STATUS_DRAFT, temporaryItemOid, tid.getItemOid(), user.getUserOid(), now.getRight(), user.getOfficeOid());
		

		String sCols = Joiner.on(",").join(cols);
		String sParam = Joiner.on(",").join(param);
		String sql = "insert into " + Table.SCHEMA_AST + Table.ASSET_INFO + " (" + sCols + ") values (" + sParam + ")";

		Object[] aParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, aParam);
	}
	
	@Synchronized
	public static ImmutablePair<String, Object[]> saveAssetInfoDetailSql(String oid, String assetOid) {
		
		List<String> cols = Lists.newArrayList("oid", "asset_information_oid");
		List<String> param = Lists.newArrayList("?", "?");
		List<Object> data = Lists.newArrayList(oid, assetOid);
		

		String sCols = Joiner.on(",").join(cols);
		String sParam = Joiner.on(",").join(param);
		String sql = "insert into " + Table.SCHEMA_AST + Table.ASSET_INFO_DETAILS + " (" + sCols + ") values (" + sParam + ")";

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
	
	@Synchronized
	public static ImmutablePair<String, Object[]> getQualifiedQuantity(AuthUser user, String oid) {
		
		String sql = "select sum(t.qualified_quantity) as Sum"
				+ " from " + Table.SCHEMA_AST + Table.TEMP_ITEM_DETAILS
				+ " t where 1 = 1 and t.temporary_item_oid = ?";
			
			List<Object> data = Lists.newArrayList(oid);
			
			Object[] aParam = data.toArray(new Object[data.size()]);
			return new ImmutablePair<String, Object[]>(sql, aParam);
		
	}
	
	@Synchronized
	public static ImmutablePair<String, Object[]> getAllUniqueCode(String code) {
		
		List<Object> data = Lists.newArrayList(code);
		String sql = "select count(*) from " + Table.SCHEMA_AST + Table.ASSET_INFO + " where 1 = 1 and code = ?"; 

		Object[] aParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, aParam);
	}
	
	@Synchronized
	public static ImmutablePair<String, Object[]> getTemporaryItemDetailSql(String oid) {
		
		String sql = "select qualified_quantity, item_oid "
			+ " from " + Table.SCHEMA_AST + Table.TEMP_ITEM_DETAILS 
			+ " where 1 = 1 and temporary_item_oid = ?";

		List<Object> data = Lists.newArrayList(oid);
		
		Object[] aParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, aParam);
	}

}
