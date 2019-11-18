package com.cokreates.grp.ast.comacq.assetinfo.util;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;

import com.cokreates.grp.ast.comacq.assetinfo.request.DraftGetListByTempOidRequest;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.util.constant.Constant;
import com.cokreates.grp.util.constant.Table;
import com.google.common.collect.Lists;

import lombok.Synchronized;

public class DraftGetListByTempOidQuery {
	
	@Synchronized
	public static ImmutablePair<String, Object[]> getAssetInfoSql(AuthUser user, DraftGetListByTempOidRequest request){
		
		String sql = "select a.oid as oid, a.code as code, a.description as description, a.status as status, "
				+ " a.item_oid, t.code as tempItemCode,  ad.serial_no, ad.expiry_duration, ad.remarks "
				+ " from " + Table.SCHEMA_AST + Table.ASSET_INFO 
//				+ " a left join " + Table.SCHEMA_CMN + Table.ITEM
//				+ " i on a.item_oid = i.oid"
				+ " a left join " + Table.SCHEMA_AST + Table.ASSET_INFO_DETAILS
				+ " ad on ad.asset_information_oid = a.oid"
				+ " left join " + Table.SCHEMA_AST + Table.TEMP_ITEM
				+ " t on a.temporary_item_oid = t.oid"
				+ " where 1 = 1 and a.temporary_item_oid = ? and a.office_oid = ? and t.status = ? and a.status = ?"
				+ " order by code asc ";
		 
		 List<Object> data = Lists.newArrayList(request.getBody().getOid(), user.getOfficeOid(), Constant.TI_STATUS_QC_APPROVED, Constant.AI_STATUS_DRAFT);
		 Object[] aParam = data.toArray(new Object[data.size()]);
	     return new ImmutablePair<String, Object[]>(sql, aParam);
	}
	
	@Synchronized
	public static ImmutablePair<String, Object[]> getTemporaryItemStatusSql(AuthUser user, String oid) {
		
		String sql = "select t.status as Status"
				+ " from " + Table.SCHEMA_AST + Table.TEMP_ITEM
				+ " t where 1 = 1 and t.oid = ? and office_oid = ?";
			
			List<Object> data = Lists.newArrayList(oid, user.getOfficeOid());
			
			Object[] aParam = data.toArray(new Object[data.size()]);
			return new ImmutablePair<String, Object[]>(sql, aParam);
		
	}

}
