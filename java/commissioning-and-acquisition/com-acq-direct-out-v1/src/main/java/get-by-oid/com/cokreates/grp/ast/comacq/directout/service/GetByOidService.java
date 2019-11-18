package com.cokreates.grp.ast.comacq.directout.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cokreates.grp.ast.comacq.directout.dao.GetByOidDao;
import com.cokreates.grp.ast.comacq.directout.request.GetByOidRequest;
import com.cokreates.grp.ast.comacq.directout.response.GetByOid;
import com.cokreates.grp.ast.comacq.directout.response.GetByOidResponse;
import com.cokreates.grp.ast.comacq.directout.response.GetByOidResponseBody;
import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.service.BaseService;
import com.cokreates.grp.util.constant.Code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("comacqDirectOutV1GetByOidService")
public class GetByOidService extends BaseService<GetByOidRequest, GetByOidResponse> {

	@Autowired
	@Qualifier("comacqDirectOutV1GetByOidDao")
	private GetByOidDao getListDao;
	
	@Override
	public GetByOidResponse perform(AuthUser user, String url, String version, GetByOidRequest request, GetByOidResponse response) throws AppException {
		try {
			GetByOidResponseBody directOut = getListDao.getDirectOutByOid(user, request.getBody().getOid());
			List<GetByOid> directOutDetailList = getListDao.getDirectOutDetailByOid(request.getBody().getOid());
			directOut.setDetail(directOutDetailList);
			log.info("Successfully load data");
			response.setBody(directOut);
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
	
}
