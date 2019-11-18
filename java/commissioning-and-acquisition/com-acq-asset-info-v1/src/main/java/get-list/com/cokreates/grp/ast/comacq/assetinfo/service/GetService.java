package com.cokreates.grp.ast.comacq.assetinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cokreates.grp.ast.comacq.assetinfo.dao.GetListDao;
import com.cokreates.grp.ast.comacq.assetinfo.model.GetListAssetInfo;
import com.cokreates.grp.ast.comacq.assetinfo.request.GetListRequest;
import com.cokreates.grp.ast.comacq.assetinfo.response.GetListResponse;
import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.service.BaseService;
import com.cokreates.grp.util.constant.Code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("comacqAssetInfoV1GetService")
public class GetService extends BaseService<GetListRequest, GetListResponse> {

	@Autowired
	@Qualifier("comacqAssetInfoV1GetListDao")
	private GetListDao getListDao;
	
	@Override
	public GetListResponse perform(AuthUser user, String url, String version, GetListRequest request, GetListResponse response) throws AppException {
		try {
			List<GetListAssetInfo> assetInfoList = getListDao.findAll(user, request); 
			log.info("Asset count - {}", assetInfoList.size());
			response.getBody().setData(assetInfoList);
			response.getBody().setCount(getListDao.getDataCount(user, request));
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
	
}
