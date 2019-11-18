package com.cokreates.grp.ast.comacq.directin.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;

import com.cokreates.grp.ast.comacq.directin.response.GetByOidResponseBody;

public class GetByOidDirectInRowMapper implements RowMapper<GetByOidResponseBody> {
	
	public GetByOidResponseBody mapRow(ResultSet rs, int rowNum) throws SQLException {
		GetByOidResponseBody entity = new GetByOidResponseBody();
		entity.setOid(rs.getString("oid"));
		entity.setCode(rs.getString("code"));
		entity.setStatus(rs.getString("status"));
		entity.setRequestedBy(rs.getString("requestedBy"));
		entity.setDecisionBy(rs.getString("decisionBy"));
		entity.setRemarks(rs.getString("remarks"));
		entity.setEndUserOid(rs.getString("endUserOid"));
		entity.setOfficeUnitOid(rs.getString("officeUnitOid"));
		entity.setOfficeUnitPostOid(rs.getString("officeUnitPostOid"));
		entity.setPurposeOid(rs.getString("purposeOid"));
		entity.setPurposeNameEn(rs.getString("purposeNameEn"));
		entity.setPurposeNameBn(rs.getString("purposeNameBn"));
		entity.setDirectInType(rs.getString("directInType"));

		
		if(rs.getDate("requestedOn") != null) {
			entity.setRequestedOn(new DateTime(rs.getDate("requestedOn").getTime()));
		}
		if(rs.getDate("decisionOn") != null) {
			entity.setDecisionOn(new DateTime(rs.getDate("decisionOn").getTime()));
		}

		return entity;
	}
	
}
