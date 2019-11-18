package com.cokreates.grp.ast.comacq.assetinfo.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cokreates.grp.ast.comacq.assetinfo.dao.GetByOidForTaggingDao;
import com.cokreates.grp.ast.comacq.assetinfo.request.GetByOidForTaggingRequest;
import com.cokreates.grp.ast.comacq.assetinfo.response.GetByOidForTagging;
import com.cokreates.grp.ast.comacq.assetinfo.response.GetByOidForTaggingResponse;
import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.service.BaseService;
import com.cokreates.grp.util.constant.Code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("comacqAssetInfoV1GetByOidForTaggingService")
public class GetByOidForTaggingService extends BaseService<GetByOidForTaggingRequest, GetByOidForTaggingResponse> {

	@Autowired
	@Qualifier("comacqAssetInfoV1GetByOidForTaggingDao")
	private GetByOidForTaggingDao getByOidForTaggingDao;
	
	@Override
	public GetByOidForTaggingResponse perform(AuthUser user, String url, String version, GetByOidForTaggingRequest request, GetByOidForTaggingResponse response) throws AppException {
		try {
			List<GetByOidForTagging> assetInfo = getByOidForTaggingDao.getAssetInfoByOidForTagging(user, request.getBody().getOid());
			int count=getByOidForTaggingDao.getDataCount(request.getBody().getOid());
			response.getBody().setData(assetInfo);
			response.getBody().setCount(count);
			log.info("Successfully loaded data for for tagging");
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
	
}
