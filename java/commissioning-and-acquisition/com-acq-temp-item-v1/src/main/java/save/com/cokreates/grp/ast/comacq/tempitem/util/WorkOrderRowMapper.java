package com.cokreates.grp.ast.comacq.tempitem.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cokreates.grp.ast.comacq.tempitem.model.WorkOrderDetail;

public class WorkOrderRowMapper implements RowMapper<WorkOrderDetail> {
	
	public WorkOrderDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		WorkOrderDetail entity = new WorkOrderDetail();
		
		entity.setOid(rs.getString("oid"));
		entity.setOrderedQuantity(rs.getInt("orderedQuantity"));
		entity.setPreviosulyReceivedQuantity(rs.getInt("previosulyReceivedQuantity"));

		return entity;
	}
	
}
