package com.cokreates.grp.ast.comacq.directout.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cokreates.grp.ast.comacq.directout.dao.SaveDao;
import com.cokreates.grp.ast.comacq.directout.request.SaveRequest;
import com.cokreates.grp.ast.comacq.directout.response.SaveResponse;
import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.service.BaseService;
import com.cokreates.grp.util.constant.Code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("comAcqDirectOutV1SaveService")
public class SaveService extends BaseService<SaveRequest, SaveResponse> {

	@Autowired
	@Qualifier("comAcqDirectOutV1SaveDao")
	private SaveDao saveDao;

	@Override
	@Transactional
    public SaveResponse perform(AuthUser user, String url, String version, SaveRequest request, SaveResponse response) throws AppException {
		try {
		
			String uniqueCode = saveDao.getUniqueId();		
			saveDao.saveDirectOut(user, request, uniqueCode);
			log.info("Successfully saved");
			response.getBody().setData(true);
			response.getHeader().setResponseCode(Code.C_200.get());
			response.getHeader().setResponseMessage("Successfully saved");
			
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
	
}
