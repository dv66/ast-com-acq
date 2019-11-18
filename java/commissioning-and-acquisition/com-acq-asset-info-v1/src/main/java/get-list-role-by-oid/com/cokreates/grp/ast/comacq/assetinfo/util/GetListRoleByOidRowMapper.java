package com.cokreates.grp.ast.comacq.assetinfo.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cokreates.grp.ast.comacq.assetinfo.model.GetListRoleByOid;




public class GetListRoleByOidRowMapper implements RowMapper<GetListRoleByOid> {
	
	public GetListRoleByOid mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		GetListRoleByOid entity = new GetListRoleByOid();
		
		entity.setGrpUserOid(rs.getString("grpUserOid"));
		entity.setNameEn(rs.getString("nameEn"));
		entity.setNameBn(rs.getString("nameBn"));

		return entity;
	}
	
}

  