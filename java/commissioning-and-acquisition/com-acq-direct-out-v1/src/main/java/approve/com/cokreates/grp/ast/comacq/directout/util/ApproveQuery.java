package com.cokreates.grp.ast.comacq.directout.util;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.joda.time.DateTime;

import com.cokreates.grp.ast.comacq.directout.model.ApproveDirectOut;
import com.cokreates.grp.ast.comacq.directout.model.ApproveDirectOutDetail;
import com.cokreates.grp.ast.comacq.directout.request.ApproveRequest;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.util.constant.Constant;
import com.cokreates.grp.util.constant.Table;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import lombok.Synchronized;

public class ApproveQuery {
	
	@Synchronized
	public static ImmutablePair<String, Object[]> updateDirectOutSql(AuthUser user, ApproveRequest request, String assetAllocOid) {
		
		ImmutablePair<String, String> now = Constant.getDateTime(new DateTime());
		List<String> cols = Lists.newArrayList( "asset_alloc_oid = ?", "status = ?",  "decision_by = ?", "decision_on = " + now.getLeft()
				+ ", updated_by = ?" , "updated_on = " + now.getLeft());
		
		List<Object> data = Lists.newArrayList(assetAllocOid, Constant.DO_APPROVED, user.getUserOid(),now.getRight(), user.getUserOid(), now.getRight());
		
		
		String sCols = Joiner.on(",").join(cols);
		String sql = "update " + Table.SCHEMA_AST + Table.DIRECT_OUT + " set " + sCols + " where 1 = 1 and oid = ? and office_oid = ? and status = ?";
		data.add(request.getBody().getData().getOid());
		data.add(user.getOfficeOid());
		data.add(Constant.DO_PENDING);
		
		Object[] queryParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, queryParam);
	}
	
	@Synchronized
	public static ImmutablePair<String, Object[]> getDirectOutSql(String oid) {
		
		String sql = "select  "
			+ " t.user_oid as endUserOid, "
			+ " t.purpose_oid as purposeOid,"
			+ " t.office_unit_oid as officeUnitOid,"
			+ " t.office_unit_post_oid as officeUnitPostOid, "
			+ " t.allocation_date as allocationdate "
			+ " from " + Table.SCHEMA_AST + Table.DIRECT_OUT 
			+ " t where 1 = 1 and t.oid = ?";

		List<Object> data = Lists.newArrayList(oid);
		
		Object[] aParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, aParam);
	}
	
	@Synchronized
	public static ImmutablePair<String, Object[]> getDirectOutDetailSql(String oid) {
		
		String sql = "select  "
			+ " t.oid as oid, t.asset_oid as assetOid, "
			+ " t.remarks as remarks "
			+ " from " + Table.SCHEMA_AST + Table.DIRECT_OUT_LINE 
			+ " t where 1 = 1 and t.direct_out_oid = ?";

		List<Object> data = Lists.newArrayList(oid);
		
		Object[] aParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, aParam);
	}
	
	
	@Synchronized
	public static ImmutablePair<String, Object[]> saveAssetAllocationSql(AuthUser user, ApproveDirectOut body, String oid, String uniqueCode, String status) {

		ImmutablePair<String, String> now = Constant.getDateTime(new DateTime());
		ImmutablePair<String, String> allocDate = Constant.getDateTime(body.getAllocationDate());
		
		List<String> cols = Lists.newArrayList("oid", "code", "status", "allocation_date", "allocator_oid", "end_user_oid", "created_by","created_on", "office_oid", "allocation_type_oid", "office_unit_oid", "description", "office_unit_post_oid" );
		List<String> param = Lists.newArrayList("?", "?", "?" ,allocDate.getLeft(), "?", "?", "?", now.getLeft(), "?", "?", "?", "?", "?");
		List<Object> data = Lists.newArrayList(oid, uniqueCode, status, allocDate.getRight(), user.getUserOid(), body.getEndUserOid(),
				user.getUserOid(), now.getRight(), user.getOfficeOid(), body.getPurposeOid(), body.getOfficeUnitOid(), "Direct Out", body.getOfficeUnitPostOid());


		String sCols = Joiner.on(",").join(cols);
		String sParam = Joiner.on(",").join(param);
		String sql = "insert into " + Table.SCHEMA_AST + Table.ASSET_ALLOC + " (" + sCols + ") values (" + sParam + ")";

		Object[] aParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, aParam);
	}
	
	@Synchronized
	public static ImmutablePair<String, Object[]> saveAssetAllocationDetailSql(ApproveDirectOutDetail rd, String oid, String assetAllocationOid, String assetOid) {

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
	
	@Synchronized
	public static ImmutablePair<String, Object[]> updateAssetInfoSql(AuthUser user, String oid) {
		
		ImmutablePair<String, String> now = Constant.getDateTime(new DateTime());
		
		List<String> cols = Lists.newArrayList( "status = ?", "updated_by = ?" , "updated_on = " + now.getLeft());
		
		List<Object> data = Lists.newArrayList( Constant.AI_STATUS_IN_USE, user.getUserOid(), now.getRight());
		
		String sCols = Joiner.on(",").join(cols);
		String sql = "update " + Table.SCHEMA_AST + Table.ASSET_INFO + " set " + sCols + " where 1 = 1 and oid = ? and status = ?";
		data.add(oid);
		data.add(Constant.AI_STATUS_DIRECT_OUT_PENDING);
		
		Object[] queryParam = data.toArray(new Object[data.size()]);
		
		return new ImmutablePair<String, Object[]>(sql, queryParam);
		
	}
	


}
