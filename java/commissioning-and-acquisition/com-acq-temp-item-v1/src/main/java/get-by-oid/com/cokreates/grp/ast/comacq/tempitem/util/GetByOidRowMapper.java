package com.cokreates.grp.ast.comacq.tempitem.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cokreates.grp.ast.comacq.tempitem.response.GetByOid;

public class GetByOidRowMapper implements RowMapper<GetByOid> {
	
	public GetByOid mapRow(ResultSet rs, int rowNum) throws SQLException {
		GetByOid entity = new GetByOid();
		entity.setOid(rs.getString("oid"));
		entity.setWorkOrderDetailOid(rs.getString("workOrderDetailOid"));
		entity.setItemOid(rs.getString("itemOid"));
		entity.setReceivedQuantity(rs.getInt("receivedQuantity"));
		entity.setExtraQuantity(rs.getInt("extraQuantity"));
		entity.setQualifiedQuantity(rs.getInt("qualifiedQuantity"));
		entity.setDisqualifiedQuantity(rs.getInt("disqualifiedQuantity"));
		entity.setQuantity(rs.getInt("quantity"));
		entity.setPreviousReceivedQuantity(rs.getInt("previousReceivedQuantity"));
		entity.setRemarks(rs.getString("remarks"));
		return entity;
	}
	
}
