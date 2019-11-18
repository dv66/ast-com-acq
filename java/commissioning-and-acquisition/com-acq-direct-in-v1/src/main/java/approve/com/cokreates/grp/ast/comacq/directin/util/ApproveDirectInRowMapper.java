package com.cokreates.grp.ast.comacq.directin.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.cokreates.grp.ast.comacq.directin.model.GetDirectIn;


public class ApproveDirectInRowMapper implements RowMapper<GetDirectIn> {
	
	public GetDirectIn mapRow(ResultSet rs, int rowNum) throws SQLException {
		GetDirectIn entity = new GetDirectIn();
		entity.setDirectInType(rs.getString("directInType"));
		entity.setEndUserOid(rs.getString("endUserOid"));
		entity.setOfficeUnitOid(rs.getString("officeUnitOid"));
		entity.setPurposeOid(rs.getString("purposeOid"));
		entity.setOfficeUnitPostOid(rs.getString("officeUnitPostOid"));

		return entity;
	}
	
}
