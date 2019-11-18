package com.cokreates.grp.ast.comacq.assetinfo.util;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.joda.time.DateTime;

import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.util.constant.Constant;
import com.cokreates.grp.util.constant.Table;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import lombok.Synchronized;

public class AssetTagQuery {

	@Synchronized
	public static ImmutablePair<String, Object[]> assetTagSql(AuthUser user, String oid) {

		ImmutablePair<String, String> now = Constant.getDateTime(new DateTime());
		List<String> cols = Lists.newArrayList("status = ?", "tagged_by = ?", "tagged_on = " +  now.getLeft(), "updated_by = ?", "updated_on = " +  now.getLeft());
		List<Object> data = Lists.newArrayList(Constant.AI_STATUS_AVAILABLE, user.getUserOid(), now.getRight(), user.getUserOid(), now.getRight());

		String sCols = Joiner.on(",").join(cols);
		String sql = "update " + Table.SCHEMA_AST + Table.ASSET_INFO + " set " + sCols + " where 1 = 1 and oid = ?";
		data.add(oid);
		
		Object[] queryParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, queryParam);
	}
	
	@Synchronized
	public static ImmutablePair<String, Object[]> getAssetStatusSql(String oid) {
		
		String sql = "select status as status, temporary_item_oid as tempItemOid"
			+ " from " + Table.SCHEMA_AST + Table.ASSET_INFO
			+ " where 1 = 1 and oid=?";
		
		List<Object> data = Lists.newArrayList(oid);
		
		Object[] aParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, aParam);
	}
	
	@Synchronized
	public static ImmutablePair<String, Object[]> getAssetStatusAfterTagSql(String oid) {
		
		String sql = "select status as status, temporary_item_oid as tempItemOid"
			+ " from " + Table.SCHEMA_AST + Table.ASSET_INFO
			+ " where status = ? and temporary_item_oid=?";
		
		List<Object> data = Lists.newArrayList(Constant.AI_STATUS_READY_FOR_TAGGING, oid);
		
		Object[] aParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, aParam);
	}
	
	@Synchronized
	public static ImmutablePair<String, Object[]> temporaryItemUpdatedAfterAssetTagSql(String oid) {

		//ImmutablePair<String, String> now = Constant.getDateTime(new DateTime());
		List<String> cols = Lists.newArrayList("status = ?");
		List<Object> data = Lists.newArrayList(Constant.TI_STATUS_ASSET_TAGGED);

		String sCols = Joiner.on(",").join(cols);
		String sql = "update " + Table.SCHEMA_AST + Table.TEMP_ITEM + " set " + sCols + " where 1 = 1 and oid = ?";
		data.add(oid);
		
		Object[] queryParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, queryParam);
	}
	

}
