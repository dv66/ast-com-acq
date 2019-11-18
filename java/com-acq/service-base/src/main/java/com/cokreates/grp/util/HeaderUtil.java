package com.cokreates.grp.util;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.cokreates.grp.request.ServiceRequestHeader;
import com.cokreates.grp.response.ServiceResponseHeader;
import com.cokreates.grp.util.constant.Code;
import com.google.common.base.Strings;

import lombok.Synchronized;

@Component("headerUtil")
public class HeaderUtil {
	
	@Synchronized
	public ServiceResponseHeader getResponseHeader(ServiceRequestHeader requestHeader, String version,String requestSourceService) {
		ServiceResponseHeader header = null;
		if(requestHeader == null) {
			header = ServiceResponseHeader.builder()
					.requestId(UUID.randomUUID().toString() )
					.requestSourceService(requestSourceService)
					.responseVersion(version)
					.responseCode(Code.C_200.get())
					.responseMessage("Successfully perform")
					.hopCount(1)
					.traceId(UUID.randomUUID().toString())
					.build();
		} else {
			header = ServiceResponseHeader.builder()
					.requestId(requestHeader.getRequestId())
					.requestSourceService(requestSourceService)
					.responseVersion(version)
					.responseCode(Code.C_200.get())
					.responseMessage("Successfully perform")
					.hopCount(requestHeader.getHopCount() == null ? 1 : requestHeader.getHopCount() + 1)
					.traceId(Strings.isNullOrEmpty(requestHeader.getTraceId()) ? UUID.randomUUID().toString() : requestHeader.getTraceId())
					.build();
		}
		return header;
	}

}
