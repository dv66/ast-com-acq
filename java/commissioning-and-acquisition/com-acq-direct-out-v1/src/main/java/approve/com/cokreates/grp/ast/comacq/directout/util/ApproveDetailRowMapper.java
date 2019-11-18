package com.cokreates.grp.ast.comacq.directout.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cokreates.grp.ast.comacq.directout.model.ApproveDirectOutDetail;


public class ApproveDetailRowMapper implements RowMapper<ApproveDirectOutDetail> {
	
	public ApproveDirectOutDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
		ApproveDirectOutDetail entity = new ApproveDirectOutDetail();
		entity.setOid(rs.getString("oid"));
		entity.setAssetOid(rs.getString("assetOid"));
		entity.setRemarks(rs.getString("remarks"));

		return entity;
	}
	
}
