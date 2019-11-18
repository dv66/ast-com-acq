package com.cokreates.grp.ast.comacq.tempitem.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cokreates.grp.ast.comacq.tempitem.dao.SaveDao;
import com.cokreates.grp.ast.comacq.tempitem.model.SaveTempItem;
import com.cokreates.grp.ast.comacq.tempitem.request.SaveRequest;
import com.cokreates.grp.ast.comacq.tempitem.response.SaveResponse;
import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.service.BaseService;
import com.cokreates.grp.util.constant.Code;
import com.cokreates.grp.util.constant.Constant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("comAcqTempItemV1SaveService")
public class SaveService extends BaseService<SaveRequest, SaveResponse> {

	@Autowired
	@Qualifier("comAcqTempItemV1SaveDao")
	private SaveDao saveDao;

	@Override
	@Transactional
    public SaveResponse perform(AuthUser user, String url, String version, SaveRequest request, SaveResponse response) throws AppException {
		try {
			
			boolean result = saveDao.checkReceivedQuantity(request);
			String uniqueCode = saveDao.getUniqueId();
			
			if(!checkEmpty(request.getBody().getData())) {
				log.warn("Contract No and date or Workorder No and date are not given!!!!");
				response.getBody().setData(false);
				response.getHeader().setResponseCode(Code.C_500.get());
				response.getHeader().setResponseMessage("Contract No and date or Workorder No and date are not given!!!!");
				return response;
			}
			
			if(!result) {
				log.warn("Received quantity is not greater than 0");
				response.getBody().setData(false);
				response.getHeader().setResponseCode(Code.C_500.get());
				response.getHeader().setResponseMessage("Received quantity is not greater than 0");
				return response;
			}
						
			saveDao.saveTemporaryItem(user, request, uniqueCode);
			log.info("Successfully saved");
			response.getBody().setData(true);
			response.getHeader().setResponseCode(Code.C_200.get());
			response.getHeader().setResponseMessage("Successfully saved");
			
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
	
	public boolean checkEmpty(SaveTempItem tempItem) {
		
		String pm = tempItem.getProcurementMethod();
		
		if(pm.equals(Constant.TI_PRC_METHOD_OTM)) {
			if(StringUtils.isNotBlank(tempItem.getContractNo()) && tempItem.getContractSigningDate() != null) {
				return true;
			}
		}
		
		if(pm.equals(Constant.TI_PRC_METHOD_DM)) {
			if(StringUtils.isNotBlank(tempItem.getWorkorderNo()) && tempItem.getWorkorderDate() != null) {
				return true;
			}
		}
		
		return false;		
	}
	
}
