package com.cokreates.grp.ast.comacq.directout.resource;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cokreates.grp.ast.comacq.directout.request.GetListApprovalRequest;
import com.cokreates.grp.ast.comacq.directout.response.GetListApprovalResponse;
import com.cokreates.grp.ast.comacq.directout.response.GetListApprovalResponseBody;
import com.cokreates.grp.ast.comacq.directout.service.GetApprovalService;
import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.response.ServiceResponseHeader;
import com.cokreates.grp.util.HeaderUtil;
import com.cokreates.grp.util.constant.Api;
import com.cokreates.grp.util.constant.Constant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController("comAcqDirectOutV1GetListApprovalResource")
@RequestMapping(Api.COMACQ_DIRECTOUT_V1_RESOURCE)
public class GetListApprovalResource {
	
	private final String REQUEST_VERSION = "1.0";

	@Autowired
	@Qualifier("headerUtil")
	private HeaderUtil headerUtil;
	
	@Autowired
	@Qualifier("comAcqDirectOutV1GetListApprovalService")
	private GetApprovalService service;

	// @HystrixCommand(fallbackMethod = "fallbackGetAllOnPost")
	@PostMapping(path = Api.COMACQ_DIRECTOUT_V1_GET_LIST_APPROVAL_PATH, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody GetListApprovalResponse onPost(@Valid @RequestBody GetListApprovalRequest request, @RequestHeader(value="Authorization") String token) throws AppException {
		String url = Api.COMACQ_DIRECTOUT_V1_RESOURCE.concat(Api.COMACQ_DIRECTOUT_V1_GET_LIST_APPROVAL_PATH);
		log.info("Resource : {}", url);
		log.info("Request received : {}", request);
		AuthUser user = Constant.getAuthUser(request.getHeader(), token);
		GetListApprovalResponse response = getResponse(request, url);
		response = service.perform(user, url, REQUEST_VERSION, request, response);
		log.info("Resource : {}", url);
		log.info("Response sent : {}", response);
		return response;
	}

	private GetListApprovalResponse getResponse(GetListApprovalRequest request, String requestSourceService){
		ServiceResponseHeader header = headerUtil.getResponseHeader(request.getHeader(), REQUEST_VERSION, requestSourceService);
		GetListApprovalResponseBody body = GetListApprovalResponseBody.builder().build();
		return GetListApprovalResponse.builder().header(header).meta(request.getMeta()).body(body).build();
	}

	public GetListApprovalResponse fallbackGetAllOnPost(GetListApprovalRequest request) {
		String url = Api.COMACQ_DIRECTOUT_V1_RESOURCE.concat(Api.COMACQ_DIRECTOUT_V1_GET_LIST_APPROVAL_PATH);
		GetListApprovalResponse response = getResponse(request, url);
		return response;
	}

}
