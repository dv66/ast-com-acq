package com.cokreates.grp.ast.comacq.tempitem.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cokreates.grp.ast.comacq.tempitem.model.QcAcceptTempItemDetail;

public class QcAcceptRowMapper implements RowMapper<QcAcceptTempItemDetail> {
	
	public QcAcceptTempItemDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		QcAcceptTempItemDetail entity = new QcAcceptTempItemDetail();
		
		entity.setItemOid(rs.getString("item_oid"));
		entity.setQualifiedQuantity(rs.getString("qualified_quantity"));

		return entity;
	}
	
}
