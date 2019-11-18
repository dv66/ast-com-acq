package com.cokreates.grp.ast.comacq.tempitem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cokreates.grp.ast.comacq.tempitem.dao.UpdateDao;
import com.cokreates.grp.ast.comacq.tempitem.request.UpdateRequest;
import com.cokreates.grp.ast.comacq.tempitem.response.GetByOidUpdateResponseBody;
import com.cokreates.grp.ast.comacq.tempitem.response.UpdateResponse;
import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.service.BaseService;
import com.cokreates.grp.util.constant.Code;
import com.cokreates.grp.util.constant.Constant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("comacqTempitemV1UpdateService")
public class UpdateService extends BaseService<UpdateRequest, UpdateResponse> {

	@Autowired
	@Qualifier("comacqTempitemV1UpdateDao")
	private UpdateDao updateDao;
	
	@Override
	public UpdateResponse perform(AuthUser user, String url, String version, UpdateRequest request, UpdateResponse response) throws AppException {
		try {
			GetByOidUpdateResponseBody status = updateDao.getTemporaryItemByOid(request.getBody().getData().getOid());
			if((status.getStatus()).equals(Constant.TI_STATUS_DRAFT)) {
				updateDao.updateTemporaryItemDetails(user, request.getBody().getData(), request.getBody().getData().getUpdateTempItemDetail());
				log.info("Successfully Temporary Item Draft Details updated");
				response.getBody().setData(true);
				response.getHeader().setResponseCode(Code.C_200.get());
				response.getHeader().setResponseMessage("Successfully Temporary Item Details updated");
			} else {
				log.info("Temporary Items are already added");
				response.getBody().setData(false);
				response.getHeader().setResponseCode(Code.C_500.get());
				response.getHeader().setResponseMessage("Temporary Items are already added");
			}
			
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
	
}
