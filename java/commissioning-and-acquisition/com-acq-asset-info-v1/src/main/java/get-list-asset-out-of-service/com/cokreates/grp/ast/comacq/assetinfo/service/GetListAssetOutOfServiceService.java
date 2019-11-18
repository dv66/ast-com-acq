package com.cokreates.grp.ast.comacq.assetinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cokreates.grp.ast.comacq.assetinfo.dao.GetListAssetOutOfServiceDao;
import com.cokreates.grp.ast.comacq.assetinfo.model.GetListAssetOutOfService;
import com.cokreates.grp.ast.comacq.assetinfo.request.GetListAssetOutOfServiceRequest;
import com.cokreates.grp.ast.comacq.assetinfo.response.GetListAssetOutOfServiceResponse;
import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.service.BaseService;
import com.cokreates.grp.util.constant.Code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("comacqAssetInfoV1GetListAssetOutOfServiceService")
public class GetListAssetOutOfServiceService extends BaseService<GetListAssetOutOfServiceRequest, GetListAssetOutOfServiceResponse> {

	@Autowired
	@Qualifier("comacqAssetInfoV1GetListAssetOutOfServiceDao")
	private GetListAssetOutOfServiceDao getListDao;
	
	@Override
	public GetListAssetOutOfServiceResponse perform(AuthUser user, String url, String version, GetListAssetOutOfServiceRequest request, GetListAssetOutOfServiceResponse response) throws AppException {
		try {
			List<GetListAssetOutOfService> assetInfoList = getListDao.findAll(user, request); 
			log.info("Out of service asset count - {}", assetInfoList.size());
			response.getBody().setData(assetInfoList);
			response.getBody().setCount(getListDao.getDataCount(user, request));
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
	
}
