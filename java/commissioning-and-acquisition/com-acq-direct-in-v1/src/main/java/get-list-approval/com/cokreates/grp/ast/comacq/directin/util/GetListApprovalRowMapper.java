package com.cokreates.grp.ast.comacq.directin.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cokreates.grp.ast.comacq.directin.model.DirectInApproval;

public class GetListApprovalRowMapper implements RowMapper<DirectInApproval> {
	
	public DirectInApproval mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		DirectInApproval entity = new DirectInApproval();
		
		entity.setOid(rs.getString("oid"));
		entity.setCode(rs.getString("code"));
		entity.setStatus(rs.getString("status"));
		entity.setRequestedBy(rs.getString("requestedBy"));
//		entity.setRequestedByNameEn(rs.getString("requestedByNameEn"));
//		entity.setRequestedByNameBn(rs.getString("requestedByNameBn"));
		return entity;
	}
	
}
