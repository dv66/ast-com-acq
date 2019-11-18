package com.cokreates.grp.ast.comacq.assetinfo.dao;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cokreates.grp.ast.comacq.assetinfo.model.DraftAssetInfo;
import com.cokreates.grp.ast.comacq.assetinfo.request.DraftRequest;
import com.cokreates.grp.ast.comacq.assetinfo.util.DraftQuery;
import com.cokreates.grp.model.AuthUser;

@Repository("comAcqAssetInfoV1DraftDao")
public class DraftDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Transactional
    public void draftAssetInfo(AuthUser user, DraftRequest request) throws Exception {

    	for(DraftAssetInfo assetInfo : request.getBody().getData().getAssetItems()) {
    		ImmutablePair<String, Object[]> query = DraftQuery.saveAssetInfoSql(user, assetInfo);
            int result = jdbcTemplate.update(query.getLeft(), query.getRight());
            if(result != 1) {
            	throw new Exception("Not Drafted Asset Information!!!");
            }
            
            if(assetInfo.getExpiryDuration() != null || assetInfo.getSerialNo() != null || assetInfo.getRemarks() != null)
            {
            	ImmutablePair<String, Object[]> query1 = DraftQuery.saveAssetInfoDetailSql(assetInfo);
                result = jdbcTemplate.update(query1.getLeft(), query1.getRight());
                if(result != 1) {
                	throw new Exception(" Asset Information detail not updated!!!");
                }
            	
            }
    	}
    	
    	
    }
    
    @Transactional
    public String checkTemporaryItemStatus(AuthUser user, DraftRequest request) throws Exception {
    	
        String temporaryItemCode = request.getBody().getData().getTempOid();
    	ImmutablePair<String, Object[]> tempQuery = DraftQuery.getTemporaryItemStatusSql(user, temporaryItemCode);
    	String tempStatus =jdbcTemplate.queryForObject(tempQuery.getLeft(),tempQuery.getRight(), String.class);
    	
    	return tempStatus;
    }
 
}
