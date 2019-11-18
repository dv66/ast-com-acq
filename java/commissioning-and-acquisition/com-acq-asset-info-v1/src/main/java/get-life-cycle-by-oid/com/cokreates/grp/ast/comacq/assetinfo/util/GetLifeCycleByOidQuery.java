package com.cokreates.grp.ast.comacq.assetinfo.util;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;

import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.util.constant.Table;
import com.google.common.collect.Lists;

import lombok.Synchronized;

public class GetLifeCycleByOidQuery {

	@Synchronized
	public static ImmutablePair<String, Object[]> getAssetInfoSql(String oid, AuthUser user) {
		
		String sql = "select a.code as Code, a.description as Description, a.status as Status, a.created_by as CreatedBy,"
			+ " a.created_on as CreatedOn, a.updated_by as UpdatedBy, a.updated_on as UpdatedOn, t.code as TempCode, "
			+ " t.qc_by as qcBy, t.qc_on as qcOn, t.asset_added_by as assetAddedBy, t.asset_added_on as assetAddedOn, "
			+ " a.item_oid as itemOid, t.workorder_no as workOrderNo, t.chalan_no as ChalanNo, "
			+ " t.decision_on_qc_by as DecisionOnQcBy, t.received_by as ReceivedBy, a.tagged_by as TaggedBy, a.tagged_on as"
			+ " taggedOn,  t.created_by as TempCreatedBy, t.created_on as TempCreatedOn, t.updated_by as TempUpdatedBy,"
			+ " t.updated_on as TempUpdatedOn, t.workorder_date as WorkOrderDate, t.chalan_date as ChalanDate,"
			+ " t.received_at as ReceivedAt, t.procurement_method as procurementMethod, t.contract_no as contractNo,"
			+ " t.contract_signing_date as cotractSigningDate, t.decision_on_qc_date as decisionOnQcDate, "
			+ " ( select name_bn from " + Table.SCHEMA_CMN + Table.VENDOR 
			+ " e where e.oid = t.vendor_oid ) as VendorNameBn, "
			+ " ( select name_en from " + Table.SCHEMA_CMN + Table.VENDOR 
			+ " e where e.oid = t.vendor_oid ) as VendorNameEn "
			+ " from " + Table.SCHEMA_AST + Table.ASSET_INFO 
			+ " a left join " + Table.SCHEMA_AST + Table.TEMP_ITEM
			+ " t on a.temporary_item_oid = t.oid"
			+ " where 1 = 1 and a.oid=? and a.office_oid = ?";
		
		List<Object> data = Lists.newArrayList(oid, user.getOfficeOid());
		
		Object[] aParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, aParam);
	}
	
	@Synchronized
	public static ImmutablePair<String, Object[]> getAssetAllocationDetailSql(String oid) {
		
		String sql = "select a.code as AssetAllocationCode, a.end_user_oid as endUserOid, a.allocator_oid as allocatorOid,"
			+ " a.allocation_date as AllocationDate, d.received_date as ReceivedDate, "
			+ " ( select requisition_ref_no from " + Table.SCHEMA_AST + Table.ASSET_REQUISITION 
			+ " e where e.oid = a.asset_requisition_oid ) as RequisitionCode, "
			+ "  ar.requested_date as RequestedDate, arl.qc_pass as QcPassed "
			+ " from " + Table.SCHEMA_AST + Table.ASSET_ALLOCATION_DETAILS 
			+ " d left join " + Table.SCHEMA_AST + Table.ASSET_ALLOCATION
			+ " a on d.asset_allocation_oid = a.oid"
			+ " left join "+ Table.SCHEMA_AST + Table.ASSET_RETURN
			+ " ar on ar.asset_allocation_oid = a.oid"
			+ " left join "+ Table.SCHEMA_AST + Table.ASSET_RETURN_LINE
			+ " arl on arl.asset_return_oid = ar.oid"
			+ " where 1 = 1 and d.asset_oid=? "
			+ " order by a.allocation_date";
		
		List<Object> data = Lists.newArrayList(oid);
		
		Object[] aParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, aParam);
	}
	@Synchronized
	public static ImmutablePair<String, Object[]> getAssetMaintenanceAndDisposalInfoSql(String oid, String status1, String status2, AuthUser user) {
		
		String sql = "select e.code as Code, e.decision_by as DecisionBy, e.decision_on as DecisionOn, e.status as Status,"
				    + "	case "
	                + "		when e.status = 'Maintenance approved' or e.status = 'Maintenance done' then e.maintenance_cost "
	                + "		when e.status = 'Disposal approved' or e.status = 'Disposal done' then e.disposal_cost "
	                + "		else '' "
	                + "	end as Cost, "
	                + "	case "
	                + "		when e.status = 'Maintenance approved' or e.status = 'Maintenance done' then e.maintenance_vendor_name "
	                + "		when e.status = 'Disposal approved' or e.status = 'Disposal done' then e.disposal_vendor_name "
	                + "		else ' ' "
	                + "	end as VendorName,  "
	                + "	case "
	                + "		when e.status = 'Maintenance approved' or e.status = 'Maintenance done' then e.maintenance_remarks "
	                + "		when e.status = 'Disposal approved' or e.status = 'Disposal done' then e.disposal_remarks "
	                + "		else ' ' "
	                + "	end as Remarks,  "
	                + "  e.maintenance_date as MaintenanceDate, e.disposal_process as DisposalProcess"
	                + " from "+ Table.SCHEMA_AST + Table.EXPERT_DECISION
	                + " e where 1 = 1 and e.asset_oid=? and (e.status=? or e.status=?) and e.office_oid=?"
	                + " order by e.decision_on";
		
		List<Object> data = Lists.newArrayList(oid, status1, status2, user.getOfficeOid());
		
		Object[] aParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, aParam);
	}
}

