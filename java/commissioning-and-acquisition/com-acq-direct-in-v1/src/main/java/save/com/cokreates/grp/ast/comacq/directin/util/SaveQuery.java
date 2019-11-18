package com.cokreates.grp.ast.comacq.directin.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import com.cokreates.grp.ast.comacq.directin.model.SaveDirectInDetail;
import com.cokreates.grp.ast.comacq.directin.request.SaveRequest;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.util.constant.Constant;
import com.cokreates.grp.util.constant.Table;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import lombok.Synchronized;

public class SaveQuery {

	@Synchronized
	public static ImmutablePair<String, Object[]> saveDirectInSql(AuthUser user, SaveRequest request, String oid, String uniqueCode) {

		ImmutablePair<String, String> now = Constant.getDateTime(new DateTime());
		
		List<String> cols = Lists.newArrayList("oid", "code" , "requested_on", "requested_by",  "status",  "created_by","created_on", "updated_by","updated_on", "office_oid", "direct_in_type");
		List<String> param = Lists.newArrayList("?", "?" , now.getLeft(), "?", "?", "?", now.getLeft(), "?", now.getLeft(),  "?",  "?");
		List<Object> data = Lists.newArrayList(oid, uniqueCode, now.getRight(), user.getUserOid(), Constant.DI_PENDING, 
			 user.getUserOid(), now.getRight(), user.getUserOid(), now.getRight(), user.getOfficeOid(), request.getBody().getData().getDirectInType());
		

		if(StringUtils.isNotBlank(request.getBody().getData().getRemarks())) {
			cols.add("remarks");
			param.add("?");
			data.add(request.getBody().getData().getRemarks());
		}
		
		if(StringUtils.isNotBlank(request.getBody().getData().getStoreOid())) {
			cols.add("store_oid");
			param.add("?");
			data.add(request.getBody().getData().getStoreOid());
		}
		
		if(StringUtils.isNotBlank(request.getBody().getData().getEndUserOid())) {
			cols.add("user_oid");
			param.add("?");
			data.add(request.getBody().getData().getEndUserOid());
		}
		
		if(StringUtils.isNotBlank(request.getBody().getData().getOfficeUnitOid())) {
			cols.add("office_unit_oid");
			param.add("?");
			data.add(request.getBody().getData().getOfficeUnitOid());
		}
		
		if(StringUtils.isNotBlank(request.getBody().getData().getOfficeUnitPostOid())) {
			cols.add("office_unit_post_oid");
			param.add("?");
			data.add(request.getBody().getData().getOfficeUnitPostOid());
		}
		
		if(StringUtils.isNotBlank(request.getBody().getData().getPurposeOid())) {
			cols.add("purpose_oid");
			param.add("?");
			data.add(request.getBody().getData().getPurposeOid());
		}

		String sCols = Joiner.on(",").join(cols);
		String sParam = Joiner.on(",").join(param);
		String sql = "insert into " + Table.SCHEMA_AST + Table.DIRECT_IN + " (" + sCols + ") values (" + sParam + ")";

		Object[] aParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, aParam);
	}

	@Synchronized
	public static ImmutablePair<String, Object[]> saveDirectInDetailSql(SaveDirectInDetail rd, String oid, String directInOid) {

		List<String> cols = Lists.newArrayList("oid",  "item_oid", "direct_in_oid");
		List<String> param = Lists.newArrayList("?", "?", "?");
		List<Object> data = Lists.newArrayList(oid, rd.getItemOid(), directInOid);
		
		if(StringUtils.isNotBlank(rd.getRemarks())) {
			cols.add("remarks");
			param.add("?");
			data.add(rd.getRemarks());
		}
		
		if(StringUtils.isNotBlank(rd.getSerialNo())) {
			cols.add("serial_no");
			param.add("?");
			data.add(rd.getSerialNo());
		}
		
		if(rd.getExpiryDuration() != null) {
			cols.add("expiry_duration");
			param.add("?");
			data.add(rd.getExpiryDuration());
		}
		
		if(StringUtils.isNotBlank(rd.getPreviousTag())) {
			cols.add("previous_tag");
			param.add("?");
			data.add(rd.getPreviousTag());
		}
		

		
		if(rd.getAllocationDate() != null) {
			cols.add("allocation_date");
			param.add("to_date(?, 'YYYY-MM-DD')::date");
			data.add(DateTimeFormat.forPattern(Constant.JAVA_DATE_FORMAT).print(rd.getAllocationDate()));
		}
		
		String sCols = Joiner.on(",").join(cols);
		String sParam = Joiner.on(",").join(param);
		String sql = "insert into " + Table.SCHEMA_AST + Table.DIRECT_IN_LINE + " (" + sCols + ") values (" + sParam + ")";

		Object[] aParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, aParam);
	}
	
	
	@Synchronized
	public static ImmutablePair<String, Object[]> getAllUniqueCode(String code) {
		
		List<Object> data = Lists.newArrayList(code);
		String sql = "select count(*) from " + Table.SCHEMA_AST + Table.DIRECT_IN + " where 1 = 1 and code = ?"; 

		Object[] aParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, aParam);
	}
	
}
