package com.cokreates.grp.ast.comacq.directout.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;

import com.cokreates.grp.ast.comacq.directout.model.ApproveDirectOut;


public class ApproveRowMapper implements RowMapper<ApproveDirectOut> {
	
	public ApproveDirectOut mapRow(ResultSet rs, int rowNum) throws SQLException {
		ApproveDirectOut entity = new ApproveDirectOut();
		entity.setEndUserOid(rs.getString("endUserOid"));
		entity.setOfficeUnitOid(rs.getString("officeUnitOid"));
		entity.setPurposeOid(rs.getString("purposeOid"));
		entity.setOfficeUnitPostOid(rs.getString("officeUnitPostOid"));
		
		if(rs.getDate("allocationDate") != null) {
			entity.setAllocationDate(new DateTime(rs.getDate("allocationDate").getTime()));
		}


		return entity;
	}
	
}
