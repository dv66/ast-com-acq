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

import com.cokreates.grp.ast.comacq.directin.request.RejectRequest;
import com.cokreates.grp.ast.comacq.directin.response.RejectResponse;
import com.cokreates.grp.ast.comacq.directin.response.RejectResponseBody;
import com.cokreates.grp.ast.comacq.directin.service.RejectService;
import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.response.ServiceResponseHeader;
import com.cokreates.grp.util.HeaderUtil;
import com.cokreates.grp.util.constant.Api;
import com.cokreates.grp.util.constant.Constant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController("comAcqDirectInV1RejectResource")
@RequestMapping(Api.COMACQ_DIRECTIN_V1_RESOURCE)
public class RejectResource {
	
	private final String REQUEST_VERSION = "1.0";

	@Autowired
	@Qualifier("headerUtil")
	private HeaderUtil headerUtil;
	
	@Autowired
	@Qualifier("comAcqDirectInV1RejectService")
	private RejectService service;

	// @HystrixCommand(fallbackMethod = "fallbackGetAllOnPost")
	@PostMapping(path = Api.COMACQ_DIRECTIN_V1_REJECT_PATH, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody RejectResponse onPost(@Valid @RequestBody RejectRequest request, @RequestHeader(value="Authorization") String token) throws AppException {
		String url = Api.COMACQ_DIRECTIN_V1_RESOURCE.concat(Api.COMACQ_DIRECTIN_V1_REJECT_PATH);
		log.info("Resource : {}", url);
		log.info("Request received : {}", request);
		AuthUser user = Constant.getAuthUser(request.getHeader(), token);
		RejectResponse response = getResponse(request, url);
		response = service.perform(user, url, REQUEST_VERSION, request, response);
		log.info("Resource : {}", url);
		log.info("Response sent : {}", response);
		return response;
	}

	private RejectResponse getResponse(RejectRequest request, String requestSourceService){
		ServiceResponseHeader header = headerUtil.getResponseHeader(request.getHeader(), REQUEST_VERSION, requestSourceService);
		RejectResponseBody body = RejectResponseBody.builder().build();
		return RejectResponse.builder().header(header).meta(request.getMeta()).body(body).build();
	}

	public RejectResponse fallbackGetAllOnPost(RejectRequest request) {
		String url = Api.COMACQ_DIRECTIN_V1_RESOURCE.concat(Api.COMACQ_DIRECTIN_V1_REJECT_PATH);
		RejectResponse response = getResponse(request, url);
		return response;
	}

}
