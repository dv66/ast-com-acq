package com.cokreates.grp.ast.comacq.directout.util;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;

import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.util.constant.Table;
import com.google.common.collect.Lists;

import lombok.Synchronized;

public class GetByOidQuery {

	@Synchronized
	public static ImmutablePair<String, Object[]> getDirectOutSql(AuthUser user, String oid) {
		
		String sql = "select t.code as code, t.oid as oid, t.requested_by as requestedBy, "
			+ " t.status as status, t.remarks as remarks, t.decision_by as decisionBy, "
			+ " t.decision_on as decisionOn, t.requested_on as requestedOn, "
			+ " t.user_oid as endUserOid, t.purpose_oid as purposeOid,"
			+ " t.office_unit_oid as officeUnitOid, t.office_unit_post_oid as officeUnitPostOid, "
			+ " t.allocation_date as allocationdate, t.asset_alloc_oid as allocationOid,"
			+ " ( select code from " + Table.SCHEMA_AST + Table.ASSET_ALLOC 
			+ " e where e.oid = t.asset_alloc_oid ) as allocationCode, "
			+ " ( select name_bn from " + Table.SCHEMA_AST + Table.REQUISITION_TYPE 
			+ " e where e.oid = t.purpose_oid ) as purposeNameBn, "
			+ " ( select name_en from " + Table.SCHEMA_AST + Table.REQUISITION_TYPE 
			+ " e where e.oid = t.purpose_oid ) as purposeNameEn "
			+ " from " + Table.SCHEMA_AST + Table.DIRECT_OUT
			+ " t where 1 = 1 and t.oid = ? and t.office_oid = ?";
		
		List<Object> data = Lists.newArrayList(oid, user.getOfficeOid());
		
		Object[] aParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, aParam);
	}

	@Synchronized
	public static ImmutablePair<String, Object[]> getDirectOutLineSql(String oid) {
		
		String sql = "select t.oid, t.asset_oid as assetOid, "
			+ " t.remarks as remarks, "
			+ " ( select code from " + Table.SCHEMA_AST + Table.ASSET_INFO 
			+ " e where e.oid = t.asset_oid ) as assetCode "
			+ " from " + Table.SCHEMA_AST + Table.DIRECT_OUT_LINE 
			+ " t where 1 = 1 and t.direct_out_oid = ?";

		List<Object> data = Lists.newArrayList(oid);
		
		Object[] aParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, aParam);
	}
	

}

