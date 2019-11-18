package com.cokreates.grp.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cokreates.grp.model.organogram.OfficeUnitPostRequest;
import com.cokreates.grp.model.organogram.OfficeUnitPostResponse;
import com.cokreates.grp.model.organogram.OfficeUnitRequest;
import com.cokreates.grp.model.organogram.OfficeUnitResponse;
import com.cokreates.grp.util.constant.Api;

@FeignClient(name="${cmn.service.organogram.name}")
public interface CmnServiceOrganogramClient {
	
	@RequestMapping(method=RequestMethod.POST, value=Api.OFFICE_UNIT_GET_BY_OID, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE}, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	public OfficeUnitResponse getOfficeUnit(@RequestBody OfficeUnitRequest request);
	
	@RequestMapping(method=RequestMethod.POST, value=Api.OFFICE_UNIT_POST_GET_BY_OID, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE}, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	public OfficeUnitPostResponse getOfficeUnitPost(@RequestBody OfficeUnitPostRequest request);

}
