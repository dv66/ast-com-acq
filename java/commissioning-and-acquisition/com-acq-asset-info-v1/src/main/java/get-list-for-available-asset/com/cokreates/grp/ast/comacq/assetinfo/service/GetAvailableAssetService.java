package com.cokreates.grp.ast.comacq.assetinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cokreates.grp.ast.comacq.assetinfo.dao.GetListAvailableAssetDao;
import com.cokreates.grp.ast.comacq.assetinfo.model.GetListAvailableAssetInfo;
import com.cokreates.grp.ast.comacq.assetinfo.request.GetListAvailableAssetRequest;
import com.cokreates.grp.ast.comacq.assetinfo.response.GetListAvailableAssetResponse;
import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.service.BaseService;
import com.cokreates.grp.util.constant.Code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("comacqAssetInfoV1GetAvailableAssetService")
public class GetAvailableAssetService extends BaseService<GetListAvailableAssetRequest, GetListAvailableAssetResponse> {

	@Autowired
	@Qualifier("comacqAssetInfoV1GetListAvailableAssetDao")
	private GetListAvailableAssetDao getListDao;
	
	@Override
	public GetListAvailableAssetResponse perform(AuthUser user, String url, String version, GetListAvailableAssetRequest request, GetListAvailableAssetResponse response) throws AppException {
		try {
			List<GetListAvailableAssetInfo> assetInfoList = getListDao.findAll(user, request); 
			log.info("Avaible Asset count - {}", assetInfoList.size());
			response.getBody().setData(assetInfoList);
			response.getBody().setCount(getListDao.getDataCount(user, request));
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
	
}
