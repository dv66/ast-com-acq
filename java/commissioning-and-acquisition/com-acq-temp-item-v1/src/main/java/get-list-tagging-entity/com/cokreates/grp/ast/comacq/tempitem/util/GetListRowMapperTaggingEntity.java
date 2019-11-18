package com.cokreates.grp.ast.comacq.tempitem.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;

import com.cokreates.grp.ast.comacq.tempitem.model.TempItemTaggingEntity;

public class GetListRowMapperTaggingEntity implements RowMapper<TempItemTaggingEntity> {
	
	public TempItemTaggingEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		TempItemTaggingEntity entity = new TempItemTaggingEntity();
		
		entity.setOid(rs.getString("tempOid"));
		entity.setCode(rs.getString("tempCode"));
		entity.setReceivedBy(rs.getString("receivedBy"));
		entity.setStatus(rs.getString("tempStatus"));
		entity.setDescription(rs.getString("description"));
		entity.setVendorOid(rs.getString("vendorOid"));
		entity.setCreatedBy(rs.getString("createdBy"));
		entity.setUpdatedBy(rs.getString("updatedBy"));
		entity.setVendorNameEn(rs.getString("vendorEnglishName"));
		entity.setVendorNameBn(rs.getString("vendorBanglaName"));
		if (rs.getDate("receivedAt") != null) {
			entity.setReceivedAt(new DateTime(rs.getDate("receivedAt").getTime()));
	    }
		if (rs.getDate("createdOn") != null) {
	            entity.setCreatedOn(new DateTime(rs.getDate("createdOn").getTime()));
	    }
		if (rs.getDate("updatedOn") != null) {
	            entity.setUpdatedOn(new DateTime(rs.getDate("updatedOn").getTime()));
	    }
		return entity;
	}
	
}
