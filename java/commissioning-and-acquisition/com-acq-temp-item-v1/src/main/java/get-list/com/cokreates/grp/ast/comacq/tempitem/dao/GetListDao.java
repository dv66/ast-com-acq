package com.cokreates.grp.ast.comacq.tempitem.dao;

import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cokreates.grp.ast.comacq.tempitem.model.TempItem;
import com.cokreates.grp.ast.comacq.tempitem.request.GetListRequest;
import com.cokreates.grp.ast.comacq.tempitem.util.GetListQuery;
import com.cokreates.grp.ast.comacq.tempitem.util.GetListRowMapper;
import com.cokreates.grp.client.AuthWsClient;
import com.cokreates.grp.model.EmployeeResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository("comAcqTempItemV1GetListDao")
public class GetListDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AuthWsClient authWsClient;
    
    @Transactional
    public List<TempItem> findAll(GetListRequest request) {
        ImmutablePair<String, Object[]> query = GetListQuery.getTemporaryItemSql(request);
        List<TempItem> result = jdbcTemplate.query(query.getLeft(), query.getRight(), new GetListRowMapper());
        for(int i=0; i<result.size(); i++) {
	    	try {
				JSONObject j = new JSONObject();
				j.put("userOid", result.get(i).getReceivedBy());
				EmployeeResponse response = authWsClient.getUsername((j.toString()));
				log.info("ReceivedBy name response : {}", response);
				if(response != null && response.getStatus() == 200) {
					result.get(i).setReceivedByEn(response.getData().getNameEn());
					result.get(i).setReceivedByBn(response.getData().getNameBn());
				}
			} catch (Exception e) {
				log.error("An exception occurred while getting receivedBy name  : ", e);
			}
        }
        return ListUtils.emptyIfNull(result);
    }
    
    @Transactional
    public int getDataCount(GetListRequest request) {
        ImmutablePair<String, Object[]> query = GetListQuery.getDataCountSql(request);
        int count = jdbcTemplate.queryForObject(query.getKey(), query.getValue(), Integer.class);
        return count;
    }
    

}
