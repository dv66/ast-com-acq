package com.cokreates.grp.ast.comacq.assetinfo.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;

import com.cokreates.grp.ast.comacq.assetinfo.response.GetLifeCycleByOidResponseBody;

public class GetLifeCycleByOidAssetInfoRowMapper implements RowMapper<GetLifeCycleByOidResponseBody> {
	
	public GetLifeCycleByOidResponseBody mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		GetLifeCycleByOidResponseBody entity = new GetLifeCycleByOidResponseBody();
		entity.setCode(rs.getString("Code"));
		entity.setDescription(rs.getString("Description"));
		entity.setStatus(rs.getString("Status"));
		entity.setCreatedBy(rs.getString("CreatedBy"));
		entity.setUpdatedBy(rs.getString("UpdatedBy"));
		entity.setTemporaryItemCode(rs.getString("TempCode"));
		entity.setWorkOrderNo(rs.getString("WorkOrderNo"));
		entity.setChalanNo(rs.getString("ChalanNo"));
		entity.setDecisionOnQcBy(rs.getString("DecisionOnQcBy"));
		entity.setReceivedBy(rs.getString("ReceivedBy"));
		entity.setTaggedBy(rs.getString("TaggedBy"));
		entity.setVendorNameBn(rs.getString("VendorNameBn"));
		entity.setVendorNameEn(rs.getString("VendorNameEn"));
		entity.setTempCreatedBy(rs.getString("TempCreatedBy"));
		entity.setTempUpdatedBy(rs.getString("TempUpdatedBy"));
		entity.setItemOid(rs.getString("itemOid"));
		entity.setQcBy(rs.getString("QcBy"));
		entity.setAssetAddedBy(rs.getString("AssetAddedBy"));
		entity.setProcurementMethod(rs.getString("procurementMethod"));
		entity.setContractNo(rs.getString("contractNo"));
		
		if(rs.getDate("CreatedOn") != null) {
			entity.setCreatedOn(new DateTime(rs.getDate("CreatedOn").getTime()));
		}
		if(rs.getDate("UpdatedOn") != null) {
			entity.setUpdatedOn(new DateTime(rs.getDate("UpdatedOn").getTime()));
		}
		if(rs.getDate("QcOn") != null) {
			entity.setQcOn(new DateTime(rs.getDate("QcOn").getTime()));
		}
		if(rs.getDate("AssetAddedOn") != null) {
			entity.setAssetAddedOn(new DateTime(rs.getDate("AssetAddedOn").getTime()));
		}
		if(rs.getDate("ReceivedAt") != null) {
			entity.setReceivedAt(new DateTime(rs.getDate("ReceivedAt").getTime()));
		}
		if(rs.getDate("ChalanDate") != null) {
			entity.setChalanDate(new DateTime(rs.getDate("ChalanDate").getTime()));
		}
		if(rs.getDate("WorkOrderDate") != null) {
			entity.setWorkOrderDate(new DateTime(rs.getDate("WorkOrderDate").getTime()));
		}
		if(rs.getDate("TempCreatedOn") != null) {
			entity.setTempCreatedOn(new DateTime(rs.getDate("TempCreatedOn").getTime()));
		}
		if(rs.getDate("TempUpdatedOn") != null) {
			entity.setTempUpdatedOn(new DateTime(rs.getDate("TempUpdatedOn").getTime()));
		}
		if(rs.getDate("TaggedOn") != null) {
			entity.setTaggedOn(new DateTime(rs.getDate("TaggedOn").getTime()));
		}
		if(rs.getDate("cotractSigningDate") != null) {
			entity.setContractSigningDate(new DateTime(rs.getDate("cotractSigningDate").getTime()));
		}
		if(rs.getDate("decisionOnQcDate") != null) {
			entity.setDecisionOnQcDate(new DateTime(rs.getDate("decisionOnQcDate").getTime()));
		}
		
		return entity;
	}
	
}
