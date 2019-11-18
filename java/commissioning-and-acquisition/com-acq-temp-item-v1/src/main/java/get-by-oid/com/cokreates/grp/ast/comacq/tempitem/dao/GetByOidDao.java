package com.cokreates.grp.ast.comacq.tempitem.dao;

import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cokreates.grp.ast.comacq.tempitem.response.GetByOid;
import com.cokreates.grp.ast.comacq.tempitem.response.GetByOidResponseBody;
import com.cokreates.grp.ast.comacq.tempitem.util.GetByOidQuery;
import com.cokreates.grp.ast.comacq.tempitem.util.GetByOidRowMapper;
import com.cokreates.grp.ast.comacq.tempitem.util.GetByOidTemporaryItemRowMapper;
import com.cokreates.grp.client.AuthWsClient;
import com.cokreates.grp.client.InvCatalogueClient;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.model.EmployeeResponse;
import com.cokreates.grp.model.inv.InvItem;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository("comacqTempitemV1GetByOidDao")
public class GetByOidDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private InvCatalogueClient inventoryClient;
    
    @Autowired
    private AuthWsClient authWsClient;
     
    @Transactional(readOnly=true)
    public GetByOidResponseBody getTemporaryItemByOid(AuthUser user,String oid) {
    	ImmutablePair<String, Object[]> query = GetByOidQuery.getTemporaryItemSql(user, oid);
    	GetByOidResponseBody result = jdbcTemplate.queryForObject(query.getLeft(), query.getRight(), new GetByOidTemporaryItemRowMapper());
    	                try {
    						JSONObject j = new JSONObject();
    						j.put("userOid", result.getDecisionOnQcBy());
    						EmployeeResponse response = authWsClient.getUsername((j.toString()));
    						log.info("decisionOnQC name response : {}", response);
    						if(response != null && response.getStatus() == 200) {
    							result.setDecisionOnQcByEn(response.getData().getNameEn());
    							result.setDecisionOnQcByBn(response.getData().getNameBn());
    						}
    					} catch (Exception e) {
    						log.error("An exception occurred while getting decisionOnQC name : ", e);
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
    						log.error("An exception occurred while getting receivedBy name  : ", e);
    					}
    			    	try {
    						JSONObject j = new JSONObject();
    						j.put("userOid", result.getQcBy());
    						EmployeeResponse response = authWsClient.getUsername((j.toString()));
    						log.info("QCby name response : {}", response);
    						if(response != null && response.getStatus() == 200) {
    							result.setQcByEn(response.getData().getNameEn());
    							result.setQcByBn(response.getData().getNameBn());
    						}
    					} catch (Exception e) {
    						log.error("An exception occurred while getting QCby name : ", e);
    					}
    			    	
    			    	try {
    						JSONObject j = new JSONObject();
    						j.put("userOid", result.getAssetAddedBy());
    						EmployeeResponse response = authWsClient.getUsername((j.toString()));
    						log.info("AssetAddedBy name response : {}", response);
    						if(response != null && response.getStatus() == 200) {
    							result.setAssetAddedByEn(response.getData().getNameEn());
    							result.setAssetAddedByBn(response.getData().getNameBn());
    						}
    					} catch (Exception e) {
    						log.error("An exception occurred while getting AssetAddedBy name  : ", e);
    					}
        return result;
    }

    @Transactional(readOnly=true)
    public List<GetByOid> getTemporaryItemDetailByOid(AuthUser user, String oid) {
    	ImmutablePair<String, Object[]> query = GetByOidQuery.getTemporaryItemDetailSql(oid);
        List<GetByOid> result = jdbcTemplate.query(query.getLeft(), query.getRight(), new GetByOidRowMapper());
        try {
			for(int i=0; i<result.size(); i++) {
				InvItem item = inventoryClient.getItem(user.getToken(), result.get(i).getItemOid());
				result.get(i).itemValuesSetter(item);
			}
		} catch (Exception e) {
			log.error("An exception occurred while getting item : ", e);
		}
        return ListUtils.emptyIfNull(result);
    }

}
