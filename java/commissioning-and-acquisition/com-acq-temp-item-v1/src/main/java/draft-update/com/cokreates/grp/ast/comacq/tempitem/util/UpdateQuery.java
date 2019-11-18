package com.cokreates.grp.ast.comacq.tempitem.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import com.cokreates.grp.ast.comacq.tempitem.model.UpdateTempItem;
import com.cokreates.grp.ast.comacq.tempitem.model.UpdateTempItemDetail;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.util.constant.Constant;
import com.cokreates.grp.util.constant.Table;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import lombok.Synchronized;

public class UpdateQuery {

	@Synchronized
	public static ImmutablePair<String, Object[]> updateTemporaryItemSql(AuthUser user, UpdateTempItem body) {
		
		ImmutablePair<String, String> now = Constant.getDateTime(new DateTime());
		List<String> cols = Lists.newArrayList("chalan_no = ?", "chalan_date = to_date(?, 'YYYY-MM-DD')::date",
				"received_at = to_date(?, 'YYYY-MM-DD')::date", "vendor_oid = ?", "procurement_method = ?", "updated_by = ?" , "updated_on = " + now.getLeft());
		
		List<Object> data = Lists.newArrayList(body.getChalanNo(), DateTimeFormat.forPattern(Constant.JAVA_DATE_FORMAT).print(body.getChalanDate()),
				DateTimeFormat.forPattern(Constant.JAVA_DATE_FORMAT).print(body.getReceivedAt()), 
				body.getVendorOid(), body.getProcurementMethod(), user.getUserOid(), now.getRight());
		
		if(body.getProcurementMethod().equals(Constant.TI_PRC_METHOD_OTM)) {
			
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
		String sql = "update " + Table.SCHEMA_AST + Table.TEMP_ITEM + " set " + sCols + " where 1 = 1 and oid = ? and office_oid = ?";
		data.add(body.getOid());
		data.add(user.getOfficeOid());
		
		Object[] queryParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, queryParam);
	}

	@Synchronized
	public static ImmutablePair<String, Object[]> saveTempItemDetailSql(UpdateTempItemDetail rd, String oid, String tempItemOid) {

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

}
