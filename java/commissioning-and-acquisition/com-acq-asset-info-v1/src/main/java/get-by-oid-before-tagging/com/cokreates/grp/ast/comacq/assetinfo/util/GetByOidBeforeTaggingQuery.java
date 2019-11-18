package com.cokreates.grp.ast.comacq.assetinfo.util;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;

import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.util.constant.Constant;
import com.cokreates.grp.util.constant.Table;
import com.google.common.collect.Lists;

import lombok.Synchronized;

public class GetByOidBeforeTaggingQuery {

	@Synchronized
	public static ImmutablePair<String, Object[]> getAssetInfoBeforeTaggingSql(AuthUser user,String oid) {
		
		String sql = "select t.code as Code, t.description as Description, t.status as Status, t.created_by as CreatedBy,"
			+ " t.created_on as CreatedOn, t.updated_by as UpdatedBy, t.updated_on as UpdatedOn, i.code as TempCode, "
			+ " i.qc_by as qcBy, i.qc_on as qcOn, i.asset_added_by as assetAddedBy, i.asset_added_on as assetAddedOn, t.item_oid  "
//			+ " ( select name_bn from " + Table.SCHEMA_HRM + Table.EMPLOYEE_MASTER_INFO 
//			+ " e where e.grp_username = i.qc_by ) as qcByBn, "
//			+ " ( select name_en from " + Table.SCHEMA_HRM + Table.EMPLOYEE_MASTER_INFO 
//			+ " e where e.grp_username = i.qc_by ) as qcByEn, "
//			+ " ( select name_bn from " + Table.SCHEMA_HRM + Table.EMPLOYEE_MASTER_INFO 
//			+ " e where e.grp_username = i.asset_added_by ) as assetAddedByBn, "
//			+ " ( select name_en from " + Table.SCHEMA_HRM + Table.EMPLOYEE_MASTER_INFO 
//			+ " e where e.grp_username = i.asset_added_by ) as assetAddedByEn "
			+ " from " + Table.SCHEMA_AST + Table.ASSET_INFO 
			+ " t left join " + Table.SCHEMA_AST + Table.TEMP_ITEM
			+ " i on t.temporary_item_oid = i.oid"
			+ " where 1 = 1 and t.oid = ? and t.status = ?";
		
		List<Object> data = Lists.newArrayList(oid, Constant.AI_STATUS_READY_FOR_TAGGING);
		
		Object[] aParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, aParam);
	}
}

