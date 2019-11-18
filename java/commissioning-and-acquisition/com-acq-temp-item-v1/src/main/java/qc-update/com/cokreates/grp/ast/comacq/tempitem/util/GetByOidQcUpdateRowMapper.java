package com.cokreates.grp.ast.comacq.tempitem.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cokreates.grp.ast.comacq.tempitem.response.GetByOidQcUpdateResponseBody;

public class GetByOidQcUpdateRowMapper implements RowMapper<GetByOidQcUpdateResponseBody> {
	
	public GetByOidQcUpdateResponseBody mapRow(ResultSet rs, int rowNum) throws SQLException {
		GetByOidQcUpdateResponseBody entity = new GetByOidQcUpdateResponseBody();
		entity.setReceivedQuantity(rs.getInt("received_quantity"));
		return entity;
	}
	
}
