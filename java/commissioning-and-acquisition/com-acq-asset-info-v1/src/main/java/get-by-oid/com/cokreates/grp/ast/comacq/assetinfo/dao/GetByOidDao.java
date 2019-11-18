package com.cokreates.grp.ast.comacq.assetinfo.dao;


import org.apache.commons.lang3.tuple.ImmutablePair;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cokreates.grp.ast.comacq.assetinfo.response.GetByOidResponseBody;
import com.cokreates.grp.ast.comacq.assetinfo.util.GetByOidAssetInfoRowMapper;
import com.cokreates.grp.ast.comacq.assetinfo.util.GetByOidQuery;
import com.cokreates.grp.client.AuthWsClient;
import com.cokreates.grp.client.InvCatalogueClient;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.model.EmployeeResponse;
import com.cokreates.grp.model.inv.InvItem;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Repository("comacqAssetInfoV1GetByOidDao")
public class GetByOidDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private InvCatalogueClient inventoryClient;
    
    @Autowired
    private AuthWsClient authWsClient;
    
    @Transactional(readOnly=true)
    public GetByOidResponseBody getAssetInfoByOid(String oid, AuthUser user) {
    	ImmutablePair<String, Object[]> query = GetByOidQuery.getAssetInfoSql(oid);
    	GetByOidResponseBody temp =  jdbcTemplate.queryForObject(query.getLeft(), query.getRight(), new GetByOidAssetInfoRowMapper());
    	InvItem item = inventoryClient.getItem(user.getToken(), temp.getItemOid());
    	temp.setItemNameEn(item.getItemName());
    	temp.setItemNameBn(item.getBnItemName());
    	 try {
             JSONObject j = new JSONObject();
             j.put("userOid", temp.getAssetAddedBy());
             EmployeeResponse response = authWsClient.getUsername((j.toString()));
             log.info("AddedBy name response : {}", response);
             if(response != null && response.getStatus() == 200) {
            	 temp.setAssetAddedByEn(response.getData().getNameEn());
            	 temp.setAssetAddedByBn(response.getData().getNameBn());
             }
         } catch (Exception e) {
             log.error("An exception occurred while getting AddedBy name  : ", e);
         }
    	 try {
             JSONObject j = new JSONObject();
             j.put("userOid", temp.getQcBy());
             EmployeeResponse response = authWsClient.getUsername((j.toString()));
             log.info("QcBy name response : {}", response);
             if(response != null && response.getStatus() == 200) {
            	 temp.setQcByEn(response.getData().getNameEn());
            	 temp.setQcByBn(response.getData().getNameBn());
             }
         } catch (Exception e) {
             log.error("An exception occurred while getting QcBy name  : ", e);
         }
    	return temp;
    
    }
    


   

}
