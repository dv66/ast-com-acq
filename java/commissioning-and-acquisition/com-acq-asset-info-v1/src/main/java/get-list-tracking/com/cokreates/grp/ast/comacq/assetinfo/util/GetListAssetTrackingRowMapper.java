package com.cokreates.grp.ast.comacq.assetinfo.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cokreates.grp.ast.comacq.assetinfo.model.AssetListTracking;



public class GetListAssetTrackingRowMapper implements RowMapper<AssetListTracking> {
	
	public AssetListTracking mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		AssetListTracking entity = new AssetListTracking();
		
		
		entity.setItemOid(rs.getString("itemOid"));
		entity.setAssetOid(rs.getString("assetOid"));
		entity.setAssetCode(rs.getString("assetCode"));
		entity.setAssetStatus(rs.getString("assetStatus"));
		
		entity.setRecievedBy(rs.getString("recievedBy"));
//		entity.setRecievedByNameBn(rs.getString("recievedByNameBn"));
//		entity.setRecievedByNameEn(rs.getString("recievedByNameEn"));
		
//		entity.setDesignationNameBn(rs.getString("designationNameBn"));
//		entity.setDesignationNameEn(rs.getString("designationNameEn"));
//
//		entity.setOfficeUnitNameEn(rs.getString("officeUnitNameEn"));
//		entity.setOfficeUnitNameBn(rs.getString("officeUnitNameBn"));
		
		entity.setReqTypeEn(rs.getString("reqTypeEn"));
		entity.setReqTypeBn(rs.getString("reqTypeBn"));
		
//		entity.setItemCategoryOid(rs.getString("itemCategoryOid"));
//		entity.setItemCategoryNameBn(rs.getString("itemCategoryNameBn"));
//		entity.setItemCategoryNameEn(rs.getString("itemCategoryNameEn"));
//		entity.setItemGroupOid(rs.getString("itemGroupOid"));
//		entity.setItemGroupNameBn(rs.getString("itemGroupNameBn"));
//		entity.setItemGroupNameEn(rs.getString("itemGroupNameEn" ));
//		entity.setItemNameBn(rs.getString("itemNameBn"));
//		entity.setItemNameEn(rs.getString("itemNameEn"));
		
//		if (rs.getDate("receivedAt") != null) {
//			entity.setReceivedAt(new DateTime(rs.getDate("receivedAt").getTime()));
//	    }
		return entity;
	}
	
}
