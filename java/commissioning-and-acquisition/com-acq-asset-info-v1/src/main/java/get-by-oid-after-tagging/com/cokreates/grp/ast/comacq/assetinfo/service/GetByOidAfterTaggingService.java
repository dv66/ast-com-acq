package com.cokreates.grp.ast.comacq.assetinfo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cokreates.grp.ast.comacq.assetinfo.dao.GetByOidAfterTaggingDao;
import com.cokreates.grp.ast.comacq.assetinfo.request.GetByOidAfterTaggingRequest;
import com.cokreates.grp.ast.comacq.assetinfo.response.GetByOidAfterTaggingResponse;
import com.cokreates.grp.ast.comacq.assetinfo.response.GetByOidAfterTaggingResponseBody;
import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.service.BaseService;
import com.cokreates.grp.util.constant.Code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("comacqAssetInfoV1GetByOidAfterTaggingService")
public class GetByOidAfterTaggingService extends BaseService<GetByOidAfterTaggingRequest, GetByOidAfterTaggingResponse> {

	@Autowired
	@Qualifier("comacqAssetInfoV1GetByOidAfterTaggingDao")
	private GetByOidAfterTaggingDao getListAfterTaggingDao;
	
	@Override
	public GetByOidAfterTaggingResponse perform(AuthUser user, String url, String version, GetByOidAfterTaggingRequest request, GetByOidAfterTaggingResponse response) throws AppException {
		try {
			GetByOidAfterTaggingResponseBody assetInfo = getListAfterTaggingDao.getAssetInfoAfterTaggingByOid(user, request.getBody().getOid());

			log.info("Successfully loaded data of after tagging by oid");
			response.setBody(assetInfo);
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
	
}
