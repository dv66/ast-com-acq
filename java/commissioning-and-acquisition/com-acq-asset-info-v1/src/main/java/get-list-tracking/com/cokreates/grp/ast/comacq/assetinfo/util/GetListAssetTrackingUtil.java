package com.cokreates.grp.ast.comacq.assetinfo.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;

import com.cokreates.grp.ast.comacq.assetinfo.request.GetListAssetTrackingRequest;
import com.cokreates.grp.util.constant.Table;
import com.google.common.collect.Lists;

import lombok.Synchronized;

public class GetListAssetTrackingUtil {
	
	@Synchronized
    public static ImmutablePair<String, Object[]> getTemporaryItemSql(GetListAssetTrackingRequest request, String officeOid) {
        
    	List<Object> param = Lists.newArrayList(officeOid);
    	
        String sql = "select ai.item_oid as itemOid,"
        		   + " ai.oid as assetOid, ai.code as assetCode, ai.status as assetStatus, al.end_user_oid as recievedBy, "
        		   + " rt.name_en as reqTypeEn, rt.name_bn as reqTypeBn "
        		   + " from " + Table.SCHEMA_AST + Table.ASSET_INFO + " ai "
        		   + " left join " + Table.SCHEMA_AST + Table.ASSET_ALLOCATION_DETAILS + " ald on ald.asset_oid = ai.oid"
        		   + " left join " + Table.SCHEMA_AST + Table.ASSET_ALLOCATION + " al on al.oid = ald.asset_allocation_oid"
        		   + " left join " + Table.SCHEMA_AST + Table.REQUISITION_TYPE + " rt on rt.oid = al.allocation_type_oid"
//        		   + " left join " + Table.SCHEMA_HRM + Table.EMPLOYEE_OFFICE + " eo on eo.employee_oid = emp.oid"
//        		   + " left join " + Table.SCHEMA_CMN + Table.OFFICE_UNIT_POST + " oup on oup.oid = eo.office_unit_post_id"
//        		   + " left join " + Table.SCHEMA_CMN + Table.POST + " p on p.oid = oup.post_oid"
                   + " where 1 = 1 and ai.office_oid = ?" ;

        if(request.getBody().getSearchParams() != null && StringUtils.isNotBlank(request.getBody().getSearchParams().getStatus())){
        	sql += " and ai.status = ?";
        	 param.add(request.getBody().getSearchParams().getStatus());
        } 
        
        if(request.getBody().getSearchParams() != null && StringUtils.isNotBlank(request.getBody().getItemOid())){
        	sql += " and ai.item_oid = ?";
        	 param.add(request.getBody().getItemOid());
        }
        
//        if(StringUtils.isNotBlank(request.getBody().getSearchParams().getSearchText())){
//    		sql += " AND (LOWER(i.item_code) like '%" + request.getBody().getSearchParams().getSearchText().trim().toLowerCase()+ "%' OR"
//                + " LOWER(i.item_status) like '%"+ request.getBody().getSearchParams().getSearchText().trim().toLowerCase() + "%' OR"
//                + " LOWER(i.name_en) like '%"+ request.getBody().getSearchParams().getSearchText().trim().toLowerCase() + "%' OR"
//                + " LOWER(i.name_bn) like '%"+ request.getBody().getSearchParams().getSearchText().trim().toLowerCase() + "%' OR)";
//        }       
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
    public static ImmutablePair<String, Object[]> getDataCountSql(GetListAssetTrackingRequest request, String officeOid) {

        List<Object> param = Lists.newArrayList(officeOid);
        
        String sql = "select count(ai.oid)"
        		+ " from " + Table.SCHEMA_AST + Table.ASSET_INFO + " ai "
     		   + " left join " + Table.SCHEMA_AST + Table.ASSET_ALLOCATION_DETAILS + " ald on ald.asset_oid = ai.oid"
     		   + " left join " + Table.SCHEMA_AST + Table.ASSET_ALLOCATION + " al on al.oid = ald.asset_allocation_oid"
     		    + " left join " + Table.SCHEMA_AST + Table.REQUISITION_TYPE + " rt on rt.oid = al.allocation_type_oid"
               + " where 1 = 1 and ai.office_oid = ?" ;
        
        if(request.getBody().getSearchParams() != null && StringUtils.isNotBlank(request.getBody().getSearchParams().getStatus())){
        	sql += " and ai.status = ?";
        	 param.add(request.getBody().getSearchParams().getStatus());
        } 
        
//        if(request.getBody().getSearchParams() != null && StringUtils.isNotBlank(request.getBody().getItemCategoryOid())){
//        	sql += " and ig.item_category_oid = ?";
//        	 param.add(request.getBody().getItemCategoryOid());
//        }
//        
//        if(request.getBody().getSearchParams() != null && StringUtils.isNotBlank(request.getBody().getItemGroupOid())){
//        	sql += " and i.item_group_oid = ?";
//        	 param.add(request.getBody().getItemGroupOid());
//        }
        
        if(request.getBody().getSearchParams() != null && StringUtils.isNotBlank(request.getBody().getItemOid())){
        	sql += " and ai.item_oid = ?";
        	 param.add(request.getBody().getItemOid());
        }
                
        Object[] data = param.toArray(new Object[param.size()]);
        return new ImmutablePair<String, Object[]>(sql, data);
    }

}
