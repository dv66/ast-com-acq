package com.cokreates.grp.ast.comacq.assetinfo.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cokreates.grp.ast.comacq.assetinfo.response.GetByOidAssetTagResponseBody;


public class GetByOidAssetTagRowMapper implements RowMapper<GetByOidAssetTagResponseBody> {
	
	public GetByOidAssetTagResponseBody mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		GetByOidAssetTagResponseBody entity = new GetByOidAssetTagResponseBody();
		entity.setStatus(rs.getString("status"));
		entity.setTempItemOid(rs.getString("tempItemOid"));		
		return entity;
	}
	
}
