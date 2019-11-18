package com.cokreates.grp.ast.comacq.assetinfo.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;

import com.cokreates.grp.ast.comacq.assetinfo.response.GetByOidResponseBody;

public class GetByOidAssetInfoRowMapper implements RowMapper<GetByOidResponseBody> {
	
	public GetByOidResponseBody mapRow(ResultSet rs, int rowNum) throws SQLException {
		GetByOidResponseBody entity = new GetByOidResponseBody();
		
		entity.setCode(rs.getString("Code"));
		entity.setDescription(rs.getString("Description"));
		entity.setCreatedBy(rs.getString("CreatedBy"));
		entity.setUpdatedBy(rs.getString("UpdatedBy"));
		entity.setStatus(rs.getString("Status"));
		entity.setTemporaryItemCode(rs.getString("TempCode"));
//		entity.setItemNameEn(rs.getString("itemNameEn"));
		entity.setItemOid(rs.getString("item_oid"));
		entity.setQcBy(rs.getString("qcBy"));
//		entity.setQcByEn(rs.getString("qcByEn"));
//		entity.setQcByBn(rs.getString("qcByBn"));
		entity.setAssetAddedBy(rs.getString("assetAddedBy"));
//		entity.setAssetAddedByEn(rs.getString("assetAddedByEn"));
//		entity.setAssetAddedByBn(rs.getString("assetAddedByBn"));
		entity.setRemarks(rs.getString("remarks"));
		entity.setSerialNo(rs.getString("serialNo"));
		entity.setExpiryDuration(rs.getString("expiryDuration"));
		
		
		if(rs.getDate("CreatedOn") != null) {
			entity.setCreatedOn(new DateTime(rs.getDate("CreatedOn").getTime()));
		}
		if(rs.getDate("UpdatedOn") != null) {
			entity.setUpdatedOn(new DateTime(rs.getDate("UpdatedOn").getTime()));
		}
		if(rs.getDate("qcOn") != null) {
			entity.setQcOn(new DateTime(rs.getDate("qcOn").getTime()));
		}
		if(rs.getDate("assetAddedOn") != null) {
			entity.setAssetAddedOn(new DateTime(rs.getDate("assetAddedOn").getTime()));
		}
		
		return entity;
	}
	
}
