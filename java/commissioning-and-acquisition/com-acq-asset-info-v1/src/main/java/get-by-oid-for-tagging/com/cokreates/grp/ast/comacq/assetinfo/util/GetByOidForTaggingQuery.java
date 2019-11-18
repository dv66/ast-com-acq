package com.cokreates.grp.ast.comacq.assetinfo.util;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;

import com.cokreates.grp.util.constant.Constant;
import com.cokreates.grp.util.constant.Table;
import com.google.common.collect.Lists;

import lombok.Synchronized;

public class GetByOidForTaggingQuery {

	@Synchronized
	public static ImmutablePair<String, Object[]> getAssetInfoForTaggingSql(String oid) {
		
		String sql = "select t.oid as oid, t.code as code, t.description as description, t.status as status, t.temporary_item_oid as tempItemOid,"
			+ " t.item_oid as itemOid, ti.code as tempItemCode"
			+ " from " + Table.SCHEMA_AST + Table.ASSET_INFO 
			+ " t left join "+Table.SCHEMA_AST + Table.TEMP_ITEM
			+ " ti on t.temporary_item_oid = ti.oid"
			+ " where t.status = ? and t.temporary_item_oid=?";
		
		List<Object> data = Lists.newArrayList(Constant.AI_STATUS_READY_FOR_TAGGING,oid);
		
		Object[] aParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, aParam);
	}
	@Synchronized
	public static ImmutablePair<String, Object[]> getAssetCountForTaggingSql(String oid) {
		
		String sql = "select count(t.oid) as oid"
		    + " from " + Table.SCHEMA_AST + Table.ASSET_INFO 
			+ " t left join "+Table.SCHEMA_AST + Table.TEMP_ITEM
			+ " ti on t.temporary_item_oid = ti.oid"
			+ " where t.status = ? and t.temporary_item_oid=?";
		
		List<Object> data = Lists.newArrayList(Constant.AI_STATUS_READY_FOR_TAGGING,oid);
		
		Object[] aParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, aParam);
	}
}

