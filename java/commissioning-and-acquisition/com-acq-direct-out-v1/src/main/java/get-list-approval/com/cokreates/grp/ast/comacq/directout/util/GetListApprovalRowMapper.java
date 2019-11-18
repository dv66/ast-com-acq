package com.cokreates.grp.ast.comacq.directout.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cokreates.grp.ast.comacq.directout.model.DirectOutApproval;

public class GetListApprovalRowMapper implements RowMapper<DirectOutApproval> {
	
	public DirectOutApproval mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		DirectOutApproval entity = new DirectOutApproval();
		
		entity.setOid(rs.getString("oid"));
		entity.setCode(rs.getString("code"));
		entity.setStatus(rs.getString("status"));
		entity.setRequestedBy(rs.getString("requestedBy"));
		entity.setEndUserOid(rs.getString("endUserOid"));
		return entity;
	}
	
}
