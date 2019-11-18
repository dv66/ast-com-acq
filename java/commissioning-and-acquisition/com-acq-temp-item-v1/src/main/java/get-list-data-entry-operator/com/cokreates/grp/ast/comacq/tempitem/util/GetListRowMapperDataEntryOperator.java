package com.cokreates.grp.ast.comacq.tempitem.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;

import com.cokreates.grp.ast.comacq.tempitem.model.TempItemDataEntryOperator;

public class GetListRowMapperDataEntryOperator implements RowMapper<TempItemDataEntryOperator> {
	
	public TempItemDataEntryOperator mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		TempItemDataEntryOperator entity = new TempItemDataEntryOperator();
		
		entity.setOid(rs.getString("tempOid"));
		entity.setCode(rs.getString("tempCode"));
		entity.setProcurementMethod(rs.getString("procurementMethod"));
		entity.setDescription(rs.getString("description"));
		entity.setStatus(rs.getString("tempStatus"));
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
