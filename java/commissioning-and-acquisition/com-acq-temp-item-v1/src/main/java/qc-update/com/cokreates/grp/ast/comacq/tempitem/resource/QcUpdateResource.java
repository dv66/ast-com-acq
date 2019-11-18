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

import com.cokreates.grp.ast.comacq.tempitem.request.QcUpdateRequest;
import com.cokreates.grp.ast.comacq.tempitem.response.QcUpdateResponse;
import com.cokreates.grp.ast.comacq.tempitem.response.QcUpdateResponseBody;
import com.cokreates.grp.ast.comacq.tempitem.service.QcUpdateService;
import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.response.ServiceResponseHeader;
import com.cokreates.grp.util.HeaderUtil;
import com.cokreates.grp.util.constant.Api;
import com.cokreates.grp.util.constant.Constant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController("comacqTempitemV1QcUpdateResource")
@RequestMapping(Api.COMACQ_TEMPITEM_V1_RESOURCE)
public class QcUpdateResource {
	
	private final String REQUEST_VERSION = "1.0";

	@Autowired
	@Qualifier("headerUtil")
	private HeaderUtil headerUtil;
	
	@Autowired
	@Qualifier("comacqTempitemV1QcUpdateService")
	private QcUpdateService service;

	// @HystrixCommand(fallbackMethod = "fallbackGetAllOnPost")
	@PostMapping(path = Api.COMACQ_TEMPITEM_V1_QCUPDATE_PATH, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody QcUpdateResponse onPost(@Valid @RequestBody QcUpdateRequest request, @RequestHeader(value="Authorization") String token) throws AppException {
		String url = Api.COMACQ_TEMPITEM_V1_RESOURCE.concat(Api.COMACQ_TEMPITEM_V1_QCUPDATE_PATH);
		log.info("Resource : {}", url);
		log.info("Request received : {}", request);
		AuthUser user = Constant.getAuthUser(request.getHeader(), token);
		QcUpdateResponse response = getResponse(request, url);
		response = service.perform(user, url, REQUEST_VERSION, request, response);
		log.info("Resource : {}", url);
		log.info("Response sent : {}", response);
		return response;
	}

	private QcUpdateResponse getResponse(QcUpdateRequest request, String requestSourceService){
		ServiceResponseHeader header = headerUtil.getResponseHeader(request.getHeader(), REQUEST_VERSION, requestSourceService);
		QcUpdateResponseBody body = QcUpdateResponseBody.builder().build();
		return QcUpdateResponse.builder().header(header).meta(request.getMeta()).body(body).build();
	}

	public QcUpdateResponse fallbackGetAllOnPost(QcUpdateRequest request) {
		String url = Api.COMACQ_TEMPITEM_V1_RESOURCE.concat(Api.COMACQ_TEMPITEM_V1_QCUPDATE_PATH);
		QcUpdateResponse response = getResponse(request, url);
		return response;
	}

}
