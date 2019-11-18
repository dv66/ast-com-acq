package com.cokreates.grp.ast.comacq.directout.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cokreates.grp.ast.comacq.directout.response.GetByOid;
import com.cokreates.grp.ast.comacq.directout.response.GetByOidResponseBody;
import com.cokreates.grp.ast.comacq.directout.util.GetByOidDirectInRowMapper;
import com.cokreates.grp.ast.comacq.directout.util.GetByOidQuery;
import com.cokreates.grp.ast.comacq.directout.util.GetByOidRowMapper;
import com.cokreates.grp.client.AuthWsClient;
import com.cokreates.grp.client.CmnServiceOrganogramClient;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.model.EmployeeResponse;
import com.cokreates.grp.model.organogram.OfficeUnitPostRequest;
import com.cokreates.grp.model.organogram.OfficeUnitPostRequestBody;
import com.cokreates.grp.model.organogram.OfficeUnitPostResponse;
import com.cokreates.grp.model.organogram.OfficeUnitRequest;
import com.cokreates.grp.model.organogram.OfficeUnitRequestBody;
import com.cokreates.grp.model.organogram.OfficeUnitResponse;
import com.cokreates.grp.request.ServiceRequestHeader;

import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;

@Slf4j
@Repository("comacqDirectOutV1GetByOidDao")
public class GetByOidDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
   	private AuthWsClient authWsClient;
    
    @Autowired
    private CmnServiceOrganogramClient cmnServiceOrganogramClient;
    
    @Transactional(readOnly=true)
    public GetByOidResponseBody getDirectOutByOid(AuthUser user,String oid) {
    	ImmutablePair<String, Object[]> query = GetByOidQuery.getDirectOutSql(user, oid);
    	GetByOidResponseBody result = jdbcTemplate.queryForObject(query.getLeft(), query.getRight(), new GetByOidDirectInRowMapper());
    	
    	if(result.getEndUserOid() != null)
        {
    		try {
    			JSONObject j = new JSONObject();
    			j.put("userOid", result.getEndUserOid());
    			EmployeeResponse response = authWsClient.getUsername((j.toString()));
    			log.info("end user name response : {}", response);
    			if(response != null && response.getStatus() == 200) {
    				result.setEndUserNameEn(response.getData().getNameEn());
    				result.setEndUserNameBn(response.getData().getNameBn());
    			}
    		} catch (Exception e) {
    			log.error("An exception occurred while getting end user name : ", e);
    		}
        }
    	
    	if(result.getDecisionBy() != null)
        {
    		try {
    			JSONObject j = new JSONObject();
    			j.put("userOid", result.getDecisionBy());
    			EmployeeResponse response = authWsClient.getUsername((j.toString()));
    			log.info("decision name response : {}", response);
    			if(response != null && response.getStatus() == 200) {
    				result.setDecisionByNameEn(response.getData().getNameEn());
    				result.setDecisionByNameBn(response.getData().getNameBn());
    			}
    		} catch (Exception e) {
    			log.error("An exception occurred while getting decision name : ", e);
    		}
        }
    	
    	if(result.getRequestedBy() != null)
        {
    		try {
    			JSONObject j = new JSONObject();
    			j.put("userOid", result.getRequestedBy());
    			EmployeeResponse response = authWsClient.getUsername((j.toString()));
    			log.info("requester name response : {}", response);
    			if(response != null && response.getStatus() == 200) {
    				result.setRequestedByNameEn(response.getData().getNameEn());
    				result.setRequestedByNameBn(response.getData().getNameBn());
    			}
    		} catch (Exception e) {
    			log.error("An exception occurred while getting requester name : ", e);
    		}
        }
    	
        if(result.getOfficeUnitOid() != null)
        {
           	try {
        		OfficeUnitRequest request = setData(result.getOfficeUnitOid());   		
    			OfficeUnitResponse response = cmnServiceOrganogramClient.getOfficeUnit(request);
    			log.info("office unit name response : {}", response);
    			if(response != null && response.getHeader().getResponseCode().equals("200")) {
    				result.setOfficeUnitNameEn(response.getBody().getData().get(0).getNameEn());
    				result.setOfficeUnitNameBn(response.getBody().getData().get(0).getNameBn());
    			}
    		} catch (Exception e) {
    			log.error("An exception occurred while getting office unit  name : ", e);
    		}

        }
        
        if(result.getOfficeUnitPostOid() != null)
        {
           	try {
        		OfficeUnitPostRequest request = setOfficeUnitPostData(result.getOfficeUnitPostOid());
    			OfficeUnitPostResponse response = cmnServiceOrganogramClient.getOfficeUnitPost(request);
    			log.info("office unit post name response : {}", response);
    			if(response != null && response.getHeader().getResponseCode().equals("200")) {
    				result.setPostNameEn(response.getBody().getData().get(0).getPost().getNameEn());
    				result.setPostNameBn(response.getBody().getData().get(0).getPost().getNameBn());
    			}
    		} catch (Exception e) {
    			log.error("An exception occurred while getting office unit post name : ", e);
    		}

        }
    	
        return result;
    }

    @Transactional(readOnly=true)
    public List<GetByOid> getDirectOutDetailByOid(String oid) {
    	ImmutablePair<String, Object[]> query = GetByOidQuery.getDirectOutLineSql(oid);
        List<GetByOid> result = jdbcTemplate.query(query.getLeft(), query.getRight(), new GetByOidRowMapper());
        return ListUtils.emptyIfNull(result);
    }
    
    public OfficeUnitRequest setData(String oid){
    	
   	    OfficeUnitRequestBody body = new OfficeUnitRequestBody();
        body.setOid(oid);
   	
        ServiceRequestHeader srh = new ServiceRequestHeader();
	   	srh.setRequestId("random-uuid");
	   	srh.setRequestSource("portal");
	   	srh.setRequestSourceService("portal");
	   	srh.setHopCount(null);
	   	srh.setRequestClient("grp");
	   	srh.setRequestRetryCount(3);
	   	srh.setRequestVersion("v1");
	   	srh.setRequestType("random");
	   	srh.setRequestTime(new DateTime());
	   	srh.setRequestTimeoutInSeconds(30);
	   	srh.setTraceId(null);
	   	
	   	OfficeUnitRequest req = new OfficeUnitRequest();
	   	req.setHeader(srh);
	   	req.setMeta(new HashMap<String, Object>());
	   	req.setBody(body);
	   	
	   	
	   	return req;
   }
    
    public OfficeUnitPostRequest setOfficeUnitPostData(String oid){

    	OfficeUnitPostRequestBody body = new OfficeUnitPostRequestBody();
        body.setOid(oid);
    	
        ServiceRequestHeader srh = new ServiceRequestHeader();
    	srh.setRequestId("random-uuid");
    	srh.setRequestSource("portal");
    	srh.setRequestSourceService("portal");
    	srh.setHopCount(null);
    	srh.setRequestClient("grp");
    	srh.setRequestRetryCount(3);
    	srh.setRequestVersion("v1");
    	srh.setRequestType("random");
    	srh.setRequestTime(new DateTime());
    	srh.setRequestTimeoutInSeconds(30);
    	srh.setTraceId(null);
    	
    	OfficeUnitPostRequest req = new OfficeUnitPostRequest();
    	req.setHeader(srh);
    	req.setMeta(new HashMap<String, Object>());
    	req.setBody(body);
    	
    	
    	return req;
    }

}
