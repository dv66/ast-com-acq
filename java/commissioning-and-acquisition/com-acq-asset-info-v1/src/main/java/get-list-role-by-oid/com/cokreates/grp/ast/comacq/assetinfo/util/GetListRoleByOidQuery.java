package com.cokreates.grp.ast.comacq.assetinfo.util;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;

import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.util.constant.Table;
import com.google.common.collect.Lists;

import lombok.Synchronized;

public class GetListRoleByOidQuery {
	
	@Synchronized
    public static ImmutablePair<String, Object[]> getByOidExpertDecisionSql(String oid, AuthUser user) {
        
    	List<Object> param = Lists.newArrayList(oid, user.getOfficeOid());
    	        
    	 String sql = "select u.grp_user_oid as grpUserOid, gr.name_bn as nameEn, gr.name_en as nameBn " + 
	    	 		  "from " + Table.SCHEMA_SEC + Table.GRP_USER_ROLE + " as u " + 
	    	 		  "left join " + Table.SCHEMA_SEC + Table.GRP_MODULE + " as gm " + 
	    	 		  "on gm.oid = u.grp_module_oid " + 
	    	 		  "left join " + Table.SCHEMA_SEC + Table.GRP_ROLE + " as gr " + 
	    	 		  "on gr.oid = u.grp_role_oid " + 
	    	 		  "where 1 = 1 and gm.name_en = 'Asset Management' " + 
	    	 		  "and u.grp_user_oid = ? and u.active_status = 'Yes' and u.office_oid = ?" ;
        		
        
        Object[] data = param.toArray(new Object[param.size()]);
        return new ImmutablePair<String, Object[]>(sql, data);
    }
    
    
    @Synchronized
    public static ImmutablePair<String, Object[]> getDataCountSql(String oid, AuthUser user) {
         
    	List<Object> param = Lists.newArrayList(oid, user.getOfficeOid());
    	
        String sql = "select count(gr.name_en) " + 
		  	 		  "from " + Table.SCHEMA_SEC + Table.GRP_USER_ROLE + " as u " + 
		  	 		  "left join " + Table.SCHEMA_SEC + Table.GRP_MODULE + " as gm " + 
		  	 		  "on gm.oid = u.grp_module_oid " + 
		  	 		  "left join " + Table.SCHEMA_SEC + Table.GRP_ROLE + " as gr " + 
		  	 		  "on gr.oid = u.grp_role_oid " + 
		  	 		  "where 1 = 1 and gm.name_en = 'Asset Management' " + 
		  	 		  "and u.grp_user_oid = ? and u.active_status = 'Yes' and u.office_oid = ?" ;
        
        Object[] data = param.toArray(new Object[param.size()]);
        return new ImmutablePair<String, Object[]>(sql, data);
    }


}
