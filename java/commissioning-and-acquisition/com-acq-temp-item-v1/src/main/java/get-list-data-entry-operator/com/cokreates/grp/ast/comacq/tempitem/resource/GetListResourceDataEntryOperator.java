package com.cokreates.grp.ast.comacq.tempitem.resource;

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

import com.cokreates.grp.ast.comacq.tempitem.request.GetListRequestDataEntryOperator;
import com.cokreates.grp.ast.comacq.tempitem.response.GetListResponseBodyDataEntryOperator;
import com.cokreates.grp.ast.comacq.tempitem.response.GetListResponseDataEntryOperator;
import com.cokreates.grp.ast.comacq.tempitem.service.GetServiceDataEntryOperator;
import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.response.ServiceResponseHeader;
import com.cokreates.grp.util.HeaderUtil;
import com.cokreates.grp.util.constant.Api;
import com.cokreates.grp.util.constant.Constant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController("comAcqTempItemV1GetListResourceDataEntryOperator")
@RequestMapping(Api.COMACQ_TEMPITEM_V1_RESOURCE)
public class GetListResourceDataEntryOperator {
	
	private final String REQUEST_VERSION = "1.0";

	@Autowired
	@Qualifier("headerUtil")
	private HeaderUtil headerUtil;
	
	@Autowired
	@Qualifier("comAcqTempItemV1GetServiceDataEntryOperator")
	private GetServiceDataEntryOperator service;

	// @HystrixCommand(fallbackMethod = "fallbackGetAllOnPost")
	@PostMapping(path = Api.COMACQ_TEMPITEM_V1_GET_LIST_DATA_ENTRY_OPERATOR_PATH, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody GetListResponseDataEntryOperator onPost(@Valid @RequestBody GetListRequestDataEntryOperator request, @RequestHeader(value="Authorization") String token) throws AppException {
		String url = Api.COMACQ_TEMPITEM_V1_RESOURCE.concat(Api.COMACQ_TEMPITEM_V1_GET_LIST_DATA_ENTRY_OPERATOR_PATH);
		log.info("Resource : {}", url);
		log.info("Request received : {}", request);
		AuthUser user = Constant.getAuthUser(request.getHeader(), token);
		GetListResponseDataEntryOperator response = getResponse(request, url);
		response = service.perform(user, url, REQUEST_VERSION, request, response);
		log.info("Resource : {}", url);
		log.info("Response sent : {}", response);
		return response;
	}

	private GetListResponseDataEntryOperator getResponse(GetListRequestDataEntryOperator request, String requestSourceService){
		ServiceResponseHeader header = headerUtil.getResponseHeader(request.getHeader(), REQUEST_VERSION, requestSourceService);
		GetListResponseBodyDataEntryOperator body = GetListResponseBodyDataEntryOperator.builder().build();
		return GetListResponseDataEntryOperator.builder().header(header).meta(request.getMeta()).body(body).build();
	}

	public GetListResponseDataEntryOperator fallbackGetAllOnPost(GetListRequestDataEntryOperator request) {
		String url = Api.COMACQ_TEMPITEM_V1_RESOURCE.concat(Api.COMACQ_TEMPITEM_V1_GET_LIST_DATA_ENTRY_OPERATOR_PATH);
		GetListResponseDataEntryOperator response = getResponse(request, url);
		return response;
	}

}