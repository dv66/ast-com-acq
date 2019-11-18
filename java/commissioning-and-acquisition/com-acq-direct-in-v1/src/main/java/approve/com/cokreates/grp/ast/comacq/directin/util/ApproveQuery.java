package com.cokreates.grp.ast.comacq.directin.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.joda.time.DateTime;

import com.cokreates.grp.ast.comacq.directin.model.ApproveDirectInDetail;
import com.cokreates.grp.ast.comacq.directin.model.GetDirectIn;
import com.cokreates.grp.ast.comacq.directin.request.ApproveRequest;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.util.constant.Constant;
import com.cokreates.grp.util.constant.Table;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import lombok.Synchronized;

public class ApproveQuery {
	
	@Synchronized
	public static ImmutablePair<String, Object[]> updateDirectInSql(AuthUser user, ApproveRequest request) {
		
		ImmutablePair<String, String> now = Constant.getDateTime(new DateTime());
		List<String> cols = Lists.newArrayList("status = ?",  "decision_by = ?", "decision_on = " + now.getLeft()
				+ ", updated_by = ?" , "updated_on = " + now.getLeft());
		
		List<Object> data = Lists.newArrayList(Constant.DI_APPROVED, user.getUserOid(),now.getRight(), user.getUserOid(), now.getRight());
		
		
		String sCols = Joiner.on(",").join(cols);
		String sql = "update " + Table.SCHEMA_AST + Table.DIRECT_IN + " set " + sCols + " where 1 = 1 and oid = ? and office_oid = ? and status = ? ";
		data.add(request.getBody().getData().getOid());
		data.add(user.getOfficeOid());
		data.add(Constant.DI_PENDING);
		
		Object[] queryParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, queryParam);
	}
	
	@Synchronized
	public static ImmutablePair<String, Object[]> updateDirectInDetailSql(String oid, String assetOid, String allocOid) {
		
		List<String> cols = Lists.newArrayList("asset_oid = ?", "asset_alloc_oid = ?");
		
		List<Object> data = Lists.newArrayList(assetOid, allocOid);
		
		
		String sCols = Joiner.on(",").join(cols);
		String sql = "update " + Table.SCHEMA_AST + Table.DIRECT_IN_LINE + " set " + sCols + " where 1 = 1 and oid = ? ";
		data.add(oid);
		
		Object[] queryParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, queryParam);
	}
	
	@Synchronized
	public static ImmutablePair<String, Object[]> saveAssetInfoSql(AuthUser user, String  itemOid, String oid, String code, String status) {

		ImmutablePair<String, String> now = Constant.getDateTime(new DateTime());
		
		List<String> cols = Lists.newArrayList("oid", "code", "status", "in_type",  "item_oid", "created_by", "created_on", "updated_by", "updated_on", "office_oid");
		List<String> param = Lists.newArrayList("?", "?", "?", "?", "?", "?", now.getLeft(), "?", now.getLeft(), "?");
		List<Object> data = Lists.newArrayList(oid, code, status, Constant.AI_IN_TYPE_DIRECT_IN, itemOid, user.getUserOid(), now.getRight(), user.getUserOid(), now.getRight(), user.getOfficeOid());
		

		String sCols = Joiner.on(",").join(cols);
		String sParam = Joiner.on(",").join(param);
		String sql = "insert into " + Table.SCHEMA_AST + Table.ASSET_INFO + " (" + sCols + ") values (" + sParam + ")";

		Object[] aParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, aParam);
	}
	
	@Synchronized
	public static ImmutablePair<String, Object[]> saveAssetInfoDetailSql(String oid, String assetOid, ApproveDirectInDetail body) {
		
		
		List<String> cols = Lists.newArrayList("oid", "asset_information_oid");
		List<String> param = Lists.newArrayList("?", "?");
		List<Object> data = Lists.newArrayList(oid, assetOid);
		
		if(StringUtils.isNotBlank(body.getSerialNo())) {
			cols.add("serial_no");
			param.add("?");
			data.add(body.getSerialNo());
		}
		
		if(StringUtils.isNotBlank(body.getPreviousTag())) {
			cols.add("previous_tag");
			param.add("?");
			data.add(body.getPreviousTag());
		}
		
		if(body.getExpiryDuration() != null) {
			cols.add("expiry_duration");
			param.add("?");
			data.add(body.getExpiryDuration());
		}
		
		if(StringUtils.isNotBlank(body.getRemarks())) {
			cols.add("remarks");
			param.add("?");
			data.add(body.getRemarks());
		}
		

		String sCols = Joiner.on(",").join(cols);
		String sParam = Joiner.on(",").join(param);
		String sql = "insert into " + Table.SCHEMA_AST + Table.ASSET_INFO_DETAILS + " (" + sCols + ") values (" + sParam + ")";

		Object[] aParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, aParam);
	}
	
	@Synchronized
	public static ImmutablePair<String, Object[]> getDirectInLineSql(String oid) {
		
		String sql = "select t.oid, t.item_oid as itemOid, t.previous_tag as previousTag, "
			+ " t.remarks as remarks, "
			+ " t.serial_no as serialNo, "
			+ " t.expiry_duration as expiryDuration,"
			+ " t.allocation_date as allocationdate "
			+ " from " + Table.SCHEMA_AST + Table.DIRECT_IN_LINE 
			+ " t where 1 = 1 and t.direct_in_oid = ?";

		List<Object> data = Lists.newArrayList(oid);
		
		Object[] aParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, aParam);
	}
	
	@Synchronized
	public static ImmutablePair<String, Object[]> getDirectInSql(String oid) {
		
		String sql = "select t.direct_in_type as directInType, "
			+ " t.user_oid as endUserOid,"
			+ " t.purpose_oid as purposeOid,"
			+ " t.office_unit_oid as officeUnitOid, t.office_unit_post_oid as officeUnitPostOid "
			+ " from " + Table.SCHEMA_AST + Table.DIRECT_IN 
			+ " t where 1 = 1 and t.oid = ?";

		List<Object> data = Lists.newArrayList(oid);
		
		Object[] aParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, aParam);
	}
	
	@Synchronized
	public static ImmutablePair<String, Object[]> getAssetUniqueCode(String code) {
		
		List<Object> data = Lists.newArrayList(code);
		String sql = "select count(*) from " + Table.SCHEMA_AST + Table.ASSET_INFO + " where 1 = 1 and code = ?"; 

		Object[] aParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, aParam);
	}
	
	@Synchronized
	public static ImmutablePair<String, Object[]> saveAssetAllocationSql(AuthUser user, ApproveDirectInDetail body, String oid, String uniqueCode, String status, GetDirectIn directIn ) {

		ImmutablePair<String, String> now = Constant.getDateTime(new DateTime());
		ImmutablePair<String, String> allocDate = Constant.getDateTime(body.getAllocationDate());
		
		List<String> cols = Lists.newArrayList("oid", "code", "status", "allocation_date", "allocator_oid", "end_user_oid", "created_by","created_on", "office_oid", "allocation_type_oid", "office_unit_oid", "description", "office_unit_post_oid" );
		List<String> param = Lists.newArrayList("?", "?", "?" ,allocDate.getLeft(), "?", "?", "?", now.getLeft(), "?", "?", "?", "?", "?");
		List<Object> data = Lists.newArrayList(oid, uniqueCode, status, allocDate.getRight(), user.getUserOid(), directIn.getEndUserOid(),
				user.getUserOid(), now.getRight(), user.getOfficeOid(), directIn.getPurposeOid(), directIn.getOfficeUnitOid(), "Direct Out", directIn.getOfficeUnitPostOid());


		String sCols = Joiner.on(",").join(cols);
		String sParam = Joiner.on(",").join(param);
		String sql = "insert into " + Table.SCHEMA_AST + Table.ASSET_ALLOC + " (" + sCols + ") values (" + sParam + ")";

		Object[] aParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, aParam);
	}
	
	@Synchronized
	public static ImmutablePair<String, Object[]> saveAssetAllocationDetailSql(ApproveDirectInDetail rd, String oid, String assetAllocationOid, String assetOid) {

		List<String> cols = Lists.newArrayList("oid", "asset_oid", "asset_allocation_oid");
		List<String> param = Lists.newArrayList("?", "?", "?");
		List<Object> data = Lists.newArrayList(oid, assetOid ,assetAllocationOid);		
		
		String sCols = Joiner.on(",").join(cols);
		String sParam = Joiner.on(",").join(param);
		String sql = "insert into " + Table.SCHEMA_AST + Table.ASSET_ALLOC_DETAILS + " (" + sCols + ") values (" + sParam + ")";

		Object[] aParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, aParam);
	}
	
	@Synchronized
	public static ImmutablePair<String, Object[]> getAllocationUniqueCode(String code) {
		
		List<Object> data = Lists.newArrayList(code);
		String sql = "select count(*) from " + Table.SCHEMA_AST + Table.ASSET_ALLOC + " where 1 = 1 and code = ?"; 

		Object[] aParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, aParam);
	}
	


}
