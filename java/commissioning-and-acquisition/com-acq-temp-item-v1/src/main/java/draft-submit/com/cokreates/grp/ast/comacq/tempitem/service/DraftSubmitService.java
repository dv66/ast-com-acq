package com.cokreates.grp.ast.comacq.tempitem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cokreates.grp.ast.comacq.tempitem.dao.DraftSubmitDao;
import com.cokreates.grp.ast.comacq.tempitem.request.DraftSubmitRequest;
import com.cokreates.grp.ast.comacq.tempitem.response.DraftSubmitResponse;
import com.cokreates.grp.ast.comacq.tempitem.response.GetByOidDraftSubmitResponseBody;
import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.service.BaseService;
import com.cokreates.grp.util.constant.Code;
import com.cokreates.grp.util.constant.Constant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("comacqTempitemV1DraftSubmitService")
public class DraftSubmitService extends BaseService<DraftSubmitRequest, DraftSubmitResponse> {

	@Autowired
	@Qualifier("comacqTempitemV1DraftSubmitDao")
	private DraftSubmitDao draftSubmitDao;
	
	@Override
	public DraftSubmitResponse perform(AuthUser user, String url, String version, DraftSubmitRequest request, DraftSubmitResponse response) throws AppException {
		 
		try {
			
			boolean result = draftSubmitDao.checkReceivedQuantity(request.getBody().getData());
			
			GetByOidDraftSubmitResponseBody status = draftSubmitDao.getTemporaryItemByOid(request.getBody().getData().getOid());
		
			String uniqueCode = draftSubmitDao.getUniqueId();
			
			if(!result) {
				log.info("Received quantity is not greater than 0");
				response.getBody().setData(false);
				response.getHeader().setResponseCode(Code.C_500.get());
				response.getHeader().setResponseMessage("Received quantity is not greater than 0");
				return response;
			}
			
			
			if(!status.getStatus().equalsIgnoreCase(Constant.TI_STATUS_DRAFT)) {				
				log.info("Temporary item is already added");
				response.getBody().setData(false);
				response.getHeader().setResponseCode(Code.C_500.get());
				response.getHeader().setResponseMessage("Temporary item is already added");
				return response;
			}
			
			draftSubmitDao.draftSubmitTemporaryItemDetails(user, request.getBody().getData(), request.getBody().getData().getDraftSubmitTempItemDetail(), uniqueCode);
			log.info("Successfully Temporary Item Details submitted");
			response.getBody().setData(true);
			response.getHeader().setResponseCode(Code.C_200.get());
			response.getHeader().setResponseMessage("Successfully Temporary Item Details submitted");
			
			
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
	
}
