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

import com.cokreates.grp.ast.comacq.assetinfo.request.DraftGetListByTempOidRequest;
import com.cokreates.grp.ast.comacq.assetinfo.response.DraftGetListByTempOidResponse;
import com.cokreates.grp.ast.comacq.assetinfo.response.DraftGetListByTempOidResponseBody;
import com.cokreates.grp.ast.comacq.assetinfo.service.GetDraftListService;
import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.response.ServiceResponseHeader;
import com.cokreates.grp.util.HeaderUtil;
import com.cokreates.grp.util.constant.Api;
import com.cokreates.grp.util.constant.Constant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController("comacqAssetInfoV1DraftGetListByTempOidResource")
@RequestMapping(Api.COMACQ_ASSETINFO_V1_RESOURCE)
public class DraftGetListByTempOidResource {
	
	private final String REQUEST_VERSION = "1.0";

	@Autowired
	@Qualifier("headerUtil")
	private HeaderUtil headerUtil;
	
	@Autowired
	@Qualifier("comacqAssetInfoV1DraftGetListByTempOidService")
	private GetDraftListService service;

	// @HystrixCommand(fallbackMethod = "fallbackGetAllOnPost")
	@PostMapping(path = Api.COMACQ_ASSETINFO_V1_DRAFT_GET_LIST_BY_TEMP_OID_PATH, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody DraftGetListByTempOidResponse onPost(@Valid @RequestBody DraftGetListByTempOidRequest request, @RequestHeader(value="Authorization") String token) throws AppException {
		String url = Api.COMACQ_ASSETINFO_V1_RESOURCE.concat(Api.COMACQ_ASSETINFO_V1_DRAFT_GET_LIST_BY_TEMP_OID_PATH);
		log.info("Resource : {}", url);
		log.info("Request received : {}", request);
		AuthUser user = Constant.getAuthUser(request.getHeader(), token);
		DraftGetListByTempOidResponse response = getResponse(request, url);
		response = service.perform(user, url, REQUEST_VERSION, request, response);
		log.info("Resource : {}", url);
		log.info("Response sent : {}", response);
		return response;
	}

	private DraftGetListByTempOidResponse getResponse(DraftGetListByTempOidRequest request, String requestSourceService){
		ServiceResponseHeader header = headerUtil.getResponseHeader(request.getHeader(), REQUEST_VERSION, requestSourceService);
		DraftGetListByTempOidResponseBody body = DraftGetListByTempOidResponseBody.builder().build();
		return DraftGetListByTempOidResponse.builder().header(header).meta(request.getMeta()).body(body).build();
	}

	public DraftGetListByTempOidResponse fallbackGetAllOnPost(DraftGetListByTempOidRequest request) {
		String url = Api.COMACQ_ASSETINFO_V1_RESOURCE.concat(Api.COMACQ_ASSETINFO_V1_DRAFT_GET_LIST_BY_TEMP_OID_PATH);
		DraftGetListByTempOidResponse response = getResponse(request, url);
		return response;
	}

}
