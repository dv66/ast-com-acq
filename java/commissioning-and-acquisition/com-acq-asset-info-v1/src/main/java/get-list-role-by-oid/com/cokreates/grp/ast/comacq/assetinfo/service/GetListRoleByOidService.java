package com.cokreates.grp.ast.comacq.assetinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cokreates.grp.ast.comacq.assetinfo.dao.GetListRoleByOidDao;
import com.cokreates.grp.ast.comacq.assetinfo.model.GetListRoleByOid;
import com.cokreates.grp.ast.comacq.assetinfo.request.GetListRoleByOidRequest;
import com.cokreates.grp.ast.comacq.assetinfo.response.GetListRoleByOidResponse;
import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.service.BaseService;
import com.cokreates.grp.util.constant.Code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("comAcqAssetInfoV1GetListRoleByOidService")
public class GetListRoleByOidService extends BaseService<GetListRoleByOidRequest, GetListRoleByOidResponse> {

	@Autowired
	@Qualifier("comAcqAssetInfoV1GetListRoleByOidDao")
	private GetListRoleByOidDao getListDao;
	
	@Override
	public GetListRoleByOidResponse perform(AuthUser user, String url, String version, GetListRoleByOidRequest request, GetListRoleByOidResponse response) throws AppException {
		try {
			List<GetListRoleByOid> assetReq = getListDao.findAllDetails(request, user);
			//List<ExpertDecisionByOid> assetReqDetails = getListDao.findAllDetails(request, user);
			log.info("Asset Requisition count - {}", assetReq);
			//log.info("Asset Requisition count - {}", assetReqDetails.size());
//			response.setDetails(assetReq);
			response.getBody().setDetails(assetReq);
//			response.getBody().setDataCount(getListDao.getDataCount(request, user));
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
	
}
