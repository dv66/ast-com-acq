package com.cokreates.grp.ast.comacq.assetinfo.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cokreates.grp.ast.comacq.assetinfo.dao.GetLifeCycleByOidDao;
import com.cokreates.grp.ast.comacq.assetinfo.request.GetLifeCycleByOidRequest;
import com.cokreates.grp.ast.comacq.assetinfo.response.AssetAllocationDetails;
import com.cokreates.grp.ast.comacq.assetinfo.response.ExpertDecisionDetail;
import com.cokreates.grp.ast.comacq.assetinfo.response.GetLifeCycleByOidResponse;
import com.cokreates.grp.ast.comacq.assetinfo.response.GetLifeCycleByOidResponseBody;
import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.service.BaseService;
import com.cokreates.grp.util.constant.Code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("comacqAssetLifeCycleV1GetByOidService")
public class GetLifeCycleByOidService extends BaseService<GetLifeCycleByOidRequest, GetLifeCycleByOidResponse> {

	@Autowired
	@Qualifier("comacqAssetLifeCycleV1GetByOidDao")
	private GetLifeCycleByOidDao getLifeCycleByOidDao;
	
	@Override
	public GetLifeCycleByOidResponse perform(AuthUser user, String url, String version, GetLifeCycleByOidRequest request, GetLifeCycleByOidResponse response) throws AppException {
		try {
			GetLifeCycleByOidResponseBody assetInfo = getLifeCycleByOidDao.getAssetInfoByOid(request.getBody().getOid(), user);
			List<AssetAllocationDetails> listOfAssetAllocation = getLifeCycleByOidDao.getAssetAllocationDetails(request.getBody().getOid());
			List<ExpertDecisionDetail> listOfAssetMaintenance = getLifeCycleByOidDao.getMaintenanceAndDisposalDetails(request.getBody().getOid(), "Maintenance approved" , "Maintenance done" ,user);
			List<ExpertDecisionDetail> disposalInfo = getLifeCycleByOidDao.getMaintenanceAndDisposalDetails(request.getBody().getOid(), "Disposal approved" , "Disposal done" ,user);
			List<ExpertDecisionDetail> deniedOrWaitingDetail = getLifeCycleByOidDao.getMaintenanceAndDisposalDetails(request.getBody().getOid(), "Waiting" , "Denied" ,user);
			log.info("Successfully load data");
			assetInfo.setAllocationDetail(listOfAssetAllocation);
			assetInfo.setMaintenanceDetail(listOfAssetMaintenance);
			assetInfo.setDisposalDetail(disposalInfo);
			assetInfo.setDeniedOrWaitigDetail(deniedOrWaitingDetail);
			response.setBody(assetInfo);
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		} 
		return response;
	}
	
}
