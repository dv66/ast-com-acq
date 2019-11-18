package com.cokreates.grp.ast.comacq.assetinfo.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cokreates.grp.ast.comacq.assetinfo.model.RoleDetailsUpdate;

public class RoleUpdateRowMapper implements RowMapper<RoleDetailsUpdate> {
	
	public RoleDetailsUpdate mapRow(ResultSet rs, int rowNum) throws SQLException {
		RoleDetailsUpdate entity = new RoleDetailsUpdate();
		entity.setGrpRoleOid(rs.getString("grp_role_oid"));
		return entity;
	}
	
}
