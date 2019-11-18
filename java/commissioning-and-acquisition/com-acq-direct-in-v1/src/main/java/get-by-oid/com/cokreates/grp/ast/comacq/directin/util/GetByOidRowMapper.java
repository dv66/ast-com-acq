package com.cokreates.grp.ast.comacq.directin.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;

import com.cokreates.grp.ast.comacq.directin.response.GetByOid;

public class GetByOidRowMapper implements RowMapper<GetByOid> {
	
	public GetByOid mapRow(ResultSet rs, int rowNum) throws SQLException {
		GetByOid entity = new GetByOid();
		entity.setOid(rs.getString("oid"));
		entity.setItemOid(rs.getString("itemOid"));
		entity.setRemarks(rs.getString("remarks"));
		entity.setSerialNo(rs.getString("serialNo"));
		entity.setAssetOid(rs.getString("assetOid"));
		entity.setAssetCode(rs.getString("assetCode"));
		entity.setAllocationOid(rs.getString("allocationOid"));
		entity.setAllocationCode(rs.getString("allocationCode"));
		entity.setPreviousTag(rs.getString("previousTag"));
		entity.setExpiryDuration(rs.getDouble("expiryDuration"));
		
		if(rs.getDate("allocationDate") != null) {
			entity.setAllocationDate(new DateTime(rs.getDate("allocationDate").getTime()));
		}


		return entity;
	}
	
}
