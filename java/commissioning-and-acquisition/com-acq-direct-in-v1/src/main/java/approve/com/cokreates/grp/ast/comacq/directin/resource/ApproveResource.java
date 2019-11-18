package com.cokreates.grp.ast.comacq.directin.resource;

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

import com.cokreates.grp.ast.comacq.directin.request.ApproveRequest;
import com.cokreates.grp.ast.comacq.directin.response.ApproveResponse;
import com.cokreates.grp.ast.comacq.directin.response.ApproveResponseBody;
import com.cokreates.grp.ast.comacq.directin.service.ApproveService;
import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.response.ServiceResponseHeader;
import com.cokreates.grp.util.HeaderUtil;
import com.cokreates.grp.util.constant.Api;
import com.cokreates.grp.util.constant.Constant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController("comAcqDirectInV1ApproveResource")
@RequestMapping(Api.COMACQ_DIRECTIN_V1_RESOURCE)
public class ApproveResource {
	
	private final String REQUEST_VERSION = "1.0";

	@Autowired
	@Qualifier("headerUtil")
	private HeaderUtil headerUtil;
	
	@Autowired
	@Qualifier("comAcqDirectInV1ApproveService")
	private ApproveService service;

	// @HystrixCommand(fallbackMethod = "fallbackGetAllOnPost")
	@PostMapping(path = Api.COMACQ_DIRECTIN_V1_APPROVE_PATH, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ApproveResponse onPost(@Valid @RequestBody ApproveRequest request, @RequestHeader(value="Authorization") String token) throws AppException {
		String url = Api.COMACQ_DIRECTIN_V1_RESOURCE.concat(Api.COMACQ_DIRECTIN_V1_APPROVE_PATH);
		log.info("Resource : {}", url);
		log.info("Request received : {}", request);
		AuthUser user = Constant.getAuthUser(request.getHeader(), token);
		ApproveResponse response = getResponse(request, url);
		response = service.perform(user, url, REQUEST_VERSION, request, response);
		log.info("Resource : {}", url);
		log.info("Response sent : {}", response);
		return response;
	}

	private ApproveResponse getResponse(ApproveRequest request, String requestSourceService){
		ServiceResponseHeader header = headerUtil.getResponseHeader(request.getHeader(), REQUEST_VERSION, requestSourceService);
		ApproveResponseBody body = ApproveResponseBody.builder().build();
		return ApproveResponse.builder().header(header).meta(request.getMeta()).body(body).build();
	}

	public ApproveResponse fallbackGetAllOnPost(ApproveRequest request) {
		String url = Api.COMACQ_DIRECTIN_V1_RESOURCE.concat(Api.COMACQ_DIRECTIN_V1_APPROVE_PATH);
		ApproveResponse response = getResponse(request, url);
		return response;
	}

}
