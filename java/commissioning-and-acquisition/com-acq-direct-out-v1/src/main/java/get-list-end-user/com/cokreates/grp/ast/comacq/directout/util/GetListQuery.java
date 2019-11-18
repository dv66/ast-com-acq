package com.cokreates.grp.ast.comacq.directout.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;

import com.cokreates.grp.ast.comacq.directout.request.GetListRequest;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.util.constant.Table;
import com.google.common.collect.Lists;

import lombok.Synchronized;

public class GetListQuery {
	
	@Synchronized
    public static ImmutablePair<String, Object[]> getDirectOutSql(GetListRequest request, AuthUser user ) {
        
    	List<Object> param = Lists.newArrayList(user.getOfficeOid(),user.getUserOid());
    	
    	
        String sql = " select t.oid as oid, t.code as code, t.status as status, t.user_oid as endUserOid "
        		   + " from " + Table.SCHEMA_AST + Table.DIRECT_OUT
                   + " t where 1=1 and t.office_oid = ? and t.requested_by = ? ";
        
        //  
        
        if(StringUtils.isNotBlank(request.getBody().getSearchParams().getSearchText())){
        		sql += " AND (LOWER(t.code) like '%" + request.getBody().getSearchParams().getSearchText().trim().toLowerCase()+ "%' OR"
                    + " LOWER(t.status) like '%"+ request.getBody().getSearchParams().getSearchText().trim().toLowerCase() + "%' ";
        }
        
        sql += " order by t.updated_on desc";
        
        if(request.getBody().getSearchParams() != null && request.getBody().getSearchParams().getLimit() > 0){
            sql += " offset ? limit ?";
            param.add(request.getBody().getSearchParams().getOffset());
            param.add(request.getBody().getSearchParams().getLimit());
        }  
        Object[] data = param.toArray(new Object[param.size()]);
        return new ImmutablePair<String, Object[]>(sql, data);
    }
    
    @Synchronized
    public static ImmutablePair<String, Object[]> getDataCountSql(GetListRequest request, AuthUser user) {
         
        List<Object> param = Lists.newArrayList(user.getOfficeOid(),user.getUserOid());
        String sql = "select count(t.oid)"
            + " from " + Table.SCHEMA_AST + Table.DIRECT_OUT + " t"
            + " where 1 = 1 and t.office_oid = ? and t.requested_by = ?";
        
        
        Object[] data = param.toArray(new Object[param.size()]);
        return new ImmutablePair<String, Object[]>(sql, data);
    }


}
