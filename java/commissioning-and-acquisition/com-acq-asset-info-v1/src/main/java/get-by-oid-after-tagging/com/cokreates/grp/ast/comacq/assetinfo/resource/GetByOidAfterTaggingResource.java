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

import com.cokreates.grp.ast.comacq.assetinfo.request.GetByOidAfterTaggingRequest;
import com.cokreates.grp.ast.comacq.assetinfo.response.GetByOidAfterTaggingResponse;
import com.cokreates.grp.ast.comacq.assetinfo.response.GetByOidAfterTaggingResponseBody;
import com.cokreates.grp.ast.comacq.assetinfo.service.GetByOidAfterTaggingService;
import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.response.ServiceResponseHeader;
import com.cokreates.grp.util.HeaderUtil;
import com.cokreates.grp.util.constant.Api;
import com.cokreates.grp.util.constant.Constant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController("comacqAssetInfoV1GetByOidAfterTaggingResource")
@RequestMapping(Api.COMACQ_ASSETINFO_V1_RESOURCE)
public class GetByOidAfterTaggingResource {
	
	private final String REQUEST_VERSION = "1.0";

	@Autowired
	@Qualifier("headerUtil")
	private HeaderUtil headerUtil;
	
	@Autowired
	@Qualifier("comacqAssetInfoV1GetByOidAfterTaggingService")
	private GetByOidAfterTaggingService service;

	// @HystrixCommand(fallbackMethod = "fallbackGetAllOnPost")
	@PostMapping(path = Api.COMACQ_ASSETINFO_V1_GET_BY_OID_AFTER_TAGGING_PATH, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody GetByOidAfterTaggingResponse onPost(@Valid @RequestBody GetByOidAfterTaggingRequest request, @RequestHeader(value="Authorization") String token) throws AppException {
		String url = Api.COMACQ_ASSETINFO_V1_RESOURCE.concat(Api.COMACQ_ASSETINFO_V1_GET_BY_OID_AFTER_TAGGING_PATH);
		log.info("Resource : {}", url);
		log.info("Request received : {}", request);
		AuthUser user = Constant.getAuthUser(request.getHeader(), token);
		GetByOidAfterTaggingResponse response = getResponse(request, url);
		response = service.perform(user, url, REQUEST_VERSION, request, response);
		log.info("Resource : {}", url);
		log.info("Response sent : {}", response);
		return response;
	}

	private GetByOidAfterTaggingResponse getResponse(GetByOidAfterTaggingRequest request, String requestSourceService){
		ServiceResponseHeader header = headerUtil.getResponseHeader(request.getHeader(), REQUEST_VERSION, requestSourceService);
		GetByOidAfterTaggingResponseBody body = GetByOidAfterTaggingResponseBody.builder().build();
		return GetByOidAfterTaggingResponse.builder().header(header).meta(request.getMeta()).body(body).build();
	}

	public GetByOidAfterTaggingResponse fallbackGetAllOnPost(GetByOidAfterTaggingRequest request) {
		String url = Api.COMACQ_ASSETINFO_V1_RESOURCE.concat(Api.COMACQ_ASSETINFO_V1_GET_BY_OID_AFTER_TAGGING_PATH);
		GetByOidAfterTaggingResponse response = getResponse(request, url);
		return response;
	}

}
