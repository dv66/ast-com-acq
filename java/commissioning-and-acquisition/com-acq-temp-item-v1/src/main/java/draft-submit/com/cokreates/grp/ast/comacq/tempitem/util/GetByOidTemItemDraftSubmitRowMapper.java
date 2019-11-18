package com.cokreates.grp.ast.comacq.tempitem.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cokreates.grp.ast.comacq.tempitem.response.GetByOidDraftSubmitResponseBody;

public class GetByOidTemItemDraftSubmitRowMapper implements RowMapper<GetByOidDraftSubmitResponseBody> {
	
	public GetByOidDraftSubmitResponseBody mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		GetByOidDraftSubmitResponseBody entity = new GetByOidDraftSubmitResponseBody();
		entity.setStatus(rs.getString("status"));
				
		return entity;
	}
	
}
