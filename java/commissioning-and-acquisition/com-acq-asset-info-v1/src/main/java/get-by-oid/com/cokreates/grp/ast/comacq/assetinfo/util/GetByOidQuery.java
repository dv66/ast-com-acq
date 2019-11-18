package com.cokreates.grp.ast.comacq.assetinfo.util;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;

import com.cokreates.grp.util.constant.Table;
import com.google.common.collect.Lists;

import lombok.Synchronized;

public class GetByOidQuery {

	@Synchronized
	public static ImmutablePair<String, Object[]> getAssetInfoSql(String oid) {
		
		String sql = "select a.code as Code, a.description as Description, a.status as Status, a.created_by as CreatedBy,"
			+ " a.created_on as CreatedOn, a.updated_by as UpdatedBy, a.updated_on as UpdatedOn, t.code as TempCode, "
			+ " t.qc_by as qcBy, t.qc_on as qcOn, t.asset_added_by as assetAddedBy, t.asset_added_on as assetAddedOn, "
			+ " aid.serial_no as serialNo, aid.expiry_duration as expiryDuration, aid.remarks as remarks, a.item_oid "
//			+ " ( select name_bn from " + Table.SCHEMA_HRM + Table.EMPLOYEE_MASTER_INFO 
//			+ " e where e.grp_username = t.qc_by ) as qcByBn, "
//			+ " ( select name_en from " + Table.SCHEMA_HRM + Table.EMPLOYEE_MASTER_INFO 
//			+ " e where e.grp_username = t.qc_by ) as qcByEn, "
//			+ " ( select name_bn from " + Table.SCHEMA_HRM + Table.EMPLOYEE_MASTER_INFO 
//			+ " e where e.grp_username = t.asset_added_by ) as assetAddedByBn, "
//			+ " ( select name_en from " + Table.SCHEMA_HRM + Table.EMPLOYEE_MASTER_INFO 
//			+ " e where e.grp_username = t.asset_added_by ) as assetAddedByEn "
			+ " from " + Table.SCHEMA_AST + Table.ASSET_INFO 
			+ " a left join " + Table.SCHEMA_AST + Table.ASSET_INFO_DETAILS
			+ " aid on aid.asset_information_oid = a.oid "
			+ " left join " + Table.SCHEMA_AST + Table.TEMP_ITEM
			+ " t on a.temporary_item_oid = t.oid"
//			+ " left join "+Table.SCHEMA_CMN + Table.ITEM
//			+ " s on a.item_oid = s.oid"
			+ " where 1 = 1 and a.oid=?";
		
		List<Object> data = Lists.newArrayList(oid);
		
		Object[] aParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, aParam);
	}
}

