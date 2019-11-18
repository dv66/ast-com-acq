package com.cokreates.grp.ast.comacq.tempitem.resource;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cokreates.grp.ast.comacq.tempitem.request.GetQcReportRequest;
import com.cokreates.grp.ast.comacq.tempitem.response.GetQcReportResponse;
import com.cokreates.grp.ast.comacq.tempitem.response.GetQcReportResponseBody;
import com.cokreates.grp.ast.comacq.tempitem.service.GetQcReportService;
import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.response.ServiceResponseHeader;
import com.cokreates.grp.util.HeaderUtil;
import com.cokreates.grp.util.constant.Api;
import com.cokreates.grp.util.constant.Code;
import com.cokreates.grp.util.constant.Constant;

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

@Slf4j
@RestController("comacqTempitemV1GetQcReportResource")
@RequestMapping(Api.COMACQ_TEMPITEM_V1_RESOURCE)
public class GetQcReportResource {
	
	private final String REQUEST_VERSION = "1.0";

	@Autowired
	@Qualifier("headerUtil")
	private HeaderUtil headerUtil;
	
	@Autowired
	private Environment env;
	
	@Autowired
	@Qualifier("comacqTempitemV1GetQcReportService")
	private GetQcReportService service;

	// @HystrixCommand(fallbackMethod = "fallbackGetAllOnPost")
	@PostMapping(path = Api.COMACQ_TEMPITEM_V1_GET_QC_REPORT, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Object> onPost(HttpServletRequest req, @Valid @RequestBody GetQcReportRequest request, @RequestHeader(value="Authorization") String token) throws AppException {
		String url = Api.COMACQ_TEMPITEM_V1_RESOURCE.concat(Api.COMACQ_TEMPITEM_V1_GET_QC_REPORT);
		log.info("Resource : {}", url);
		log.info("Request received : {}", request);
		AuthUser user = Constant.getAuthUser(request.getHeader(), token);
		GetQcReportResponse response = getResponse(request, url);
		response = service.perform(user, url, REQUEST_VERSION, request, response);
		log.info("Resource : {}", url);
		log.info("Response sent : {}", response);
		
		if(StringUtils.isBlank(response.getBody().getFilePath())) {
			throw new AppException(request.getHeader(), Code.C_500.get(), "Report file not found");
		}
		
		String filePath = env.getProperty("file.upload-dir") + "/" + response.getBody().getFilePath(); 
        Path path = Paths.get(filePath);
		String contentType = null;
		Resource resource = null;
        try {
            resource = new UrlResource(path.toUri());
            contentType = req.getServletContext().getMimeType(filePath);
        } catch (Exception e) {
            log.error("An exception occurred while get report : ", e);
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
        }
        
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
 
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
            .contentType(MediaType.parseMediaType(contentType))
            .body(resource);
	}

	private GetQcReportResponse getResponse(GetQcReportRequest request, String requestSourceService){
		ServiceResponseHeader header = headerUtil.getResponseHeader(request.getHeader(), REQUEST_VERSION, requestSourceService);
		GetQcReportResponseBody body = GetQcReportResponseBody.builder().build();
		return GetQcReportResponse.builder().header(header).meta(request.getMeta()).body(body).build();
	}

	public GetQcReportResponse fallbackGetAllOnPost(GetQcReportRequest request) {
		String url = Api.COMACQ_TEMPITEM_V1_RESOURCE.concat(Api.COMACQ_TEMPITEM_V1_GET_QC_REPORT);
		GetQcReportResponse response = getResponse(request, url);
		return response;
	}

}
