package com.cokreates.grp.ast.comacq.tempitem.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cokreates.grp.ast.comacq.tempitem.response.GetByOidAfterQc;

public class GetByOidAfterQcRowMapper implements RowMapper<GetByOidAfterQc> {
	
	public GetByOidAfterQc mapRow(ResultSet rs, int rowNum) throws SQLException {
		GetByOidAfterQc entity = new GetByOidAfterQc();
		entity.setOid(rs.getString("oid"));
		entity.setWorkOrderDetailOid(rs.getString("workOrderDetailOid"));
		entity.setItemOid(rs.getString("itemOid"));
		entity.setReceivedQuantity(rs.getDouble("receivedQuantity"));
		entity.setQualifiedQuantity(rs.getDouble("qualifiedQuantity"));
		entity.setDisqualifiedQuantity(rs.getDouble("disqualifiedQuantity"));
		entity.setExtraQuantity(rs.getDouble("extraQuantity"));
		entity.setRemarks(rs.getString("remarks"));
		return entity;
	}
	
}
