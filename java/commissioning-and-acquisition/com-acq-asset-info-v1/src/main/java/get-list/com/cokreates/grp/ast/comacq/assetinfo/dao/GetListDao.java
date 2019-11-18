package com.cokreates.grp.ast.comacq.assetinfo.dao;

import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cokreates.grp.ast.comacq.assetinfo.model.GetListAssetInfo;
import com.cokreates.grp.ast.comacq.assetinfo.request.GetListRequest;
import com.cokreates.grp.ast.comacq.assetinfo.util.GetListQuery;
import com.cokreates.grp.ast.comacq.assetinfo.util.GetListRowMapper;
import com.cokreates.grp.client.InvCatalogueClient;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.model.inv.InvItem;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository("comacqAssetInfoV1GetListDao")
public class GetListDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private InvCatalogueClient inventoryClient;
    
    @Transactional(readOnly=true)
    public List<GetListAssetInfo> findAll(AuthUser user, GetListRequest request) {
    	ImmutablePair<String, Object[]> query = GetListQuery.getAssetInfoSql(user, request);
        List<GetListAssetInfo> result = jdbcTemplate.query(query.getLeft(), query.getRight(), new GetListRowMapper());
        for(int i =0; i<result.size(); i++) {
        	try {
        		InvItem item = inventoryClient.getItem(user.getToken(), result.get(i).getItemOid());
            	result.get(i).itemValuesSetter(item);
        	} catch (Exception e) {
        		log.error("An exception occurred while getting item : ", e);
        	}
        }
        return ListUtils.emptyIfNull(result);
    }
    
    @Transactional
    public int getDataCount(AuthUser user, GetListRequest request) {
        ImmutablePair<String, Object[]> query = GetListQuery.getDataCountSql(user, request);
        int count = jdbcTemplate.queryForObject(query.getKey(), query.getValue(), Integer.class);
        return count;
    }

}
