package com.cokreates.grp.ast.comacq.tempitem.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;

import com.cokreates.grp.ast.comacq.tempitem.request.GetListRequestTaggingEntity;
import com.cokreates.grp.util.constant.Constant;
import com.cokreates.grp.util.constant.Table;
import com.google.common.collect.Lists;

import lombok.Synchronized;

public class GetListQueryTaggingEntity {
	
	@Synchronized
    public static ImmutablePair<String, Object[]> getTemporaryItemSql(GetListRequestTaggingEntity request, String officeOid) {
        
    	List<Object> param = Lists.newArrayList(officeOid);
    	
        String sql = "select t.oid as tempOid, t.code as tempCode, t.received_at as receivedAt,"
        		   + " t.received_by as receivedBy, t.status as tempStatus, t.description as description,"
        		   + " t.vendor_oid as vendorOid, v.name_en as vendorEnglishName, v.name_bn as vendorBanglaName,"
        		   + " t.created_by as createdBy, t.created_on as createdOn, t.updated_by as updatedBy,t.updated_on as updatedOn "
//        		   + " ( select name_bn from " + Table.SCHEMA_HRM + Table.EMPLOYEE_MASTER_INFO 
//       			   + " e where e.grp_username = t.received_by ) as receivedByBn, "
//       			   + " ( select name_en from " + Table.SCHEMA_HRM + Table.EMPLOYEE_MASTER_INFO 
//       			   + " e where e.grp_username = t.received_by ) as receivedByEn "
        		   + " from " + Table.SCHEMA_AST + Table.TEMP_ITEM
                   + " t left join " + Table.SCHEMA_CMN + Table.VENDOR +" v on t.vendor_oid = v.oid"
                   + " where 1 = 1 and t.office_oid = ?" ;

        if(request.getBody().getSearchParams() != null && StringUtils.isNotBlank(request.getBody().getSearchParams().getStatus())){
        	sql += " and t.status = ?";
        	 param.add(request.getBody().getSearchParams().getStatus());
        } else {
        	sql += " and t.status IN (?, ?)";
        	param.add(Constant.TI_STATUS_ASSET_ADDED);
        	param.add(Constant.TI_STATUS_ASSET_TAGGED);
        }
        
        if(StringUtils.isNotBlank(request.getBody().getSearchParams().getSearchText())){
    		sql += " AND (LOWER(t.code) like '%" + request.getBody().getSearchParams().getSearchText().trim().toLowerCase()+ "%' OR"
                + " LOWER(t.status) like '%"+ request.getBody().getSearchParams().getSearchText().trim().toLowerCase() + "%' OR"
                + " LOWER(v.name_en) like '%"+ request.getBody().getSearchParams().getSearchText().trim().toLowerCase() + "%' OR"
                + " LOWER(v.name_bn) like '%"+ request.getBody().getSearchParams().getSearchText().trim().toLowerCase() + "%' OR"
                + " LOWER(t.received_by) like '%" + request.getBody().getSearchParams().getSearchText().trim().toLowerCase() + "%')";
        }
        
        sql += " order by t.created_on desc";
        
        if(request.getBody().getSearchParams() != null && request.getBody().getSearchParams().getLimit() > 0){
            sql += " offset ? limit ?";
            param.add(request.getBody().getSearchParams().getOffset());
            param.add(request.getBody().getSearchParams().getLimit());
        }
        
        Object[] data = param.toArray(new Object[param.size()]);
        return new ImmutablePair<String, Object[]>(sql, data);
    }
    
    @Synchronized
    public static ImmutablePair<String, Object[]> getDataCountSql(GetListRequestTaggingEntity request, String officeOid) {

        List<Object> param = Lists.newArrayList(officeOid, Constant.TI_STATUS_ASSET_ADDED, Constant.TI_STATUS_ASSET_TAGGED);
        
        String sql = "select count(t.oid)"
            + " from " + Table.SCHEMA_AST + Table.TEMP_ITEM
            + " t left join " + Table.SCHEMA_CMN + Table.VENDOR +" v on t.vendor_oid = v.oid"
            + " where 1 = 1 and t.office_oid = ? and t.status IN (?, ?)" ;
        
        if(request.getBody().getSearchParams() != null && StringUtils.isNotBlank(request.getBody().getSearchParams().getStatus())){
        	sql += " and t.status = ?";
        	 param.add(request.getBody().getSearchParams().getStatus());
        } else {
        	sql += " and t.status IN (?, ?)";
        	param.add(Constant.TI_STATUS_ASSET_ADDED);
        	param.add(Constant.TI_STATUS_ASSET_TAGGED);
        }
        
        if(StringUtils.isNotBlank(request.getBody().getSearchParams().getSearchText())){
    		sql += " AND (LOWER(t.code) like '%" + request.getBody().getSearchParams().getSearchText().trim().toLowerCase()+ "%' OR"
                + " LOWER(t.status) like '%"+ request.getBody().getSearchParams().getSearchText().trim().toLowerCase() + "%' OR"
                + " LOWER(v.name_en) like '%"+ request.getBody().getSearchParams().getSearchText().trim().toLowerCase() + "%' OR"
                + " LOWER(v.name_bn) like '%"+ request.getBody().getSearchParams().getSearchText().trim().toLowerCase() + "%' OR"
                + " LOWER(t.received_by) like '%" + request.getBody().getSearchParams().getSearchText().trim().toLowerCase() + "%')";
        }
        
        if(request.getBody().getSearchParams() != null && request.getBody().getSearchParams().getLimit() > 0){
            sql += " offset ? limit ?";
            param.add(request.getBody().getSearchParams().getOffset());
            param.add(request.getBody().getSearchParams().getLimit());
        }
        
        Object[] data = param.toArray(new Object[param.size()]);
        return new ImmutablePair<String, Object[]>(sql, data);
    }

}
