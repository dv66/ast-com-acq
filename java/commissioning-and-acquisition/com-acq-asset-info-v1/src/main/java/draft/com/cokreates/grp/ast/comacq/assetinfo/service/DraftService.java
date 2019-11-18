package com.cokreates.grp.ast.comacq.assetinfo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cokreates.grp.ast.comacq.assetinfo.dao.DraftDao;
import com.cokreates.grp.ast.comacq.assetinfo.request.DraftRequest;
import com.cokreates.grp.ast.comacq.assetinfo.response.DraftResponse;
import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.service.BaseService;
import com.cokreates.grp.util.constant.Code;
import com.cokreates.grp.util.constant.Constant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("comAcqAssetInfoV1DraftService")
public class DraftService extends BaseService<DraftRequest, DraftResponse> {

	@Autowired
	@Qualifier("comAcqAssetInfoV1DraftDao")
	private DraftDao draftDao;

	@Override
	public DraftResponse perform(AuthUser user, String url, String version, DraftRequest request, DraftResponse response) throws AppException {
		try {
			String status = draftDao.checkTemporaryItemStatus(user, request);
			if(!status.equals(Constant.TI_STATUS_QC_APPROVED)) {
				response.getBody().setData(false);
				response.getHeader().setResponseMessage("Status of Temporary Item is not QC Approved!!!");
				response.getHeader().setResponseCode(Code.C_500.get());
				return response;
	        }
			draftDao.draftAssetInfo(user, request);
			log.info("Successfully drafted asset information");
			response.getBody().setData(true);
			response.getHeader().setResponseCode(Code.C_200.get());
			response.getHeader().setResponseMessage("Successfully drafted");
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
	
}
