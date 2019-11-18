package com.cokreates.grp.ast.comacq.assetinfo.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cokreates.grp.ast.comacq.assetinfo.dao.DraftGetListByTempOidDao;
import com.cokreates.grp.ast.comacq.assetinfo.model.DraftGetListAssetInfo;
import com.cokreates.grp.ast.comacq.assetinfo.request.DraftGetListByTempOidRequest;
import com.cokreates.grp.ast.comacq.assetinfo.response.DraftGetListByTempOidResponse;
import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.service.BaseService;
import com.cokreates.grp.util.constant.Code;
import com.cokreates.grp.util.constant.Constant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("comacqAssetInfoV1DraftGetListByTempOidService")
public class GetDraftListService extends BaseService<DraftGetListByTempOidRequest, DraftGetListByTempOidResponse> {

	@Autowired
	@Qualifier("comacqAssetInfoV1DraftGetListByTempOidDao")
	private DraftGetListByTempOidDao getListDao;
	
	@Override
	public DraftGetListByTempOidResponse perform(AuthUser user, String url, String version, DraftGetListByTempOidRequest request, DraftGetListByTempOidResponse response) throws AppException {
		try {
			String status = getListDao.checkTemporaryItemStatus(user, request.getBody().getOid());
			if(!status.equals(Constant.TI_STATUS_QC_APPROVED)) {
				response.getHeader().setResponseMessage("Status of Temporary item is not QC Approved");
				response.getHeader().setResponseCode(Code.C_500.get());
				response.getBody().setData(Collections.<DraftGetListAssetInfo> emptyList());
				return response;
			}
			List<DraftGetListAssetInfo> assetInfoList = getListDao.findAll(user, request); 
			log.info("Draft Asset count - {}", assetInfoList.size());
			response.getBody().setData(assetInfoList);
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
	
}
