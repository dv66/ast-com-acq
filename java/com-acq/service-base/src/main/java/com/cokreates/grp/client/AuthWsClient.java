package com.cokreates.grp.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cokreates.grp.model.AuthWsResonse;
import com.cokreates.grp.model.EmployeeResponse;
import com.cokreates.grp.util.constant.Api;

@FeignClient(name = "${auth.service.name}")
public interface AuthWsClient {

	@RequestMapping(method = RequestMethod.POST, value = Api.AUTH_WS_MASTER_AUTHENTICATION_V1_CHECK_TOKEN_PATH, consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public AuthWsResonse checkToken(@RequestBody String request);

	@RequestMapping(method = RequestMethod.POST, value = Api.AUTH_WS_MASTER_AUTHENTICATION_V1_GET_USERNAME_PATH, consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public EmployeeResponse getUsername(@RequestBody String request);
}