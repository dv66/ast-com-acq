package com.cokreates.grp.ast.comacq.assetinfo.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.joda.time.DateTime;

import com.cokreates.grp.ast.comacq.assetinfo.model.AssetInfo;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.util.constant.Constant;
import com.cokreates.grp.util.constant.Table;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import lombok.Synchronized;

public class SaveQuery {

	@Synchronized
	public static ImmutablePair<String, Object[]> saveAssetInfoSql(AuthUser user, AssetInfo assetInfo) {

		ImmutablePair<String, String> now = Constant.getDateTime(new DateTime());
		
		List<String> cols = Lists.newArrayList("status = ?", "in_type = ?", "created_by = ?", "created_on = " +  now.getLeft(), " updated_by = ?", "updated_on = " + now.getLeft());
		List<Object> data = Lists.newArrayList(Constant.AI_STATUS_READY_FOR_TAGGING, Constant.AI_IN_TYPE_QC_PASS, user.getUserOid(), now.getRight(), user.getUserOid(), now.getRight());
		
		if(StringUtils.isNotBlank(assetInfo.getDescription())) {
			cols.add("description = ?");
			data.add(assetInfo.getDescription());
		}

		String sCols = Joiner.on(",").join(cols);
		String sql = "update " + Table.SCHEMA_AST + Table.ASSET_INFO + " set " + sCols + " where 1 = 1 and oid = ? and office_oid = ?";
		data.add(assetInfo.getOid());
		data.add(user.getOfficeOid());
		
		Object[] aParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, aParam);
	}
	
	@Synchronized
	public static ImmutablePair<String, Object[]> saveAssetInfoDetailSql(AssetInfo assetInfo) {
		
		List<String> cols = Lists.newArrayList();
		List<Object> data = Lists.newArrayList();
		
		if(StringUtils.isNotBlank(assetInfo.getSerialNo())) {
			cols.add("serial_no = ?");
			data.add(assetInfo.getSerialNo());
		}
		
		if(assetInfo.getExpiryDuration() != null) {
			cols.add("expiry_duration = ?");
			data.add(assetInfo.getExpiryDuration());
		}
		
		if(StringUtils.isNotBlank(assetInfo.getRemarks())) {
			cols.add("remarks = ?");
			data.add(assetInfo.getRemarks());
		}

		String sCols = Joiner.on(",").join(cols);
		String sql = "update " + Table.SCHEMA_AST + Table.ASSET_INFO_DETAILS + " set " + sCols + " where 1 = 1 and asset_information_oid = ?";
		data.add(assetInfo.getOid());
		
		Object[] aParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, aParam);
	}
	
	@Synchronized
	public static ImmutablePair<String, Object[]> updateTemporaryItemSql(AuthUser user, String oid) {
		
		ImmutablePair<String, String> now = Constant.getDateTime(new DateTime());
		
		List<String> cols = Lists.newArrayList( "status = ?", "updated_by = ?", "asset_added_by = ?" , "updated_on = " + now.getLeft(), "asset_added_on = " + now.getLeft());
		
		List<Object> data = Lists.newArrayList( Constant.TI_STATUS_ASSET_ADDED, user.getUserOid(), user.getUserOid(), now.getRight(), now.getRight());
		
		String sCols = Joiner.on(",").join(cols);
		String sql = "update " + Table.SCHEMA_AST + Table.TEMP_ITEM + " set " + sCols + " where 1 = 1 and oid = ? and office_oid = ?";
		data.add(oid);
		data.add(user.getOfficeOid());
		
		Object[] queryParam = data.toArray(new Object[data.size()]);
		
		return new ImmutablePair<String, Object[]>(sql, queryParam);
		
	}
	
	
	@Synchronized
	public static ImmutablePair<String, Object[]> getTemporaryItemStatusSql( AuthUser user, String oid) {
		
		String sql = "select t.status as Status"
				+ " from " + Table.SCHEMA_AST + Table.TEMP_ITEM
				+ " t where 1 = 1 and t.oid = ? and office_oid = ?";
			
			List<Object> data = Lists.newArrayList(oid, user.getOfficeOid());
			
			Object[] aParam = data.toArray(new Object[data.size()]);
			return new ImmutablePair<String, Object[]>(sql, aParam);
		
	}
	
}
