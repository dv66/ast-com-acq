package com.cokreates.grp.ast.comacq.assetinfo.dao;

import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cokreates.grp.ast.comacq.assetinfo.model.AssetListTracking;
import com.cokreates.grp.ast.comacq.assetinfo.request.GetListAssetTrackingRequest;
import com.cokreates.grp.ast.comacq.assetinfo.util.GetListAssetTrackingRowMapper;
import com.cokreates.grp.ast.comacq.assetinfo.util.GetListAssetTrackingUtil;
import com.cokreates.grp.client.AuthWsClient;
import com.cokreates.grp.client.HrmPimClient;
import com.cokreates.grp.client.InvCatalogueClient;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.model.EmployeeResponse;
import com.cokreates.grp.model.hrm.EmployeeInfoRequest;
import com.cokreates.grp.model.hrm.EmployeeInfoRequestBody;
import com.cokreates.grp.model.hrm.EmployeeInfoResponse;
import com.cokreates.grp.model.inv.InvItem;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository("comAcqTempItemV1GetListAssetTrackingDao")
public class GetListAssetTrackingDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private InvCatalogueClient inventoryClient;
  
    @Autowired
    private AuthWsClient authWsClient;
    
    @Autowired
 	private HrmPimClient hrmPimClient;
    
    @Transactional
    public List<AssetListTracking> findAll(AuthUser user, GetListAssetTrackingRequest request, String officeOid) {
        ImmutablePair<String, Object[]> query = GetListAssetTrackingUtil.getTemporaryItemSql(request, officeOid);
        List<AssetListTracking> result = jdbcTemplate.query(query.getLeft(), query.getRight(), new GetListAssetTrackingRowMapper());
        for(int i =0; i<result.size(); i++) {
        	try {
        		InvItem item = inventoryClient.getItem(user.getToken(), result.get(i).getItemOid());
            	result.get(i).itemValuesSetter(item);
        	} catch (Exception e) {
        		log.error("An exception occurred while getting item : ", e);
        	}
        	
        	 try {
                 JSONObject j = new JSONObject();
                 j.put("userOid", result.get(i).getRecievedBy());
                 EmployeeResponse response = authWsClient.getUsername((j.toString()));
                 log.info("ReceivedBy name response : {}", response);
                 if(response != null && response.getStatus() == 200) {
                	 
                     result.get(i).setEmployeeOid(response.getData().getOid());
                 }
             } catch (Exception e) {
                 log.error("An exception occurred while getting receivedBy name  : ", e);
             }
        	 
        	 try {
        		 	EmployeeInfoRequest req = setData(request, result.get(i).getEmployeeOid());
        		 	EmployeeInfoResponse response = hrmPimClient.getEmployee(req);
        		 	result.get(i).setRecievedByNameBn(response.getBody().getData().get(0).getNameBn());
        		 	result.get(i).setRecievedByNameEn(response.getBody().getData().get(0).getNameEn());
        		 	result.get(i).setDesignationNameBn(response.getBody().getData().get(0).getPostNameBn());
        		 	result.get(i).setDesignationNameEn(response.getBody().getData().get(0).getPostNameEn());
        		 	result.get(i).setOfficeUnitNameBn(response.getBody().getData().get(0).getOfficeUnitNameBn());
        		 	result.get(i).setOfficeUnitNameEn(response.getBody().getData().get(0).getOfficeUnitNameEn());
        		 } catch (Exception e) {
        		 	log.error("An exception occurred while getting name : ", e);
        		        }
        }
        return ListUtils.emptyIfNull(result);
    }
    
    @Transactional
    public int getDataCount(String officeOid, GetListAssetTrackingRequest request) {
        ImmutablePair<String, Object[]> query = GetListAssetTrackingUtil.getDataCountSql(request, officeOid);
        int count = jdbcTemplate.queryForObject(query.getKey(), query.getValue(), Integer.class);
        return count;
    }
    
    
    
       public EmployeeInfoRequest setData(GetListAssetTrackingRequest request, String oid){
    	    	    	
    	    	EmployeeInfoRequestBody body = new EmployeeInfoRequestBody();
    	    	body.setOid(oid);
    	    	
    	    	EmployeeInfoRequest req = new EmployeeInfoRequest();
    	    	req.setHeader(request.getHeader());
    	    	req.setMeta(request.getMeta());
    	    	req.setBody(body);
    	    	
    	    	return req;
    	    }

}
