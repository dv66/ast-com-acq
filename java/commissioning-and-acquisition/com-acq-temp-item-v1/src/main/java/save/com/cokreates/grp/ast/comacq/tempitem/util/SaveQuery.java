package com.cokreates.grp.ast.comacq.tempitem.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import com.cokreates.grp.ast.comacq.tempitem.model.SaveTempItemDetail;
import com.cokreates.grp.ast.comacq.tempitem.request.SaveRequest;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.util.constant.Constant;
import com.cokreates.grp.util.constant.Table;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import lombok.Synchronized;

public class SaveQuery {

	@Synchronized
	public static ImmutablePair<String, Object[]> saveTemporaryItemSql(AuthUser user, SaveRequest request, String oid, String uniqueCode) {

		ImmutablePair<String, String> now = Constant.getDateTime(new DateTime());
		
		List<String> cols = Lists.newArrayList("oid", "code", "procurement_method" , "received_at", "received_by", "chalan_no", "chalan_date", "status", "vendor_oid", "created_by","created_on", "updated_by","updated_on", "office_oid");
		List<String> param = Lists.newArrayList("?", "?", "?" , "to_date(?, 'YYYY-MM-DD')::date", "?", "?" ,"to_date(?, 'YYYY-MM-DD')::date", "?", "?", "?", now.getLeft(), "?", now.getLeft(),  "?");
		List<Object> data = Lists.newArrayList(oid, uniqueCode, request.getBody().getData().getProcurementMethod() , DateTimeFormat.forPattern(Constant.JAVA_DATE_FORMAT).print(request.getBody().getData().getReceivedAt()), 
			user.getUserOid(), request.getBody().getData().getChalanNo(), DateTimeFormat.forPattern(Constant.JAVA_DATE_FORMAT).print(request.getBody().getData().getChalanDate()),
			Constant.TI_STATUS_READY_FOR_QC, request.getBody().getData().getVendorOid(), user.getUserOid(), now.getRight(), user.getUserOid(), now.getRight(), user.getOfficeOid());
		
			
		if(StringUtils.isNotBlank(request.getBody().getData().getContractNo())) {
			cols.add("contract_no");
			param.add("?");
			data.add(request.getBody().getData().getContractNo());
		}
		
		if(request.getBody().getData().getContractSigningDate() != null) {
			cols.add("contract_signing_date");
			param.add("to_date(?, 'YYYY-MM-DD')::date");
			data.add(DateTimeFormat.forPattern(Constant.JAVA_DATE_FORMAT).print(request.getBody().getData().getContractSigningDate()));
		}
			
		if(StringUtils.isNotBlank(request.getBody().getData().getWorkorderNo())) {
			cols.add("workorder_no");
			param.add("?");
			data.add(request.getBody().getData().getWorkorderNo());
		}
		
		if(StringUtils.isNotBlank(request.getBody().getData().getWorkOrderOid())) {
			cols.add("work_order_oid");
			param.add("?");
			data.add(request.getBody().getData().getWorkOrderOid());
		}
		
		if(request.getBody().getData().getWorkorderDate() != null) {
			cols.add("workorder_date");
			param.add("to_date(?, 'YYYY-MM-DD')::date");
			data.add(DateTimeFormat.forPattern(Constant.JAVA_DATE_FORMAT).print(request.getBody().getData().getWorkorderDate()));
		}
		
		if(StringUtils.isNotBlank(request.getBody().getData().getDescription())) {
			cols.add("description");
			param.add("?");
			data.add(request.getBody().getData().getDescription());
		}

		String sCols = Joiner.on(",").join(cols);
		String sParam = Joiner.on(",").join(param);
		String sql = "insert into " + Table.SCHEMA_AST + Table.TEMP_ITEM + " (" + sCols + ") values (" + sParam + ")";

		Object[] aParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, aParam);
	}

	@Synchronized
	public static ImmutablePair<String, Object[]> saveTempItemDetailSql(SaveTempItemDetail rd, String oid, String tempItemOid) {

		List<String> cols = Lists.newArrayList("oid", "received_quantity", "extra_quantity", "item_oid", "temporary_item_oid");
		List<String> param = Lists.newArrayList("?", "?", "?", "?", "?");
		List<Object> data = Lists.newArrayList(oid, rd.getReceivedQuantity(), rd.getExtraQuantity(), rd.getItemOid(), tempItemOid);
		
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
	public static ImmutablePair<String, Object[]> getAllUniqueCode(String code) {
		
		List<Object> data = Lists.newArrayList(code);
		String sql = "select count(*) from " + Table.SCHEMA_AST + Table.TEMP_ITEM + " where 1 = 1 and code = ?"; 

		Object[] aParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, aParam);
	}
	
	@Synchronized
	public static ImmutablePair<String, Object[]> getWorkOrderDetail(SaveRequest rd) {
		//rd.getBody().getData().getWorkOrderOid()
		List<Object> data = Lists.newArrayList(rd.getBody().getData().getWorkOrderOid());
        
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
