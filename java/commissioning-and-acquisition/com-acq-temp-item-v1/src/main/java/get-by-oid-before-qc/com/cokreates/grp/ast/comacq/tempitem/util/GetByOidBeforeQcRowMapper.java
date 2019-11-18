package com.cokreates.grp.ast.comacq.tempitem.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cokreates.grp.ast.comacq.tempitem.response.GetByOidBeforeQc;

public class GetByOidBeforeQcRowMapper implements RowMapper<GetByOidBeforeQc> {
	
	public GetByOidBeforeQc mapRow(ResultSet rs, int rowNum) throws SQLException {
		GetByOidBeforeQc entity = new GetByOidBeforeQc();
		entity.setOid(rs.getString("oid"));
		entity.setWorkOrderDetailOid(rs.getString("workOrderDetailOid"));
		entity.setItemOid(rs.getString("itemOid"));
		entity.setExtraQuantity(rs.getInt("extraQuantity"));
		entity.setReceivedQuantity(rs.getDouble("receivedQuantity"));
//		entity.setQualifiedQuantity(rs.getDouble("qualifiedQuantity"));
//		entity.setDisqualifiedQuantity(rs.getDouble("disqualifiedQuantity"));
		entity.setRemarks(rs.getString("remarks"));
		return entity;
	}
	
}
