package com.cokreates.grp.ast.comacq.directout.dao;

import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cokreates.grp.ast.comacq.directout.model.DirectOutApproval;
import com.cokreates.grp.ast.comacq.directout.request.GetListApprovalRequest;
import com.cokreates.grp.ast.comacq.directout.util.GetListApprovalQuery;
import com.cokreates.grp.ast.comacq.directout.util.GetListApprovalRowMapper;
import com.cokreates.grp.client.AuthWsClient;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.model.EmployeeResponse;

import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;

@Slf4j
@Repository("comAcqDirectOutV1GetListApprovalDao")
public class GetListApprovalDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
   	private AuthWsClient authWsClient;

    
    @Transactional
    public List<DirectOutApproval> findAll(GetListApprovalRequest request, AuthUser user) {
        ImmutablePair<String, Object[]> query = GetListApprovalQuery.getDirectOutSql(request, user);
        List<DirectOutApproval> result = jdbcTemplate.query(query.getLeft(), query.getRight(), new GetListApprovalRowMapper());
        
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
         	
         	if(result.get(i).getRequestedBy() != null)
            {
        		try {
        			JSONObject j = new JSONObject();
        			j.put("userOid", result.get(i).getRequestedBy());
        			EmployeeResponse response = authWsClient.getUsername((j.toString()));
        			log.info("requester name response : {}", response);
        			if(response != null && response.getStatus() == 200) {
        				result.get(i).setRequestedByNameEn(response.getData().getNameEn());
        				result.get(i).setRequestedByNameBn(response.getData().getNameBn());
        			}
        		} catch (Exception e) {
        			log.error("An exception occurred while getting requester name : ", e);
        		}
            }
     	}
        
        return ListUtils.emptyIfNull(result);
    }
    
    @Transactional
    public int getDataCount(GetListApprovalRequest request, AuthUser user) {
        ImmutablePair<String, Object[]> query = GetListApprovalQuery.getDataCountSql(request, user);
        int count = jdbcTemplate.queryForObject(query.getKey(), query.getValue(), Integer.class);
        return count;
    }
    

}
