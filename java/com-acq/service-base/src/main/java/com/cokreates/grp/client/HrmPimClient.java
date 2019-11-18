package com.cokreates.grp.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cokreates.grp.model.hrm.EmployeeInfoRequest;
import com.cokreates.grp.model.hrm.EmployeeInfoResponse;
import com.cokreates.grp.util.constant.Api;

@FeignClient(name = "${hrm.service.pim.name}")
public interface HrmPimClient {

	@RequestMapping(method = RequestMethod.GET, value = Api.EMPLOYEE_GET_BY_OID, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
		public EmployeeInfoResponse getEmployee(@RequestBody EmployeeInfoRequest request);
}
