package com.cokreates.grp.ast.comacq.assetinfo.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;

import com.cokreates.grp.ast.comacq.assetinfo.request.GetListAssetOutOfServiceRequest;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.util.constant.Constant;
import com.cokreates.grp.util.constant.Table;
import com.google.common.collect.Lists;

import lombok.Synchronized;

public class GetListAssetOutOfServiceQuery {
	
	@Synchronized
	public static ImmutablePair<String, Object[]> getListAssetOutOfServiceSql(AuthUser user, GetListAssetOutOfServiceRequest request){
		
		List<Object> data = Lists.newArrayList(user.getOfficeOid(), Constant.ED_OUT_OF_SERVICE);
		
		String sql = "select a.oid as oid, a.code as code, a.description as description, a.status as status, "
				+ " a.item_oid as itemOid, t.code as tempItemCode"
				+ " from " + Table.SCHEMA_AST + Table.ASSET_INFO 
				+ " a left join " + Table.SCHEMA_AST + Table.TEMP_ITEM
				+ " t on a.temporary_item_oid = t.oid"
				+ " where 1 = 1 and a.office_oid = ? and a.status = ?";
		
		if(StringUtils.isNotBlank(request.getBody().getSearchParams().getSearchText())){
    		sql += " AND (LOWER(t.code) like '%" + request.getBody().getSearchParams().getSearchText().trim().toLowerCase()+ "%' OR"
                + " LOWER(a.code) like '%" + request.getBody().getSearchParams().getSearchText().trim().toLowerCase() + "%')";
        }
		
		sql += " order by code asc";
		
		if(request.getBody().getSearchParams() != null && request.getBody().getSearchParams().getLimit() > 0){
	           sql += " offset ? limit ?";
	           data.add(request.getBody().getSearchParams().getOffset());
	           data.add(request.getBody().getSearchParams().getLimit());
	      }
		 
		 
		Object[] aParam = data.toArray(new Object[data.size()]);
	    return new ImmutablePair<String, Object[]>(sql, aParam);
	}
	
	@Synchronized
    public static ImmutablePair<String, Object[]> getDataCountSql(AuthUser user, GetListAssetOutOfServiceRequest request) {
         
        String sql = "select count(a.oid)"
                + " from " + Table.SCHEMA_AST + Table.ASSET_INFO + " a"
				+ " left join " + Table.SCHEMA_AST + Table.TEMP_ITEM
				+ " t on a.temporary_item_oid = t.oid "
				+ " where 1 = 1 and a.office_oid = ? and a.status = ?";
        
        
        List<Object> data = Lists.newArrayList(user.getOfficeOid(), Constant.ED_OUT_OF_SERVICE);
		
		Object[] aParam = data.toArray(new Object[data.size()]);
		return new ImmutablePair<String, Object[]>(sql, aParam);
    }

}
