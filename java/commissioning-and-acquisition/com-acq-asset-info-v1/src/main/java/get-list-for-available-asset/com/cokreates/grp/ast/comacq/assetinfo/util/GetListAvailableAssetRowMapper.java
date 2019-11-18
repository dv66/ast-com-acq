package com.cokreates.grp.ast.comacq.assetinfo.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cokreates.grp.ast.comacq.assetinfo.model.GetListAvailableAssetInfo;


public class GetListAvailableAssetRowMapper implements RowMapper<GetListAvailableAssetInfo> {
	
	public GetListAvailableAssetInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		GetListAvailableAssetInfo entity = new GetListAvailableAssetInfo();
		entity.setAssetOid(rs.getString("assetOid"));
		entity.setCode(rs.getString("code"));
		entity.setItemOid(rs.getString("itemOid"));
		return entity;
	}
	
}
