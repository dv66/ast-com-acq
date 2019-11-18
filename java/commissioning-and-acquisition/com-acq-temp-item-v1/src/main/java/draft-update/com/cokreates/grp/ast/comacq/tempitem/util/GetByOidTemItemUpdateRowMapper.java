package com.cokreates.grp.ast.comacq.tempitem.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cokreates.grp.ast.comacq.tempitem.response.GetByOidUpdateResponseBody;

public class GetByOidTemItemUpdateRowMapper implements RowMapper<GetByOidUpdateResponseBody> {
	
	public GetByOidUpdateResponseBody mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		GetByOidUpdateResponseBody entity = new GetByOidUpdateResponseBody();
		entity.setStatus(rs.getString("status"));
				
		return entity;
	}
	
}
