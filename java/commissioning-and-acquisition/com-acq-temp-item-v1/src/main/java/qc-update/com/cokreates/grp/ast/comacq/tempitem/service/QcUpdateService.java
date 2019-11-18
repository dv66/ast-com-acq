package com.cokreates.grp.ast.comacq.tempitem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cokreates.grp.ast.comacq.tempitem.dao.QcUpdateDao;
import com.cokreates.grp.ast.comacq.tempitem.request.QcUpdateRequest;
import com.cokreates.grp.ast.comacq.tempitem.response.QcUpdateResponse;
import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.service.BaseService;
import com.cokreates.grp.util.constant.Code;
import com.cokreates.grp.util.constant.Constant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("comacqTempitemV1QcUpdateService")
public class QcUpdateService extends BaseService<QcUpdateRequest, QcUpdateResponse> {

	@Autowired
	@Qualifier("comacqTempitemV1QcUpdateDao")
	private QcUpdateDao updateDao;
	
	
	@Override
	public QcUpdateResponse perform(AuthUser user, String url, String version, QcUpdateRequest request, QcUpdateResponse response) throws AppException {
		
		try {
			String status = updateDao.checkTemporaryItemStatus(user, request.getBody().getData().getOid());
			if(!status.equals(Constant.TI_STATUS_READY_FOR_QC) && !status.equals(Constant.TI_STATUS_QC_ONGOING ) && !status.equals(Constant.TI_STATUS_RE_QC )) {
				response.getBody().setData(false);
				response.getHeader().setResponseMessage("Status of Temporary item is not Ready for QC or QC ongoing");
				response.getHeader().setResponseCode(Code.C_500.get());
				return response;
			}

			boolean isAvailable = updateDao.isAvailable(request.getBody().getData().getTempItemDetails());
			if(!isAvailable) {
				response.getBody().setData(false);
				response.getHeader().setResponseMessage("Received quantity is not equal qualified and disqualified quantity");
				response.getHeader().setResponseCode(Code.C_500.get());
				return response;
			}
			
			updateDao.updateTemporaryItemDetails(user, request.getBody(), request.getBody().getData().getTempItemDetails(), Constant.TI_STATUS_QC_DONE);
			log.info("Successfully Temporary Item Details updated for QC");
			response.getBody().setData(true);
			response.getHeader().setResponseCode(Code.C_200.get());
			response.getHeader().setResponseMessage("Successfully QC on Temporary Item Details updated");
			
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
	
}
