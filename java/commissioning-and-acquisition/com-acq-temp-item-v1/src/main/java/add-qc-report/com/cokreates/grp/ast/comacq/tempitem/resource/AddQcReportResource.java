package com.cokreates.grp.ast.comacq.tempitem.resource;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cokreates.grp.ast.comacq.tempitem.request.AddQcReportRequest;
import com.cokreates.grp.ast.comacq.tempitem.request.AddQcReportRequestBody;
import com.cokreates.grp.ast.comacq.tempitem.response.AddQcReportResponse;
import com.cokreates.grp.ast.comacq.tempitem.response.AddQcReportResponseBody;
import com.cokreates.grp.ast.comacq.tempitem.service.AddQcReportService;
import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.request.ServiceRequestHeader;
import com.cokreates.grp.response.ServiceResponseHeader;
import com.cokreates.grp.util.HeaderUtil;
import com.cokreates.grp.util.constant.Api;
import com.cokreates.grp.util.constant.Constant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController("comAcqTempItemV1AddQcReportResource")
@RequestMapping(Api.COMACQ_TEMPITEM_V1_RESOURCE)
public class AddQcReportResource {
	
	private final String REQUEST_VERSION = "1.0";

	@Autowired
	@Qualifier("headerUtil")
	private HeaderUtil headerUtil;
	
	@Autowired
	@Qualifier("comAcqTempItemV1AddQcReportService")
	private AddQcReportService service;

	// @HystrixCommand(fallbackMethod = "fallbackGetAllOnPost")
	@PostMapping(path = Api.COMACQ_TEMPITEM_V1_ADD_QC_REPORT_PATH, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE})
	public @ResponseBody AddQcReportResponse onPost(@RequestParam("file") MultipartFile file, @RequestParam("oid") String oid, @RequestHeader(value="Authorization") String token) throws AppException {
		
		ServiceRequestHeader header = ServiceRequestHeader.builder()
			.requestId(UUID.randomUUID().toString())
			.requestTime(new DateTime())
			.build();
		
		Map<String, Object> meta = new HashMap<String, Object>();
		AddQcReportRequestBody body = AddQcReportRequestBody.builder().file(file).oid(oid).build();
		AddQcReportRequest request = AddQcReportRequest.builder().header(header).meta(meta).body(body).build();
		
		String url = Api.COMACQ_TEMPITEM_V1_RESOURCE.concat(Api.COMACQ_TEMPITEM_V1_ADD_QC_REPORT_PATH);
		log.info("Resource : {}", url);
		log.info("Request received : {}", request);
		AuthUser user = Constant.getAuthUser(request.getHeader(), token);
		AddQcReportResponse response = getResponse(request, url);
		response = service.perform(user, url, REQUEST_VERSION, request, response);
		log.info("Resource : {}", url);
		log.info("Response sent : {}", response);
		return response;
	}

	private AddQcReportResponse getResponse(AddQcReportRequest request, String requestSourceService){
		ServiceResponseHeader header = headerUtil.getResponseHeader(request.getHeader(), REQUEST_VERSION, requestSourceService);
		AddQcReportResponseBody body = AddQcReportResponseBody.builder().build();
		return AddQcReportResponse.builder().header(header).meta(request.getMeta()).body(body).build();
	}

	public AddQcReportResponse fallbackGetAllOnPost(AddQcReportRequest request) {
		String url = Api.COMACQ_TEMPITEM_V1_RESOURCE.concat(Api.COMACQ_TEMPITEM_V1_ADD_QC_REPORT_PATH);
		AddQcReportResponse response = getResponse(request, url);
		return response;
	}

}
