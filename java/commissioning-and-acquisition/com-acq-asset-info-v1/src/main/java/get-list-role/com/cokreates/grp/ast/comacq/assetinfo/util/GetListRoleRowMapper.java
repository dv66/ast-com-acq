package com.cokreates.grp.ast.comacq.assetinfo.util;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cokreates.grp.ast.comacq.assetinfo.model.RoleList;


public class GetListRoleRowMapper implements RowMapper<RoleList> {
	
	public RoleList mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		RoleList entity = new RoleList();
		
		entity.setOid(rs.getString("oid"));
		entity.setNameEn(rs.getString("nameEn"));
		entity.setNameBn(rs.getString("nameBn"));
		
//		entity.setCode(rs.getString("tempCode"));
//		entity.setReceivedBy(rs.getString("receivedBy"));
//		entity.setReceivedByEn(rs.getString("receivedByEn"));
//		entity.setReceivedByBn(rs.getString("receivedByBn"));
//		entity.setDescription(rs.getString("tempDescription"));
//		entity.setStatus(rs.getString("tempStatus"));
//		entity.setVendorOid(rs.getString("vendorOid"));
//		entity.setCreatedBy(rs.getString("createdBy"));
//		entity.setUpdatedBy(rs.getString("updatedBy"));
//		entity.setVendorNameEn(rs.getString("vendorEnglishName"));
//		entity.setVendorNameBn(rs.getString("vendorBanglaName"));
//		if (rs.getDate("receivedAt") != null) {
//			entity.setReceivedAt(new DateTime(rs.getDate("receivedAt").getTime()));
//	    }
//		if (rs.getDate("createdOn") != null) {
//	            entity.setCreatedOn(new DateTime(rs.getDate("createdOn").getTime()));
//	    }
//		if (rs.getDate("updatedOn") != null) {
//	            entity.setUpdatedOn(new DateTime(rs.getDate("updatedOn").getTime()));
//	    }
		return entity;
	}
	
}
