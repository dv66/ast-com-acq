package com.cokreates.grp.ast.comacq.directin.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;

import com.cokreates.grp.ast.comacq.directin.model.ApproveDirectInDetail;


public class ApproveRowMapper implements RowMapper<ApproveDirectInDetail> {
	
	public ApproveDirectInDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
		ApproveDirectInDetail entity = new ApproveDirectInDetail();
		entity.setOid(rs.getString("oid"));
		entity.setItemOid(rs.getString("itemOid"));
		entity.setRemarks(rs.getString("remarks"));
		entity.setSerialNo(rs.getString("serialNo"));
		entity.setPreviousTag(rs.getString("previousTag"));
		entity.setExpiryDuration(rs.getDouble("expiryDuration"));
		
		if(rs.getDate("allocationDate") != null) {
			entity.setAllocationDate(new DateTime(rs.getDate("allocationDate").getTime()));
		}


		return entity;
	}
	
}
