package com.cokreates.grp.ast.comacq.tempitem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cokreates.grp.ast.comacq.tempitem.dao.DraftQcUpdateDao;
import com.cokreates.grp.ast.comacq.tempitem.request.DraftQcUpdateRequest;
import com.cokreates.grp.ast.comacq.tempitem.response.DraftQcUpdateResponse;
import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.service.BaseService;
import com.cokreates.grp.util.constant.Code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("comacqTempitemV1DraftQcUpdateService")
public class DraftQcUpdateService extends BaseService<DraftQcUpdateRequest, DraftQcUpdateResponse> {

	@Autowired
	@Qualifier("comacqTempitemV1DraftQcUpdateDao")
	private DraftQcUpdateDao updateDao;
	
	@Override
	public DraftQcUpdateResponse perform(AuthUser user, String url, String version, DraftQcUpdateRequest request, DraftQcUpdateResponse response) throws AppException {
		try {
			
			boolean checkStatus = updateDao.checkStatus(request.getBody().getData().getOid());
			boolean isAvailable = updateDao.isAvailable(request.getBody().getData().getTempItemDetails());
			
			if(!checkStatus) {
				response.getBody().setData(false);
				response.getHeader().setResponseMessage("QC is already done");
				response.getHeader().setResponseCode(Code.C_500.get());
				return response;
			}
			
			if(!isAvailable) {
				response.getBody().setData(false);
				response.getHeader().setResponseMessage("Received quantity is not equal qualified and disqualified quantity");
				response.getHeader().setResponseCode(Code.C_500.get());
				return response;
			}
			
			updateDao.updateTemporaryItemDetails(user, request.getBody(), request.getBody().getData().getTempItemDetails());
			log.info("Successfully Temporary Item Details(QC) updated");
			response.getBody().setData(true);
			response.getHeader().setResponseCode(Code.C_200.get());
			response.getHeader().setResponseMessage("Successfully Temporary Item Details(QC) updated");
			
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
	
}
