package com.cokreates.grp.ast.comacq.directout.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cokreates.grp.ast.comacq.directout.response.GetByOid;

public class GetByOidRowMapper implements RowMapper<GetByOid> {
	
	public GetByOid mapRow(ResultSet rs, int rowNum) throws SQLException {
		GetByOid entity = new GetByOid();
		entity.setOid(rs.getString("oid"));
		entity.setRemarks(rs.getString("remarks"));
		entity.setAssetOid(rs.getString("assetOid"));
		entity.setAssetCode(rs.getString("assetCode"));

		return entity;
	}
	
}
