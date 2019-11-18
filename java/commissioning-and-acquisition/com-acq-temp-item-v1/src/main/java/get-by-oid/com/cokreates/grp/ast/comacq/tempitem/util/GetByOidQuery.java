package com.cokreates.grp.ast.comacq.tempitem.util;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;

import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.util.constant.Table;
import com.google.common.collect.Lists;

import lombok.Synchronized;

public class GetByOidQuery {

	@Synchronized
	public static ImmutablePair<String, Object[]> getTemporaryItemSql(AuthUser user, String oid) {
		
		String sql = "select t.code, v.oid as vendorOid, v.name_en as vendorNameEn, v.name_bn as vendorNameBn, "
			+ " t.chalan_no as chalanNo, t.chalan_date as chalanDate, t.workorder_no as workorderNo, t.work_order_oid as workOrderOid, t.workorder_date as workorderDate,"
			+ " t.contract_no as contractNo, t.contract_signing_date as contractSigningDate, t.procurement_method as procurementMethod,"
			+ " t.received_by as receivedBy, t.description as tempDescription, t.status, t.received_at as receivedAt, "
			+ " t.decision_on_qc_note as decisionOnQcNote, t.decision_on_qc_date as decisionOnQcDate, t.decision_on_qc_by as decisionOnQcBy, "
			+ " t.qc_by as qcBy, t.qc_on as qcOn, t.asset_added_by as assetAddedBy, t.asset_added_on as assetAddedOn, t.qc_report_path as qcReportPath "
			+ " from " + Table.SCHEMA_AST + Table.TEMP_ITEM
			+ " t left join " + Table.SCHEMA_CMN + Table.VENDOR 
			+ " v on v.oid = t.vendor_oid"
			+ " where 1 = 1 and t.oid = ? and office_oid = ?";
		
		List<Object> data = Lists.newArrayList(oid, user.getOfficeOid());
		
		Object[] aParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, aParam);
	}

	@Synchronized
	public static ImmutablePair<String, Object[]> getTemporaryItemDetailSql(String oid) {
		
		String sql = "select t.oid as oid, t.item_oid as itemOid, t.received_quantity as receivedQuantity, t.work_order_detail_oid as workOrderDetailOid, "
			+ " t.extra_quantity as extraQuantity, t.qualified_quantity as qualifiedQuantity,"
			+ " t.disqualified_quantity as disqualifiedQuantity, t.remarks,"
			+ " w.quantity as quantity, w.previous_received_quantity as previousReceivedQuantity "
			+ " from " + Table.SCHEMA_AST + Table.TEMP_ITEM_DETAILS 
			+ " t left join " + Table.SCHEMA_PRC + Table.WORK_ORDER_DETAIL
			+ " w on w.oid = t.work_order_detail_oid"
			+ " where 1 = 1 and t.temporary_item_oid = ?";

		List<Object> data = Lists.newArrayList(oid);
		
		Object[] aParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, aParam);
	}
	

}

