package com.cokreates.grp.ast.comacq.assetinfo.resource;

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

import com.cokreates.grp.ast.comacq.assetinfo.request.GetByOidBeforeTaggingRequest;
import com.cokreates.grp.ast.comacq.assetinfo.response.GetByOidBeforeTaggingResponse;
import com.cokreates.grp.ast.comacq.assetinfo.response.GetByOidBeforeTaggingResponseBody;
import com.cokreates.grp.ast.comacq.assetinfo.service.GetByOidBeforeTaggingService;
import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.response.ServiceResponseHeader;
import com.cokreates.grp.util.HeaderUtil;
import com.cokreates.grp.util.constant.Api;
import com.cokreates.grp.util.constant.Constant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController("comacqAssetInfoV1GetByOidBeforeTaggingResource")
@RequestMapping(Api.COMACQ_ASSETINFO_V1_RESOURCE)
public class GetByOidBeforeTaggingResource {
	
	private final String REQUEST_VERSION = "1.0";

	@Autowired
	@Qualifier("headerUtil")
	private HeaderUtil headerUtil;
	
	@Autowired
	@Qualifier("comacqAssetInfoV1GetByOidBeforeTaggingService")
	private GetByOidBeforeTaggingService service;

	// @HystrixCommand(fallbackMethod = "fallbackGetAllOnPost")
	@PostMapping(path = Api.COMACQ_ASSETINFO_V1_GET_BY_OID_BEFORE_TAGGING_PATH, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody GetByOidBeforeTaggingResponse onPost(@Valid @RequestBody GetByOidBeforeTaggingRequest request, @RequestHeader(value="Authorization") String token) throws AppException {
		String url = Api.COMACQ_ASSETINFO_V1_RESOURCE.concat(Api.COMACQ_ASSETINFO_V1_GET_BY_OID_BEFORE_TAGGING_PATH);
		log.info("Resource : {}", url);
		log.info("Request received : {}", request);
		AuthUser user = Constant.getAuthUser(request.getHeader(), token);
		GetByOidBeforeTaggingResponse response = getResponse(request, url);
		response = service.perform(user, url, REQUEST_VERSION, request, response);
		log.info("Resource : {}", url);
		log.info("Response sent : {}", response);
		return response;
	}

	private GetByOidBeforeTaggingResponse getResponse(GetByOidBeforeTaggingRequest request, String requestSourceService){
		ServiceResponseHeader header = headerUtil.getResponseHeader(request.getHeader(), REQUEST_VERSION, requestSourceService);
		GetByOidBeforeTaggingResponseBody body = GetByOidBeforeTaggingResponseBody.builder().build();
		return GetByOidBeforeTaggingResponse.builder().header(header).meta(request.getMeta()).body(body).build();
	}

	public GetByOidBeforeTaggingResponse fallbackGetAllOnPost(GetByOidBeforeTaggingRequest request) {
		String url = Api.COMACQ_ASSETINFO_V1_RESOURCE.concat(Api.COMACQ_ASSETINFO_V1_GET_BY_OID_BEFORE_TAGGING_PATH);
		GetByOidBeforeTaggingResponse response = getResponse(request, url);
		return response;
	}

}
