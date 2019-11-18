package com.cokreates.grp.ast.comacq.assetinfo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cokreates.grp.ast.comacq.assetinfo.dao.GetByOidDao;
import com.cokreates.grp.ast.comacq.assetinfo.request.GetByOidRequest;
import com.cokreates.grp.ast.comacq.assetinfo.response.GetByOidResponse;
import com.cokreates.grp.ast.comacq.assetinfo.response.GetByOidResponseBody;
import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.service.BaseService;
import com.cokreates.grp.util.constant.Code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("comacqAssetInfoV1GetByOidService")
public class GetByOidService extends BaseService<GetByOidRequest, GetByOidResponse> {

	@Autowired
	@Qualifier("comacqAssetInfoV1GetByOidDao")
	private GetByOidDao getByOidDao;
	
	@Override
	public GetByOidResponse perform(AuthUser user, String url, String version, GetByOidRequest request, GetByOidResponse response) throws AppException {
		try {
			GetByOidResponseBody assetInfo = getByOidDao.getAssetInfoByOid(request.getBody().getOid(), user);
			log.info("Successfully load data");
			response.setBody(assetInfo);
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		} 
		return response;
	}
	
}
