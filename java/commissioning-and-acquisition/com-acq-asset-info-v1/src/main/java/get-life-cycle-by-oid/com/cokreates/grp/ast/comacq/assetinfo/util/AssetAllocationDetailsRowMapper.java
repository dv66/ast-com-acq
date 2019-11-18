package com.cokreates.grp.ast.comacq.assetinfo.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;

import com.cokreates.grp.ast.comacq.assetinfo.response.AssetAllocationDetails;

public class AssetAllocationDetailsRowMapper implements RowMapper<AssetAllocationDetails>{

	@Override
	public AssetAllocationDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
		AssetAllocationDetails assetAllocation = new AssetAllocationDetails();
		assetAllocation.setAssetAllocationcode(rs.getString("AssetAllocationCode"));
		assetAllocation.setAssetRequisitionCode(rs.getString("RequisitionCode"));
		assetAllocation.setAllocatorOid(rs.getString("allocatorOid"));
		assetAllocation.setEndUserOid(rs.getString("endUserOid"));
		assetAllocation.setQcPassed(rs.getString("QcPassed"));
		if(rs.getDate("AllocationDate") != null) {
			assetAllocation.setAllocationDate(new DateTime(rs.getDate("AllocationDate").getTime()));
		}
		if(rs.getDate("ReceivedDate") != null) {
			assetAllocation.setReceivedDate(new DateTime(rs.getDate("ReceivedDate").getTime()));
		}
		if(rs.getDate("RequestedDate") != null) {
			assetAllocation.setRequestedDate(new DateTime(rs.getDate("RequestedDate").getTime()));
		}
		return assetAllocation;
	}

}
