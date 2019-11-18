package com.cokreates.grp.ast.comacq.assetinfo.dao;

import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cokreates.grp.ast.comacq.assetinfo.model.DraftGetListAssetInfo;
import com.cokreates.grp.ast.comacq.assetinfo.request.DraftGetListByTempOidRequest;
import com.cokreates.grp.ast.comacq.assetinfo.util.DraftGetListByTempOidQuery;
import com.cokreates.grp.ast.comacq.assetinfo.util.DraftGetListByTempOidRowMapper;
import com.cokreates.grp.client.InvCatalogueClient;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.model.inv.InvItem;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository("comacqAssetInfoV1DraftGetListByTempOidDao")
public class DraftGetListByTempOidDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private InvCatalogueClient inventoryClient;

    @Transactional(readOnly=true)
    public List<DraftGetListAssetInfo> findAll(AuthUser user, DraftGetListByTempOidRequest request) {
    	ImmutablePair<String, Object[]> query = DraftGetListByTempOidQuery.getAssetInfoSql(user, request);
        List<DraftGetListAssetInfo> result = jdbcTemplate.query(query.getLeft(), query.getRight(), new DraftGetListByTempOidRowMapper());
        for(int i = 0; i < result.size(); i++)
        {
        	try {
        		InvItem temp = inventoryClient.getItem(user.getToken(), result.get(i).getItemOid());
            	result.get(i).setItemNameEn(temp.getItemName());
            	result.get(i).setItemNameBn(temp.getBnItemName());
        	} catch (Exception e) {
        		log.error("An exception occurred while getting item : ", e);
        	}
        	
        }
        return ListUtils.emptyIfNull(result);
    }
    
	@Transactional
    public String checkTemporaryItemStatus(AuthUser user, String oid) throws Exception {
    	
    	ImmutablePair<String, Object[]> tempQuery = DraftGetListByTempOidQuery.getTemporaryItemStatusSql(user, oid);
    	String tempStatus =jdbcTemplate.queryForObject(tempQuery.getLeft(),tempQuery.getRight(), String.class);
        return tempStatus;
    	
    }

}
