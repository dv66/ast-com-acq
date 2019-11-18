package com.cokreates.grp.ast.comacq.directin.dao;

import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cokreates.grp.ast.comacq.directin.model.DirectInApproval;
import com.cokreates.grp.ast.comacq.directin.request.GetListApprovalRequest;
import com.cokreates.grp.ast.comacq.directin.util.GetListApprovalQuery;
import com.cokreates.grp.ast.comacq.directin.util.GetListApprovalRowMapper;
import com.cokreates.grp.client.AuthWsClient;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.model.EmployeeResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository("comAcqDirectInV1GetListApprovalDao")
public class GetListApprovalDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
   	private AuthWsClient authWsClient;

    
    @Transactional
    public List<DirectInApproval> findAll(GetListApprovalRequest request, AuthUser user) {
        ImmutablePair<String, Object[]> query = GetListApprovalQuery.getDirectInSql(request, user);
        List<DirectInApproval> result = jdbcTemplate.query(query.getLeft(), query.getRight(), new GetListApprovalRowMapper());
        
        for (int i = 0; i < result.size(); i++ )
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

        return ListUtils.emptyIfNull(result);
    }
    
    @Transactional
    public int getDataCount(GetListApprovalRequest request, AuthUser user) {
        ImmutablePair<String, Object[]> query = GetListApprovalQuery.getDataCountSql(request, user);
        int count = jdbcTemplate.queryForObject(query.getKey(), query.getValue(), Integer.class);
        return count;
    }
    

}
