package com.cokreates.grp.ast.comacq.assetinfo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cokreates.grp.ast.comacq.assetinfo.dao.GetByOidBeforeTaggingDao;
import com.cokreates.grp.ast.comacq.assetinfo.request.GetByOidBeforeTaggingRequest;
import com.cokreates.grp.ast.comacq.assetinfo.response.GetByOidBeforeTaggingResponse;
import com.cokreates.grp.ast.comacq.assetinfo.response.GetByOidBeforeTaggingResponseBody;
import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.service.BaseService;
import com.cokreates.grp.util.constant.Code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("comacqAssetInfoV1GetByOidBeforeTaggingService")
public class GetByOidBeforeTaggingService extends BaseService<GetByOidBeforeTaggingRequest, GetByOidBeforeTaggingResponse> {

	@Autowired
	@Qualifier("comacqAssetInfoV1GetByOidBeforeTaggingDao")
	private GetByOidBeforeTaggingDao getListBeforeTaggingDao;
	
	@Override
	public GetByOidBeforeTaggingResponse perform(AuthUser user, String url, String version, GetByOidBeforeTaggingRequest request, GetByOidBeforeTaggingResponse response) throws AppException {
		try {
			GetByOidBeforeTaggingResponseBody assetInfo = getListBeforeTaggingDao.getAssetInfoBeforeTaggingByOid(user, request.getBody().getOid());

			log.info("Successfully loaded data of before tagging by oid");
			response.setBody(assetInfo);
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
	
}
