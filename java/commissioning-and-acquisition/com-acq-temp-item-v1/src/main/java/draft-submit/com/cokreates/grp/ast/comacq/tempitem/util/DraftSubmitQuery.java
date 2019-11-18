package com.cokreates.grp.ast.comacq.tempitem.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import com.cokreates.grp.ast.comacq.tempitem.model.DraftSubmitTempItem;
import com.cokreates.grp.ast.comacq.tempitem.model.DraftSubmitTempItemDetail;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.util.constant.Constant;
import com.cokreates.grp.util.constant.Table;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import lombok.Synchronized;

public class DraftSubmitQuery {

	@Synchronized
	public static ImmutablePair<String, Object[]> draftSubmitTemporaryItemSql(AuthUser user, DraftSubmitTempItem body, String uniqueCode) {
		
		ImmutablePair<String, String> now = Constant.getDateTime(new DateTime());
		
		List<String> cols = Lists.newArrayList("code = ?", "received_by = ?", "procurement_method = ?", "chalan_no = ?", "chalan_date = to_date(?, 'YYYY-MM-DD')::date", 
				"received_at = to_date(?, 'YYYY-MM-DD')::date", "vendor_oid = ?", "created_by = ?" , "created_on = " + now.getLeft(),
				"updated_by = ?" , "updated_on = " + now.getLeft(), "office_oid = ?", "status =?");
		
		List<Object> data = Lists.newArrayList(uniqueCode, user.getUserOid(), body.getProcurementMethod(),  body.getChalanNo(), 
				DateTimeFormat.forPattern(Constant.JAVA_DATE_FORMAT).print(body.getChalanDate()), DateTimeFormat.forPattern(Constant.JAVA_DATE_FORMAT).print(body.getReceivedAt()), 
				body.getVendorOid(), user.getUserOid(), now.getRight(), user.getUserOid(), now.getRight(), user.getOfficeOid(), Constant.TI_STATUS_READY_FOR_QC);
		
		if(body.getProcurementMethod().equalsIgnoreCase(Constant.TI_PRC_METHOD_OTM)) {
			
			if (StringUtils.isNotBlank(body.getContractNo())) {
				cols.add("contract_no = ?");
				data.add(body.getContractNo());
			}
			
			if (body.getContractSigningDate() != null) {
				cols.add("contract_signing_date = to_date(?, 'YYYY-MM-DD')::date");
				data.add(DateTimeFormat.forPattern(Constant.JAVA_DATE_FORMAT).print(body.getContractSigningDate()));
			}
			
			if (StringUtils.isNotBlank(body.getWorkorderNo())) {
				cols.add("workorder_no = ''");
			}
			
			if (body.getWorkorderDate() != null) {
				cols.add("workorder_date = null");
			}
		} else {
			
			if (StringUtils.isNotBlank(body.getWorkorderNo())) {
				cols.add("workorder_no = ?");
				data.add(body.getWorkorderNo());
			}
			
			if (StringUtils.isNotBlank(body.getWorkOrderOid())) {
				cols.add("work_order_oid = ?");
				data.add(body.getWorkOrderOid());
			}
			
			if (body.getWorkorderDate() != null) {
				cols.add("workorder_date = to_date(?, 'YYYY-MM-DD')::date");
				data.add(DateTimeFormat.forPattern(Constant.JAVA_DATE_FORMAT).print(body.getWorkorderDate()));
			}
			
			if (StringUtils.isNotBlank(body.getContractNo())) {
				cols.add("contract_no = ''");
			}
			
			if (body.getContractSigningDate() != null) {
				cols.add("contract_signing_date = null");
			}
		}
				
		if (StringUtils.isNotBlank(body.getDescription())) {
			cols.add("description = ?");
			data.add(body.getDescription());
		}

		String sCols = Joiner.on(",").join(cols);
		String sql = "update " + Table.SCHEMA_AST + Table.TEMP_ITEM + " set " + sCols + " where 1 = 1 and oid = ?";
		data.add(body.getOid());
		
		Object[] queryParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, queryParam);
	}

	@Synchronized
	public static ImmutablePair<String, Object[]> saveTempItemDetailSql(DraftSubmitTempItemDetail rd, String oid, String tempItemOid) {

		List<String> cols = Lists.newArrayList("oid", "received_quantity", "item_oid", "extra_quantity", "temporary_item_oid");
		List<String> param = Lists.newArrayList("?", "?", "?", "?", "?");
		List<Object> data = Lists.newArrayList(oid, rd.getReceivedQuantity(), rd.getItemOid(), rd.getExtraQuantity(), tempItemOid);
		
		if(StringUtils.isNotBlank(rd.getRemarks())) {
			cols.add("remarks");
			param.add("?");
			data.add(rd.getRemarks());
		}
		
		if(StringUtils.isNotBlank(rd.getWorkOrderDetailOid())) {
			cols.add("work_order_detail_oid");
			param.add("?");
			data.add(rd.getWorkOrderDetailOid());
		}
		
		String sCols = Joiner.on(",").join(cols);
		String sParam = Joiner.on(",").join(param);
		String sql = "insert into " + Table.SCHEMA_AST + Table.TEMP_ITEM_DETAILS + " (" + sCols + ") values (" + sParam + ")";

		Object[] aParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, aParam);
	}
	
	@Synchronized
	public static ImmutablePair<String, Object[]> deleteTemporaryItemDetailsSql(String tempItemOid) {

		String sql = "delete from " + Table.SCHEMA_AST + Table.TEMP_ITEM_DETAILS + " where 1 = 1 and temporary_item_oid = ?";
		List<Object> data = Lists.newArrayList(tempItemOid);
		
		Object[] queryParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, queryParam);
	}
	
	
	@Synchronized
	public static ImmutablePair<String, Object[]> getTemporaryItemSql(String oid) {
		
		String sql = "select status "
			+ " from " + Table.SCHEMA_AST + Table.TEMP_ITEM
			+ " where 1 = 1 and oid=?";
		
		List<Object> data = Lists.newArrayList(oid);
		
		Object[] aParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, aParam);
	}
	
	
	@Synchronized
	public static ImmutablePair<String, Object[]> getAllUniqueCode(String code) {
		
		List<Object> data = Lists.newArrayList(code);
		String sql = "select count(*) from " + Table.SCHEMA_AST + Table.TEMP_ITEM + " where 1 = 1 and code = ?"; 

		Object[] aParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, aParam);
	}
	
	
	@Synchronized
	public static ImmutablePair<String, Object[]> getWorkOrderDetail(DraftSubmitTempItem body) {
		//rd.getBody().getData().getWorkOrderOid()
		List<Object> data = Lists.newArrayList(body.getWorkOrderOid());
        
		String sql = "select oid as oid, quantity as orderedQuantity, previous_received_quantity as previosulyReceivedQuantity"
				+ " from " + Table.SCHEMA_PRC + Table.WORK_ORDER_DETAIL 
				+ " where 1 = 1 and work_order_oid = ? ";
		
		Object[] aParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, aParam);
		
	}
	
	@Synchronized
	public static ImmutablePair<String, Object[]> updateWorkOrderInfoSql(AuthUser user, String status, String oid) {
		
		ImmutablePair<String, String> now = Constant.getDateTime(new DateTime());
		
		List<String> cols = Lists.newArrayList( "status = ?", "updated_by = ?",  "updated_on = " + now.getLeft());
		
		List<Object> data = Lists.newArrayList( status, user.getUserOid(), now.getRight());
		
		String sCols = Joiner.on(",").join(cols);
		String sql = "update " + Table.SCHEMA_PRC + Table.WORK_ORDER_INFO + " set " + sCols + " where 1 = 1 and oid = ? and office_oid = ?";
		data.add(oid);
		data.add(user.getOfficeOid());
		
		Object[] queryParam = data.toArray(new Object[data.size()]);
		
		return new ImmutablePair<String, Object[]>(sql, queryParam);
		
	}
	
	@Synchronized
	public static ImmutablePair<String, Object[]> updateWorkOrderDetailSql(int amount, String oid) {
		
		List<String> cols = Lists.newArrayList( "previous_received_quantity = ?");
		
		List<Object> data = Lists.newArrayList( amount );
		
		String sCols = Joiner.on(",").join(cols);
		String sql = "update " + Table.SCHEMA_PRC + Table.WORK_ORDER_DETAIL + " set " + sCols + " where 1 = 1 and oid = ? ";
		data.add(oid);
		
		Object[] queryParam = data.toArray(new Object[data.size()]);
		
		return new ImmutablePair<String, Object[]>(sql, queryParam);
		
	}
	
	

}
