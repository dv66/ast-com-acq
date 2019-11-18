package com.cokreates.grp.ast.comacq.tempitem.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import com.cokreates.grp.ast.comacq.tempitem.model.DraftTempItemDetail;
import com.cokreates.grp.ast.comacq.tempitem.request.DraftRequest;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.util.constant.Constant;
import com.cokreates.grp.util.constant.Table;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import lombok.Synchronized;

public class DraftQuery {

	@Synchronized
	public static ImmutablePair<String, Object[]> draftTemporaryItemSql(AuthUser user, DraftRequest request, String oid) {

		ImmutablePair<String, String> now = Constant.getDateTime(new DateTime());
		
		List<String> cols = Lists.newArrayList("oid", "chalan_no", "status", "vendor_oid", "received_by", "created_by", "created_on", "office_oid");
		List<String> param = Lists.newArrayList("?", "?", "?", "?", "?", "?", now.getLeft(), "?");
		List<Object> data = Lists.newArrayList(oid, request.getBody().getData().getChalanNo(), Constant.TI_STATUS_DRAFT,
				request.getBody().getData().getVendorOid(), user.getUserOid(), user.getUserOid(), now.getRight(), user.getOfficeOid());
				
		if(StringUtils.isNotBlank(request.getBody().getData().getProcurementMethod())) {
			cols.add("procurement_method");
			param.add("?");
			data.add(request.getBody().getData().getProcurementMethod());
		}
		
		if(request.getBody().getData().getProcurementMethod().equals(Constant.TI_PRC_METHOD_OTM)) {
			
			if(StringUtils.isNotBlank(request.getBody().getData().getContractNo())) {
				cols.add("contract_no");
				param.add("?");
				data.add(request.getBody().getData().getContractNo());
			}
			
			if(request.getBody().getData().getContractSigningDate() != null)
			{
				cols.add("contract_signing_date");
				param.add("to_date(?, 'YYYY-MM-DD')::date");
				data.add(DateTimeFormat.forPattern(Constant.JAVA_DATE_FORMAT).
						print(request.getBody().getData().getContractSigningDate()));
			}
			
		} else {
			
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
			
			if(request.getBody().getData().getWorkorderDate() != null)
			{
				cols.add("workorder_date");
				param.add("to_date(?, 'YYYY-MM-DD')::date");
				data.add(DateTimeFormat.forPattern(Constant.JAVA_DATE_FORMAT).
						print(request.getBody().getData().getWorkorderDate()));
			}
		}			
		
		if(StringUtils.isNotBlank(request.getBody().getData().getDescription())) {
			cols.add("description");
			param.add("?");
			data.add(request.getBody().getData().getDescription());
		}
		
		if(request.getBody().getData().getReceivedAt() != null)
		{
			cols.add("received_at");
			param.add("to_date(?, 'YYYY-MM-DD')::date");
			data.add(DateTimeFormat.forPattern(Constant.JAVA_DATE_FORMAT).
					print(request.getBody().getData().getReceivedAt()));
		}
		
		if(request.getBody().getData().getChalanDate() != null)
		{
			cols.add("chalan_date");
			param.add("to_date(?, 'YYYY-MM-DD')::date");
			data.add(DateTimeFormat.forPattern(Constant.JAVA_DATE_FORMAT).
					print(request.getBody().getData().getChalanDate()));
		}

		String sCols = Joiner.on(",").join(cols);
		String sParam = Joiner.on(",").join(param);
		String sql = "insert into " + Table.SCHEMA_AST + Table.TEMP_ITEM + " (" + sCols + ") values (" + sParam + ")";

		Object[] aParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, aParam);
	}

	@Synchronized
	public static ImmutablePair<String, Object[]> draftTempItemDetailSql(DraftTempItemDetail rd, String oid, String tempItemOid) {

		List<String> cols = Lists.newArrayList("oid", "item_oid", "temporary_item_oid", "received_quantity", "extra_quantity");
		List<String> param = Lists.newArrayList("?", "?", "?", "?", "?");
		List<Object> data = Lists.newArrayList(oid, rd.getItemOid(), tempItemOid, rd.getReceivedQuantity(), rd.getExtraQuantity());
		
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

}
