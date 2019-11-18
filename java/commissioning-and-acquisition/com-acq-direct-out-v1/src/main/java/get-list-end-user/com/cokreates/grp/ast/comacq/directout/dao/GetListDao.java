package com.cokreates.grp.ast.comacq.directout.dao;

import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cokreates.grp.ast.comacq.directout.model.DirectOut;
import com.cokreates.grp.ast.comacq.directout.request.GetListRequest;
import com.cokreates.grp.ast.comacq.directout.util.GetListQuery;
import com.cokreates.grp.ast.comacq.directout.util.GetListRowMapper;
import com.cokreates.grp.client.AuthWsClient;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.model.EmployeeResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository("comAcqDirectOutV1GetListEndUserDao")
public class GetListDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
   	private AuthWsClient authWsClient;

    
    @Transactional
    public List<DirectOut> findAll(GetListRequest request, AuthUser user) {
        ImmutablePair<String, Object[]> query = GetListQuery.getDirectOutSql(request, user);
        List<DirectOut> result = jdbcTemplate.query(query.getLeft(), query.getRight(), new GetListRowMapper());
        
        for (int i = 0; i < result.size(); i++ )
    	{
        	if(result.get(i).getEndUserOid() != null)
            {
        		try {
        			JSONObject j = new JSONObject();
        			j.put("userOid", result.get(i).getEndUserOid());
        			EmployeeResponse response = authWsClient.getUsername((j.toString()));
        			log.info("end user name response : {}", response);
        			if(response != null && response.getStatus() == 200) {
        				result.get(i).setEndUserNameEn(response.getData().getNameEn());
        				result.get(i).setEndUserNameBn(response.getData().getNameBn());
        			}
        		} catch (Exception e) {
        			log.error("An exception occurred while getting end user name : ", e);
        		}
            }
    	}
        
        return ListUtils.emptyIfNull(result);
    }
    
    @Transactional
    public int getDataCount(GetListRequest request, AuthUser user) {
        ImmutablePair<String, Object[]> query = GetListQuery.getDataCountSql(request, user);
        int count = jdbcTemplate.queryForObject(query.getKey(), query.getValue(), Integer.class);
        return count;
    }
    

}
