package com.cokreates.grp.ast.comacq.tempitem.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cokreates.grp.ast.comacq.tempitem.model.DraftSubmitWorkOrderDetail;

public class DraftSubmitWorkOrderRowMapper implements RowMapper<DraftSubmitWorkOrderDetail> {
	
	public DraftSubmitWorkOrderDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		DraftSubmitWorkOrderDetail entity = new DraftSubmitWorkOrderDetail();
		
		entity.setOid(rs.getString("oid"));
		entity.setOrderedQuantity(rs.getInt("orderedQuantity"));
		entity.setPreviosulyReceivedQuantity(rs.getInt("previosulyReceivedQuantity"));

		return entity;
	}
	
}
