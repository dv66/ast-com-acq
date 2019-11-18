package com.cokreates.grp.ast.comacq.assetinfo.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;

import com.cokreates.grp.ast.comacq.assetinfo.response.ExpertDecisionDetail;

public class ExpertDecisionRowMapper implements RowMapper<ExpertDecisionDetail>{

	@Override
	public ExpertDecisionDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
		ExpertDecisionDetail expertDecision = new ExpertDecisionDetail();
		expertDecision.setCode(rs.getString("Code"));
		expertDecision.setDecisionBy(rs.getString("DecisionBy"));
		expertDecision.setStatus(rs.getString("Status"));
		expertDecision.setCost(rs.getString("Cost"));
		expertDecision.setVendorName(rs.getString("VendorName"));
		expertDecision.setRemarks(rs.getString("Remarks"));
		expertDecision.setDisposalProcess(rs.getString("DisposalProcess"));
		if(rs.getDate("DecisionOn") != null) {
			expertDecision.setDecisionOn(new DateTime(rs.getDate("DecisionOn").getTime()));
		}
		if(rs.getDate("MaintenanceDate") != null) {
			expertDecision.setMaintenanceDate(new DateTime(rs.getDate("MaintenanceDate").getTime()));
		}
		
		return expertDecision;
	}

}
