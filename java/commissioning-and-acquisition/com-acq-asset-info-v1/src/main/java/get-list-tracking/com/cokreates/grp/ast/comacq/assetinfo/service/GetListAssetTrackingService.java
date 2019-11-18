package com.cokreates.grp.ast.comacq.assetinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cokreates.grp.ast.comacq.assetinfo.dao.GetListAssetTrackingDao;
import com.cokreates.grp.ast.comacq.assetinfo.model.AssetListTracking;
import com.cokreates.grp.ast.comacq.assetinfo.request.GetListAssetTrackingRequest;
import com.cokreates.grp.ast.comacq.assetinfo.response.GetListAssetTrackingResponse;
import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.service.BaseService;
import com.cokreates.grp.util.constant.Code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("comAcqTempItemV1GetListAssetTrackingService")
public class GetListAssetTrackingService extends BaseService<GetListAssetTrackingRequest, GetListAssetTrackingResponse> {

	@Autowired
	@Qualifier("comAcqTempItemV1GetListAssetTrackingDao")
	private GetListAssetTrackingDao getListDao;
	
	@Override
	public GetListAssetTrackingResponse perform(AuthUser user, String url, String version, GetListAssetTrackingRequest request, GetListAssetTrackingResponse response) throws AppException {
		try {
			List<AssetListTracking> tempItem = getListDao.findAll(user, request, user.getOfficeOid()); 
			log.info("Asset Information Item count - {}", tempItem.size());
			for( int i = 0; i < tempItem.size(); i++ )
			{
				if(tempItem.get(i).getReqTypeEn() != null && tempItem.get(i).getReqTypeEn().equals("Office"))
				{
					tempItem.get(i).setDesignationNameBn("");
					tempItem.get(i).setDesignationNameEn("");
					tempItem.get(i).setRecievedByNameBn("");
					tempItem.get(i).setRecievedByNameEn("");
				}
			}
			response.getBody().setData(tempItem);
			response.getBody().setCount(getListDao.getDataCount(user.getOfficeOid(), request));
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
	
}
