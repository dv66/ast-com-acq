package com.cokreates.grp.ast.comacq.directout.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cokreates.grp.ast.comacq.directout.model.RejectDirectOutDetail;

public class RejectRowMapper implements RowMapper<RejectDirectOutDetail>{
	
	public RejectDirectOutDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
		RejectDirectOutDetail entity = new RejectDirectOutDetail();
		entity.setAssetOid(rs.getString("assetOid"));

		return entity;
	}

}
