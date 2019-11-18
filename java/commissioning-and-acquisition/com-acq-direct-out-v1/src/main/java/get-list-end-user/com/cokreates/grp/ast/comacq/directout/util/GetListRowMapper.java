package com.cokreates.grp.ast.comacq.directout.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cokreates.grp.ast.comacq.directout.model.DirectOut;

public class GetListRowMapper implements RowMapper<DirectOut> {
	
	public DirectOut mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		DirectOut entity = new DirectOut();
		
		entity.setOid(rs.getString("oid"));
		entity.setCode(rs.getString("code"));
		entity.setStatus(rs.getString("status"));
		entity.setEndUserOid(rs.getString("endUserOid"));
//		entity.setEndUserNameBn(rs.getString("endUserNameBn"));
		return entity;
	}
	
}
