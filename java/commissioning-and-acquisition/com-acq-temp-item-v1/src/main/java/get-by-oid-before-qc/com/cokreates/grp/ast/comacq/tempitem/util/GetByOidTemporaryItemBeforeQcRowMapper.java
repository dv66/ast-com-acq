package com.cokreates.grp.ast.comacq.tempitem.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;

import com.cokreates.grp.ast.comacq.tempitem.response.GetByOidBeforeQcResponseBody;

public class GetByOidTemporaryItemBeforeQcRowMapper implements RowMapper<GetByOidBeforeQcResponseBody> {
	
	public GetByOidBeforeQcResponseBody mapRow(ResultSet rs, int rowNum) throws SQLException {
		GetByOidBeforeQcResponseBody entity = new GetByOidBeforeQcResponseBody();
		entity.setCode(rs.getString("code"));
		entity.setWorkOrderOid(rs.getString("workOrderOid"));
		entity.setVendorOid(rs.getString("vendorOid"));
		entity.setVendorNameEn(rs.getString("vendorNameEn"));
		entity.setVendorNameBn(rs.getString("vendorNameBn"));
		entity.setReceivedBy(rs.getString("receivedBy"));
		entity.setDescription(rs.getString("description"));
		entity.setStatus(rs.getString("status"));
		entity.setChalanNo(rs.getString("chalanNo"));
		entity.setWorkorderNo(rs.getString("workorderNo"));
		entity.setContractNo(rs.getString("contractNo"));
		entity.setProcurementMethod(rs.getString("procurementMethod"));
//		entity.setDecision_on_qc_note(rs.getString("decisionOnQcNote"));
//		entity.setDecisionOnQcByBn(rs.getString("decisionOnQcByBn"));
//		entity.setDecisionOnQcByEn(rs.getString("decisionOnQcByEn"));
		
		if(rs.getDate("receivedAt") != null) {
			entity.setReceivedAt(new DateTime(rs.getDate("receivedAt").getTime()));
		}
		if(rs.getDate("chalanDate") != null) {
			entity.setChalanDate(new DateTime(rs.getDate("chalanDate").getTime()));
	    }
	    if(rs.getDate("workorderDate") != null) {
	    entity.setWorkorderDate(new DateTime(rs.getDate("workorderDate").getTime()));
	    }
	    if(rs.getDate("contractSigningDate") != null) {
			entity.setContractSigningDate(new DateTime(rs.getDate("contractSigningDate").getTime()));
		}
//		if(rs.getDate("decisionOnQcDate") != null) {
//			entity.setDecisionOnQcDate(new DateTime(rs.getDate("decisionOnQcDate").getTime()));
//		}
		
		return entity;
	}
	
}
