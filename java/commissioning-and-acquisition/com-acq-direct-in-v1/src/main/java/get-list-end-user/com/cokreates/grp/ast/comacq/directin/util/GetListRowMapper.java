package com.cokreates.grp.ast.comacq.directin.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cokreates.grp.ast.comacq.directin.model.DirectIn;

public class GetListRowMapper implements RowMapper<DirectIn> {
	
	public DirectIn mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		DirectIn entity = new DirectIn();
		
		entity.setOid(rs.getString("oid"));
		entity.setCode(rs.getString("code"));
		entity.setStatus(rs.getString("status"));
		return entity;
	}
	
}
