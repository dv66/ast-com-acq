package com.cokreates.grp.ast.comacq.tempitem.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cokreates.grp.ast.comacq.tempitem.response.GetByOidDraftQcUpdateResponseBody;

public class GetByOidDraftQcUpdateRowMapper implements RowMapper<GetByOidDraftQcUpdateResponseBody> {
	
	public GetByOidDraftQcUpdateResponseBody mapRow(ResultSet rs, int rowNum) throws SQLException {
		GetByOidDraftQcUpdateResponseBody entity = new GetByOidDraftQcUpdateResponseBody();
		entity.setReceivedQuantity(rs.getInt("received_quantity"));
		return entity;
	}
	
}
