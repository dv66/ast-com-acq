package com.cokreates.grp.ast.comacq.directout.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import com.cokreates.grp.ast.comacq.directout.model.SaveDirectOutDetail;
import com.cokreates.grp.ast.comacq.directout.request.SaveRequest;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.util.constant.Constant;
import com.cokreates.grp.util.constant.Table;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import lombok.Synchronized;

public class SaveQuery {

	@Synchronized
	public static ImmutablePair<String, Object[]> saveDirectOutSql(AuthUser user, SaveRequest request, String oid, String uniqueCode) {

		ImmutablePair<String, String> now = Constant.getDateTime(new DateTime());
		
		List<String> cols = Lists.newArrayList("oid", "code" , "requested_on", "requested_by",  "status",  "created_by","created_on", "updated_by","updated_on", "office_oid");
		List<String> param = Lists.newArrayList("?", "?" , now.getLeft(), "?", "?", "?", now.getLeft(), "?", now.getLeft(),  "?");
		List<Object> data = Lists.newArrayList(oid, uniqueCode, now.getRight(), user.getUserOid(), Constant.DO_PENDING, 
			 user.getUserOid(), now.getRight(), user.getUserOid(), now.getRight(), user.getOfficeOid());
		

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
		
		if(request.getBody().getData().getAllocationDate() != null) {
			cols.add("allocation_date");
			param.add("to_date(?, 'YYYY-MM-DD')::date");
			data.add(DateTimeFormat.forPattern(Constant.JAVA_DATE_FORMAT).print(request.getBody().getData().getAllocationDate()));
		}

		String sCols = Joiner.on(",").join(cols);
		String sParam = Joiner.on(",").join(param);
		String sql = "insert into " + Table.SCHEMA_AST + Table.DIRECT_OUT + " (" + sCols + ") values (" + sParam + ")";

		Object[] aParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, aParam);
	}

	@Synchronized
	public static ImmutablePair<String, Object[]> saveDirectOutDetailSql(SaveDirectOutDetail rd, String oid, String directOutOid) {

		List<String> cols = Lists.newArrayList("oid",  "asset_oid", "direct_out_oid");
		List<String> param = Lists.newArrayList("?", "?", "?");
		List<Object> data = Lists.newArrayList(oid, rd.getAssetOid(), directOutOid);
		
		if(StringUtils.isNotBlank(rd.getRemarks())) {
			cols.add("remarks");
			param.add("?");
			data.add(rd.getRemarks());
		}
		
		
		String sCols = Joiner.on(",").join(cols);
		String sParam = Joiner.on(",").join(param);
		String sql = "insert into " + Table.SCHEMA_AST + Table.DIRECT_OUT_LINE + " (" + sCols + ") values (" + sParam + ")";

		Object[] aParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, aParam);
	}
	
	
	@Synchronized
	public static ImmutablePair<String, Object[]> getAllUniqueCode(String code) {
		
		List<Object> data = Lists.newArrayList(code);
		String sql = "select count(*) from " + Table.SCHEMA_AST + Table.DIRECT_OUT + " where 1 = 1 and code = ?"; 

		Object[] aParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, aParam);
	}
	
	@Synchronized
	public static ImmutablePair<String, Object[]> updateAssetInfoSql(AuthUser user, String oid) {
		
		ImmutablePair<String, String> now = Constant.getDateTime(new DateTime());
		
		List<String> cols = Lists.newArrayList( "status = ?", "updated_by = ?" , "updated_on = " + now.getLeft());
		
		List<Object> data = Lists.newArrayList( Constant.AI_STATUS_DIRECT_OUT_PENDING, user.getUserOid(), now.getRight());
		
		String sCols = Joiner.on(",").join(cols);
		String sql = "update " + Table.SCHEMA_AST + Table.ASSET_INFO + " set " + sCols + " where 1 = 1 and oid = ? and status = ?";
		data.add(oid);
		data.add(Constant.AI_STATUS_AVAILABLE);
		
		Object[] queryParam = data.toArray(new Object[data.size()]);
		
		return new ImmutablePair<String, Object[]>(sql, queryParam);
		
	}
	
}
