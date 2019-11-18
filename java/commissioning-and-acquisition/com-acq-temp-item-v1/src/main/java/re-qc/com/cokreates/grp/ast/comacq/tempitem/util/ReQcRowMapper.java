package com.cokreates.grp.ast.comacq.tempitem.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cokreates.grp.ast.comacq.tempitem.response.GetByOidQcUpdateResponseBody;

public class ReQcRowMapper implements RowMapper<GetByOidQcUpdateResponseBody> {
	
	public GetByOidQcUpdateResponseBody mapRow(ResultSet rs, int rowNum) throws SQLException {
		GetByOidQcUpdateResponseBody entity = new GetByOidQcUpdateResponseBody();
//		entity.setStatus(rs.getString("status"));
		return entity;
	}
	
}
