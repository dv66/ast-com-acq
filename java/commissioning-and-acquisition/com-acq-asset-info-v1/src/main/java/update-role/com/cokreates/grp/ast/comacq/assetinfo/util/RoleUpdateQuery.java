package com.cokreates.grp.ast.comacq.assetinfo.util;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.joda.time.DateTime;

import com.cokreates.grp.ast.comacq.assetinfo.model.RoleDetailsUpdate;
import com.cokreates.grp.ast.comacq.assetinfo.model.RoleUpdate;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.util.constant.Constant;
import com.cokreates.grp.util.constant.Table;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import lombok.Synchronized;

public class RoleUpdateQuery {

	@Synchronized
	public static ImmutablePair<String, Object[]> getEmployeeRoleSql(String oid) {
		
		String sql = "select oid, grp_role_oid " + 
				"from " + Table.SCHEMA_SEC + Table.GRP_USER_ROLE + " as gur " + 
				"where grp_user_oid = ? and active_status = ? and grp_module_oid = ?";
		
		List<Object> data = Lists.newArrayList(oid, Constant.GUR_ACTIVE_STATUS_YES, "9");
		
		Object[] aParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, aParam);
	}
	
	@Synchronized
	public static ImmutablePair<String, Object[]> updateEmployeeRole(AuthUser user, RoleUpdate roleUpdate, RoleDetailsUpdate roleDetails){
	
		ImmutablePair<String, String> now = Constant.getDateTime(new DateTime());
		List<String> cols = Lists.newArrayList("active_status = ?", "updated_by = ?", "updated_on = " + now.getLeft());
		List<Object> data = Lists.newArrayList(Constant.GUR_ACTIVE_STATUS_NO, user.getUserOid(), now.getRight());
		
		String sCols = Joiner.on(",").join(cols);
		String sql = "update " + Table.SCHEMA_SEC + Table.GRP_USER_ROLE + " set " + sCols + " where 1 = 1 and grp_user_oid = ? and active_status = ? and grp_role_oid = ? and grp_module_oid = ?";
		data.add(roleUpdate.getGrpUserOid());
		data.add(Constant.GUR_ACTIVE_STATUS_YES);
		data.add(roleDetails.getGrpRoleOid());
		data.add("9");
		
		Object[] queryParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, queryParam);
	
	}
	
	@Synchronized
	public static ImmutablePair<String, Object[]> saveRoleInfoSql(AuthUser user, String oid, String grpUser, RoleDetailsUpdate roleDetails) {
		
		String status = Constant.GUR_DEFAULT_STATUS_YES;
		if (roleDetails.getDefaultStatus() != null) {
			if (!roleDetails.getDefaultStatus().equalsIgnoreCase(Constant.GUR_DEFAULT_STATUS_YES)) {
				status = Constant.GUR_DEFAULT_STATUS_NO;
			}
		} else {
			status = Constant.GUR_DEFAULT_STATUS_NO;
		}
		

		ImmutablePair<String, String> now = Constant.getDateTime(new DateTime());
		
		List<String> cols = Lists.newArrayList("oid", "default_status", "grp_role_oid", "grp_user_oid", "active_status", "grp_module_oid", "created_by", "created_on", "office_oid", "menu_json");
		List<String> param = Lists.newArrayList("?", "?", "?", "?", "?", "?", "?", now.getLeft(), "?", "?");
		List<Object> data = Lists.newArrayList(oid, status, roleDetails.getGrpRoleOid(), grpUser, Constant.GUR_ACTIVE_STATUS_YES, "9", user.getUserOid() , now.getRight(), user.getOfficeOid(), "{}");
		

		String sCols = Joiner.on(",").join(cols);
		String sParam = Joiner.on(",").join(param);
		String sql = "insert into " + Table.SCHEMA_SEC + Table.GRP_USER_ROLE + " (" + sCols + ") values (" + sParam + ")";

		Object[] aParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, aParam);
	}

}
