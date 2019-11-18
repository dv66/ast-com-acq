package com.cokreates.grp.ast.comacq.tempitem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cokreates.grp.ast.comacq.tempitem.dao.DraftDao;
import com.cokreates.grp.ast.comacq.tempitem.request.DraftRequest;
import com.cokreates.grp.ast.comacq.tempitem.response.DraftResponse;
import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.service.BaseService;
import com.cokreates.grp.util.constant.Code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("comAcqTempItemV1DraftService")
public class DraftService extends BaseService<DraftRequest, DraftResponse> {

	@Autowired
	@Qualifier("comAcqTempItemV1DraftDao")
	private DraftDao draftDao;

	@Override
    public DraftResponse perform(AuthUser user, String url, String version, DraftRequest request, DraftResponse response) throws AppException {
		try {
			draftDao.draftTemporaryItem(user, request);
			log.info("Successfully drafted");
			response.getBody().setData(true);
			response.getHeader().setResponseCode(Code.C_200.get());
			response.getHeader().setResponseMessage("Successfully drafted");
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
	
}
