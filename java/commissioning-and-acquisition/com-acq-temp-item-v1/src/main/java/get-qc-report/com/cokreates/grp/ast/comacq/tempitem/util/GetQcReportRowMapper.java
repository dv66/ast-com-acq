package com.cokreates.grp.ast.comacq.tempitem.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cokreates.grp.ast.comacq.tempitem.response.GetQcReportResponseBody;

public class GetQcReportRowMapper implements RowMapper<GetQcReportResponseBody> {
	
	public GetQcReportResponseBody mapRow(ResultSet rs, int rowNum) throws SQLException {
		GetQcReportResponseBody entity = new GetQcReportResponseBody();
		entity.setFilePath(rs.getString("qc_report_path"));

		return entity;
	}
	
}
