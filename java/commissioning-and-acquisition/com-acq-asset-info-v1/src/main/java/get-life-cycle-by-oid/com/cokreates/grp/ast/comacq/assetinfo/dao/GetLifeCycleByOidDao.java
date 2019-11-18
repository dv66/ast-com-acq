package com.cokreates.grp.ast.comacq.assetinfo.dao;


import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cokreates.grp.ast.comacq.assetinfo.response.AssetAllocationDetails;
import com.cokreates.grp.ast.comacq.assetinfo.response.ExpertDecisionDetail;
import com.cokreates.grp.ast.comacq.assetinfo.response.GetLifeCycleByOidResponseBody;
import com.cokreates.grp.ast.comacq.assetinfo.util.AssetAllocationDetailsRowMapper;
import com.cokreates.grp.ast.comacq.assetinfo.util.ExpertDecisionRowMapper;
import com.cokreates.grp.ast.comacq.assetinfo.util.GetLifeCycleByOidAssetInfoRowMapper;
import com.cokreates.grp.ast.comacq.assetinfo.util.GetLifeCycleByOidQuery;
import com.cokreates.grp.client.AuthWsClient;
import com.cokreates.grp.client.InvCatalogueClient;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.model.EmployeeResponse;
import com.cokreates.grp.model.inv.InvItem;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository("comacqAssetLifeCycleV1GetByOidDao")
public class GetLifeCycleByOidDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private InvCatalogueClient inventoryClient;
    
    @Autowired
    private AuthWsClient authWsClient;
    
    @Transactional(readOnly=true)
    public GetLifeCycleByOidResponseBody getAssetInfoByOid(String oid, AuthUser user) {
    	ImmutablePair<String, Object[]> query = GetLifeCycleByOidQuery.getAssetInfoSql(oid, user);
    	GetLifeCycleByOidResponseBody result = jdbcTemplate.queryForObject(query.getLeft(), query.getRight(), new GetLifeCycleByOidAssetInfoRowMapper());
    	
    	try {
    		InvItem item = inventoryClient.getItem(user.getToken(), result.getItemOid());
            result.itemValuesSetter(item);
    	} catch (Exception e) {
    		log.error("An exception occurred while getting item : ", e);
    	}

        try {
            JSONObject j = new JSONObject();
            j.put("userOid", result.getAssetAddedBy());
            EmployeeResponse response = authWsClient.getUsername((j.toString()));
            log.info("AddedBy name response : {}", response);
            if(response != null && response.getStatus() == 200) {
            	result.setAssetAddedByEn(response.getData().getNameEn());
            	result.setAssetAddedByBn(response.getData().getNameBn());
            }
        } catch (Exception e) {
            log.error("An exception occurred while getting AddedBy name  : ", e);
        }
        
        try {
            JSONObject j = new JSONObject();
            j.put("userOid", result.getQcBy());
            EmployeeResponse response = authWsClient.getUsername((j.toString()));
            log.info("QcBy name response : {}", response);
            if(response != null && response.getStatus() == 200) {
            	result.setQcByEn(response.getData().getNameEn());
            	result.setQcByBn(response.getData().getNameBn());
            }
        } catch (Exception e) {
            log.error("An exception occurred while getting QcBy name  : ", e);
        }
        
        try {
            JSONObject j = new JSONObject();
            j.put("userOid", result.getTaggedBy());
            EmployeeResponse response = authWsClient.getUsername((j.toString()));
            log.info("TaggedBy name response : {}", response);
            if(response != null && response.getStatus() == 200) {
            	result.setTaggedByEn(response.getData().getNameEn());
            	result.setTaggedByBn(response.getData().getNameBn());
            }
        } catch (Exception e) {
            log.error("An exception occurred while getting TaggedBy name  : ", e);
        }
        
        try {
            JSONObject j = new JSONObject();
            j.put("userOid", result.getReceivedBy());
            EmployeeResponse response = authWsClient.getUsername((j.toString()));
            log.info("ReceivedBy name response : {}", response);
            if(response != null && response.getStatus() == 200) {
            	result.setReceivedByEn(response.getData().getNameEn());
            	result.setReceivedByBn(response.getData().getNameBn());
            }
        } catch (Exception e) {
            log.error("An exception occurred while getting ReceivedBy name  : ", e);
        }
        
        try {
            JSONObject j = new JSONObject();
            j.put("userOid", result.getDecisionOnQcBy());
            EmployeeResponse response = authWsClient.getUsername((j.toString()));
            log.info("DecisionOnQcBy name response : {}", response);
            if(response != null && response.getStatus() == 200) {
            	result.setDecisionOnQcByNameEn(response.getData().getNameEn());
            	result.setDecisionOnQcByNameBn(response.getData().getNameBn());
            }
        } catch (Exception e) {
            log.error("An exception occurred while getting DecisionOnQcBy name  : ", e);
        }
        return result;
    }
    
    @Transactional(readOnly=true)
    public List<AssetAllocationDetails> getAssetAllocationDetails(String oid) {
    	ImmutablePair<String, Object[]> query = GetLifeCycleByOidQuery.getAssetAllocationDetailSql(oid);
        List<AssetAllocationDetails> result = jdbcTemplate.query(query.getLeft(), query.getRight(), new AssetAllocationDetailsRowMapper());
        for(int i=0; i<result.size(); i++) {
            try {
                JSONObject j = new JSONObject();
                j.put("userOid", result.get(i).getAllocatorOid());
                EmployeeResponse response = authWsClient.getUsername((j.toString()));
                log.info("ReceivedBy name response : {}", response);
                if(response != null && response.getStatus() == 200) {
                    result.get(i).setAllocatorNameEn(response.getData().getNameEn());
                    result.get(i).setAllocatorNameBn(response.getData().getNameBn());
                }
            } catch (Exception e) {
                log.error("An exception occurred while getting receivedBy name  : ", e);
            }
            try {
                JSONObject j = new JSONObject();
                j.put("userOid", result.get(i).getEndUserOid());
                EmployeeResponse response = authWsClient.getUsername((j.toString()));
                log.info("ReceivedBy name response : {}", response);
                if(response != null && response.getStatus() == 200) {
                    result.get(i).setEndUserNameEn(response.getData().getNameEn());
                    result.get(i).setEndUserNameBn(response.getData().getNameBn());
                }
            } catch (Exception e) {
                log.error("An exception occurred while getting receivedBy name  : ", e);
            }
       }
        return ListUtils.emptyIfNull(result);
    }
    
    @Transactional(readOnly=true)
    public List<ExpertDecisionDetail> getMaintenanceAndDisposalDetails(String oid, String status1, String status2, AuthUser user) {
    	ImmutablePair<String, Object[]> query = GetLifeCycleByOidQuery.getAssetMaintenanceAndDisposalInfoSql(oid, status1, status2, user);
        List<ExpertDecisionDetail> result = jdbcTemplate.query(query.getLeft(), query.getRight(), new ExpertDecisionRowMapper());
        for(int i=0; i<result.size(); i++) {
            try {
                JSONObject j = new JSONObject();
                j.put("userOid", result.get(i).getDecisionBy());
                EmployeeResponse response = authWsClient.getUsername((j.toString()));
                log.info("DecisionBy name response : {}", response);
                if(response != null && response.getStatus() == 200) {
                    result.get(i).setDecisionByEn(response.getData().getNameEn());
                    result.get(i).setDecisionByBn(response.getData().getNameBn());
                }
            } catch (Exception e) {
                log.error("An exception occurred while getting DecisionBy name  : ", e);
            }
       }
        return ListUtils.emptyIfNull(result);
    }
   

}
