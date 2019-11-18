package com.cokreates.grp.ast.comacq.assetinfo.dao;


import org.apache.commons.lang3.tuple.ImmutablePair;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cokreates.grp.ast.comacq.assetinfo.response.GetByOidAfterTaggingResponseBody;
import com.cokreates.grp.ast.comacq.assetinfo.util.GetByOidAfterTaggingAssetInfoRowMapper;
import com.cokreates.grp.ast.comacq.assetinfo.util.GetByOidAfterTaggingQuery;
import com.cokreates.grp.client.AuthWsClient;
import com.cokreates.grp.client.InvCatalogueClient;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.model.EmployeeResponse;
import com.cokreates.grp.model.inv.InvItem;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository("comacqAssetInfoV1GetByOidAfterTaggingDao")
public class GetByOidAfterTaggingDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private InvCatalogueClient inventoryClient;
    
    @Autowired
    private AuthWsClient authWsClient;
    
    @Transactional(readOnly=true)
    public GetByOidAfterTaggingResponseBody getAssetInfoAfterTaggingByOid(AuthUser user, String oid) {
    	ImmutablePair<String, Object[]> query = GetByOidAfterTaggingQuery.getAssetInfoAfterTaggingSql(user, oid);
    	GetByOidAfterTaggingResponseBody result = jdbcTemplate.queryForObject(query.getLeft(), query.getRight(), new GetByOidAfterTaggingAssetInfoRowMapper());
        
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
            log.info("QcBy name response : {}", response);
            if(response != null && response.getStatus() == 200) {
            	result.setTaggedByEn(response.getData().getNameEn());
            	result.setTaggedByBn(response.getData().getNameBn());
            }
        } catch (Exception e) {
            log.error("An exception occurred while getting QcBy name  : ", e);
        }
        
        return result;
    }

   

}
