package com.cokreates.grp.ast.comacq.directout.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;

import com.cokreates.grp.ast.comacq.directout.response.GetByOidResponseBody;

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
		entity.setAllocationOid(rs.getString("allocationOid"));
		entity.setAllocationCode(rs.getString("allocationCode"));
		entity.setPurposeOid(rs.getString("purposeOid"));
		entity.setPurposeNameEn(rs.getString("purposeNameEn"));
		entity.setPurposeNameBn(rs.getString("purposeNameBn"));
		entity.setOfficeUnitPostOid(rs.getString("officeUnitPostOid"));

		
		if(rs.getDate("requestedOn") != null) {
			entity.setRequestedOn(new DateTime(rs.getDate("requestedOn").getTime()));
		}
		if(rs.getDate("decisionOn") != null) {
			entity.setDecisionOn(new DateTime(rs.getDate("decisionOn").getTime()));
		}
		
		if(rs.getDate("allocationDate") != null) {
			entity.setAllocationDate(new DateTime(rs.getDate("allocationDate").getTime()));
		}

		return entity;
	}
	
}
