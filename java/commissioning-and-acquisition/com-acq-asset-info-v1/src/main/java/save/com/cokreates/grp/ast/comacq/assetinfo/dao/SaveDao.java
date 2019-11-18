package com.cokreates.grp.ast.comacq.assetinfo.dao;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cokreates.grp.ast.comacq.assetinfo.model.AssetInfo;
import com.cokreates.grp.ast.comacq.assetinfo.request.SaveRequest;
import com.cokreates.grp.ast.comacq.assetinfo.util.SaveQuery;
import com.cokreates.grp.model.AuthUser;

@Repository("comAcqAssetInfoV1SaveDao")
public class SaveDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Transactional
    public void saveAssetInfo(AuthUser user, SaveRequest request) throws Exception {

    	for(AssetInfo assetInfo : request.getBody().getData().getAssetItems()) {
    		ImmutablePair<String, Object[]> query = SaveQuery.saveAssetInfoSql(user, assetInfo);
            int result = jdbcTemplate.update(query.getLeft(), query.getRight());
            if(result != 1) {
            	throw new Exception("No record Asset Information insert!!!");
            }
            
            if(assetInfo.getExpiryDuration() != null || assetInfo.getSerialNo() != null || assetInfo.getRemarks() != null)
            {
            	ImmutablePair<String, Object[]> query1 = SaveQuery.saveAssetInfoDetailSql(assetInfo);
                result = jdbcTemplate.update(query1.getLeft(), query1.getRight());
                if(result != 1) {
                	throw new Exception(" Asset Information detail not updated!!!");
                }
            	
            }
    	}
    	
    	String temporaryItemCode = request.getBody().getData().getTempOid();
    	ImmutablePair<String, Object[]> query2 = SaveQuery.updateTemporaryItemSql(user, temporaryItemCode);
    	int result = jdbcTemplate.update(query2.getLeft(), query2.getRight());
        if(result != 1) {
        	throw new Exception("No record updated into Temporary Item!!!");
        }
        
        
    	
    }
    
    @Transactional
    public String checkTemporaryItemStatus(AuthUser user, SaveRequest request) throws Exception {
    	
        String temporaryItemCode = request.getBody().getData().getTempOid();
    	ImmutablePair<String, Object[]> tempQuery = SaveQuery.getTemporaryItemStatusSql(user, temporaryItemCode);
    	String tempStatus =jdbcTemplate.queryForObject(tempQuery.getLeft(),tempQuery.getRight(), String.class);
    	
    	return tempStatus;
    }
 
}
