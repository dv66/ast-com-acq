package com.cokreates.grp.ast.comacq.assetinfo.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cokreates.grp.ast.comacq.assetinfo.model.GetListForTaggingEntityAssetInfo;


public class GetListForTaggingEntityRowMapper implements RowMapper<GetListForTaggingEntityAssetInfo> {
	
	public GetListForTaggingEntityAssetInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		GetListForTaggingEntityAssetInfo entity = new GetListForTaggingEntityAssetInfo();
		entity.setOid(rs.getString("oid"));
		entity.setCode(rs.getString("code"));
		entity.setItemOid(rs.getString("itemOid"));
		entity.setDescription(rs.getString("description"));
		entity.setStatus(rs.getString("status"));
		entity.setTempItemCode(rs.getString("tempItemCode"));
		return entity;
	}
	
}
