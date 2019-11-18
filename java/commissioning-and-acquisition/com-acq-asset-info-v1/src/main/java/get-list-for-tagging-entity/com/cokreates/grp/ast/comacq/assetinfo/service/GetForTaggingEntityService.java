package com.cokreates.grp.ast.comacq.assetinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cokreates.grp.ast.comacq.assetinfo.dao.GetListForTaggingEntityDao;
import com.cokreates.grp.ast.comacq.assetinfo.model.GetListForTaggingEntityAssetInfo;
import com.cokreates.grp.ast.comacq.assetinfo.request.GetListForTaggingEntityRequest;
import com.cokreates.grp.ast.comacq.assetinfo.response.GetListForTaggingEntityResponse;
import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.service.BaseService;
import com.cokreates.grp.util.constant.Code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("comacqAssetInfoV1GetForTaggingEntityService")
public class GetForTaggingEntityService extends BaseService<GetListForTaggingEntityRequest, GetListForTaggingEntityResponse> {

	@Autowired
	@Qualifier("comacqAssetInfoV1GetListForTaggingEntityDao")
	private GetListForTaggingEntityDao getListDao;
	
	@Override
	public GetListForTaggingEntityResponse perform(AuthUser user, String url, String version, GetListForTaggingEntityRequest request, GetListForTaggingEntityResponse response) throws AppException {
		try {
			List<GetListForTaggingEntityAssetInfo> assetInfoList = getListDao.findAll(user, request); 
			log.info("List of tagging entity, Asset count - {}", assetInfoList.size());
			response.getBody().setData(assetInfoList);
			response.getBody().setCount(getListDao.getDataCount(user, request));
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
	
}
