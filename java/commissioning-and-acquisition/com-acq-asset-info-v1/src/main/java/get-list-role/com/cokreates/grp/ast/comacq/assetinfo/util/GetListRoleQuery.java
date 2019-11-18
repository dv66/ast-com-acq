package com.cokreates.grp.ast.comacq.assetinfo.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;

import com.cokreates.grp.ast.comacq.assetinfo.request.GetListRoleRequest;
import com.cokreates.grp.util.constant.Table;
import com.google.common.collect.Lists;

import lombok.Synchronized;

public class GetListRoleQuery {
	
	@Synchronized
    public static ImmutablePair<String, Object[]> getTemporaryItemSql(GetListRoleRequest request) {
        
    	List<Object> param = Lists.newArrayList();
    	
        String sql =  " select gr.oid as oid, gr.name_en as nameEn, gr.name_bn as nameBn"
        			+ " from " + Table.SCHEMA_SEC + Table.GRP_ROLE + " as gr "
        			+ " left join " + Table.SCHEMA_SEC + Table.GRP_MODULE + " as gm "
        			+ " on gm.oid = gr.grp_module_oid "
        			+ " where 1 = 1 and gm.name_en = 'Asset Management' ";
        
        if(StringUtils.isNotBlank(request.getBody().getSearchParams().getSearchText())){
        		sql += " AND (LOWER(gr.name_en) like '%" + request.getBody().getSearchParams().getSearchText().trim().toLowerCase()+ "%' OR"
                    + " LOWER(gr.name_en) like '%"+ request.getBody().getSearchParams().getSearchText().trim().toLowerCase() + "%')";
        }
        
//        sql += " order by t.created_on desc";
        
        if(request.getBody().getSearchParams() != null && request.getBody().getSearchParams().getLimit() > 0){
            sql += " offset ? limit ?";
            param.add(request.getBody().getSearchParams().getOffset());
            param.add(request.getBody().getSearchParams().getLimit());
        }  
        Object[] data = param.toArray(new Object[param.size()]);
        return new ImmutablePair<String, Object[]>(sql, data);
    }
    
    @Synchronized
    public static ImmutablePair<String, Object[]> getDataCountSql(GetListRoleRequest request) {
         
        List<Object> param = Lists.newArrayList();
        String sql = "select count(gr.oid)"
        		+ " from " + Table.SCHEMA_SEC + Table.GRP_ROLE + " as gr "
    			+ " left join " + Table.SCHEMA_SEC + Table.GRP_MODULE + " as gm "
    			+ " on gm.oid = gr.grp_module_oid "
    			+ " where 1 = 1 and gm.name_en = 'Asset Management' ";
        
        
        Object[] data = param.toArray(new Object[param.size()]);
        return new ImmutablePair<String, Object[]>(sql, data);
    }


}
