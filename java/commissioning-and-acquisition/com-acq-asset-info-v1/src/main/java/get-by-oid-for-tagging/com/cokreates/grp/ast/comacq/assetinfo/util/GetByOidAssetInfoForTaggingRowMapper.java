package com.cokreates.grp.ast.comacq.assetinfo.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cokreates.grp.ast.comacq.assetinfo.response.GetByOidForTagging;
public class GetByOidAssetInfoForTaggingRowMapper implements RowMapper<GetByOidForTagging> {
	
	public GetByOidForTagging mapRow(ResultSet rs, int rowNum) throws SQLException {
		GetByOidForTagging entity = new GetByOidForTagging();
		entity.setCode(rs.getString("code"));
		entity.setOid(rs.getString("oid"));
		entity.setDescription(rs.getString("description"));
		entity.setStatus(rs.getString("status"));
		entity.setTempItemOid(rs.getString("tempItemOid"));
		entity.setItemOid(rs.getString("itemOid"));
		entity.setTempItemCode(rs.getString("tempItemCode"));
		
		return entity;
	}
	
}
