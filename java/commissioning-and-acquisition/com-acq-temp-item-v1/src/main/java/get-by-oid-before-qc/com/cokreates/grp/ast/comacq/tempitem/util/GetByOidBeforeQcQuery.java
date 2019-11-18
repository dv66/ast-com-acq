package com.cokreates.grp.ast.comacq.tempitem.util;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;

import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.util.constant.Constant;
import com.cokreates.grp.util.constant.Table;
import com.google.common.collect.Lists;

import lombok.Synchronized;

public class GetByOidBeforeQcQuery {

	@Synchronized
	public static ImmutablePair<String, Object[]> getTemporaryItemSql(AuthUser user, String oid) {
		
		String sql = "select t.code, v.oid as vendorOid, v.name_en as vendorNameEn, v.name_bn as vendorNameBn,"
			+ " t.chalan_no as chalanNo, t.chalan_date as chalanDate, t.workorder_no as workorderNo, t.work_order_oid as workOrderOid, t.workorder_date as workorderDate,"
			+ " t.contract_no as contractNo, t.contract_signing_date as contractSigningDate, t.procurement_method as procurementMethod,"
//			+ " t.decision_on_qc_note as decisionOnQcNote, t.decision_on_qc_date as decisionOnQcDate,"
			+ " t.received_by as receivedBy, t.description, t.status, t.received_at as receivedAt "
//			+ " ( select name_bn from " + Table.SCHEMA_HRM + Table.EMPLOYEE_MASTER_INFO 
//			+ " e where e.grp_username = t.decision_on_qc_by ) as decisionOnQcByBn, "
//			+ " ( select name_en from " + Table.SCHEMA_HRM + Table.EMPLOYEE_MASTER_INFO 
//			+ " e where e.grp_username = t.decision_on_qc_by ) as decisionOnQcByEn, "
//			+ " ( select name_bn from " + Table.SCHEMA_HRM + Table.EMPLOYEE_MASTER_INFO 
//			+ " e where e.grp_username = t.received_by ) as receivedByBn, "
//			+ " ( select name_en from " + Table.SCHEMA_HRM + Table.EMPLOYEE_MASTER_INFO 
//			+ " e where e.grp_username = t.received_by ) as receivedByEn "
			+ " from " + Table.SCHEMA_AST + Table.TEMP_ITEM 
			+ " t left join " + Table.SCHEMA_CMN + Table.VENDOR 
			+ " v on v.oid = t.vendor_oid"
			+ " where 1 = 1 and t.oid = ? and t.status NOT IN (?, ?, ?, ?, ?, ?, ?) and office_oid = ?";
		
		List<Object> data = Lists.newArrayList(oid, Constant.TI_STATUS_PROCESSING, Constant.TI_STATUS_QC_DONE, Constant.TI_STATUS_REJECTED, 
				Constant.TI_STATUS_ASSET_ADDED, Constant.TI_STATUS_DRAFT, Constant.TI_STATUS_ASSET_TAGGED, Constant.TI_STATUS_QC_APPROVED,
				user.getOfficeOid());
		
		Object[] aParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, aParam);
	}

	@Synchronized
	public static ImmutablePair<String, Object[]> getTemporaryItemDetailSql(String oid) {
		
		String sql = "select t.oid as oid, t.work_order_detail_oid as workOrderDetailOid, t.item_oid as itemOid, t.received_quantity as receivedQuantity, "
			+ " t.extra_quantity as extraQuantity, t.remarks as remarks"
			+ " from " + Table.SCHEMA_AST + Table.TEMP_ITEM_DETAILS
			+ " t where 1 = 1 and t.temporary_item_oid = ?";

		List<Object> data = Lists.newArrayList(oid);
		
		Object[] aParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, aParam);
	}
	

}

