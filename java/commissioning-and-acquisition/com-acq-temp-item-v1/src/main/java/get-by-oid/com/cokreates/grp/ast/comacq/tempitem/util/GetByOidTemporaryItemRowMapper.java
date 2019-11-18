package com.cokreates.grp.ast.comacq.tempitem.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;

import com.cokreates.grp.ast.comacq.tempitem.response.GetByOidResponseBody;

public class GetByOidTemporaryItemRowMapper implements RowMapper<GetByOidResponseBody> {
	
	public GetByOidResponseBody mapRow(ResultSet rs, int rowNum) throws SQLException {
		GetByOidResponseBody entity = new GetByOidResponseBody();
		entity.setCode(rs.getString("code"));
		entity.setVendorOid(rs.getString("vendorOid"));
		entity.setVendorNameEn(rs.getString("vendorNameEn"));
		entity.setVendorNameBn(rs.getString("vendorNameBn"));
		entity.setReceivedBy(rs.getString("receivedBy"));
		entity.setDescription(rs.getString("tempDescription"));
		entity.setStatus(rs.getString("status"));
		entity.setQcBy(rs.getString("qcBy"));
		entity.setAssetAddedBy(rs.getString("assetAddedBy"));
		entity.setChalanNo(rs.getString("chalanNo"));
		entity.setWorkorderNo(rs.getString("workorderNo"));
		entity.setWorkOrderOid(rs.getString("workOrderOid"));
		entity.setContractNo(rs.getString("contractNo"));
		entity.setProcurementMethod(rs.getString("procurementMethod"));
		entity.setDecision_on_qc_note(rs.getString("decisionOnQcNote"));
		entity.setDecisionOnQcBy(rs.getString("decisionOnQcBy"));
		entity.setQcReportPath(rs.getString("qcReportPath"));
		
		if(rs.getDate("receivedAt") != null) {
			entity.setReceivedAt(new DateTime(rs.getDate("receivedAt").getTime()));
		}
		if(rs.getDate("chalanDate") != null) {
			entity.setChalanDate(new DateTime(rs.getDate("chalanDate").getTime()));
		}
		if(rs.getDate("workorderDate") != null) {
			entity.setWorkorderDate(new DateTime(rs.getDate("workorderDate").getTime()));
		}
		if(rs.getDate("qcOn") != null) {
			entity.setQcOn(new DateTime(rs.getDate("qcOn").getTime()));
		}
		if(rs.getDate("assetAddedOn") != null) {
			entity.setAssetAddedOn(new DateTime(rs.getDate("assetAddedOn").getTime()));
		}
		if(rs.getDate("contractSigningDate") != null) {
			entity.setContractSigningDate(new DateTime(rs.getDate("contractSigningDate").getTime()));
		}
		if(rs.getDate("decisionOnQcDate") != null) {
			entity.setDecisionOnQcDate(new DateTime(rs.getDate("decisionOnQcDate").getTime()));
		}
		
		
		return entity;
	}
	
}
