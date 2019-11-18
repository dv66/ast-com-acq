package com.cokreates.grp.ast.comacq.assetinfo.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.cokreates.grp.ast.comacq.assetinfo.model.DraftGetListAssetInfo;

public class DraftGetListByTempOidRowMapper implements RowMapper<DraftGetListAssetInfo> {
	
	public DraftGetListAssetInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		DraftGetListAssetInfo entity = new DraftGetListAssetInfo();
		entity.setOid(rs.getString("oid"));
		entity.setCode(rs.getString("code"));
//		entity.setItemNameEn(rs.getString("itemNameEn"));
		entity.setItemOid(rs.getString("item_oid"));
		entity.setDescription(rs.getString("description"));
		entity.setStatus(rs.getString("status"));
		entity.setTempItemCode(rs.getString("tempItemCode"));
		entity.setSerialNo(rs.getString("serial_no"));
		entity.setExpiryDuration(rs.getString("expiry_duration"));
		entity.setRemarks(rs.getString("remarks"));
		return entity;
	}
	
}
