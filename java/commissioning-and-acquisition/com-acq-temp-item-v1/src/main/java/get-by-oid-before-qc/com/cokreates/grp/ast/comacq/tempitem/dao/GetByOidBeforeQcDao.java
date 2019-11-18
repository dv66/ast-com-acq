package com.cokreates.grp.ast.comacq.tempitem.dao;

import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cokreates.grp.ast.comacq.tempitem.response.GetByOidBeforeQc;
import com.cokreates.grp.ast.comacq.tempitem.response.GetByOidBeforeQcResponseBody;
import com.cokreates.grp.ast.comacq.tempitem.util.GetByOidBeforeQcQuery;
import com.cokreates.grp.ast.comacq.tempitem.util.GetByOidBeforeQcRowMapper;
import com.cokreates.grp.ast.comacq.tempitem.util.GetByOidTemporaryItemBeforeQcRowMapper;
import com.cokreates.grp.client.AuthWsClient;
import com.cokreates.grp.client.InvCatalogueClient;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.model.EmployeeResponse;
import com.cokreates.grp.model.inv.InvItem;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository("comacqTempitemV1GetByOidBeforeQcDao")
public class GetByOidBeforeQcDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private InvCatalogueClient inventoryClient;

    @Autowired
    private AuthWsClient authWsClient;
     
    @Transactional(readOnly=true)
    public GetByOidBeforeQcResponseBody getTemporaryItemByOid(AuthUser user, String oid) {
    	ImmutablePair<String, Object[]> query = GetByOidBeforeQcQuery.getTemporaryItemSql(user, oid);
    	GetByOidBeforeQcResponseBody result = jdbcTemplate.queryForObject(query.getLeft(), query.getRight(), new GetByOidTemporaryItemBeforeQcRowMapper());
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
        return result;
    }

    @Transactional(readOnly=true)
    public List<GetByOidBeforeQc> getTemporaryItemDetailByOid(AuthUser user, String oid) {
    	ImmutablePair<String, Object[]> query = GetByOidBeforeQcQuery.getTemporaryItemDetailSql(oid);
        List<GetByOidBeforeQc> result = jdbcTemplate.query(query.getLeft(), query.getRight(), new GetByOidBeforeQcRowMapper());
        for(int i=0; i<result.size(); i++) {
        	try {
            	InvItem item = inventoryClient.getItem(user.getToken(), result.get(i).getItemOid());
            	result.get(i).setItemNameBn(item.getBnItemName());
            	result.get(i).setItemNameEn(item.getItemName());
            	result.get(i).setUomNameBn(item.getItemSetupDto().getUom().getBnUomId());
            	result.get(i).setUomNameEn(item.getItemSetupDto().getUom().getUnitName());
        		
        	} catch (Exception e) {
        		log.error("An exception occurred while getting item : ", e);
        	}
        }
        return ListUtils.emptyIfNull(result);
    }

}
