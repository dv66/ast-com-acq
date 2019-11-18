package com.cokreates.grp.ast.comacq.tempitem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cokreates.grp.ast.comacq.tempitem.dao.GetListAfterQcDao;
import com.cokreates.grp.ast.comacq.tempitem.model.TempItemAfterQc;
import com.cokreates.grp.ast.comacq.tempitem.request.GetListAfterQcRequest;
import com.cokreates.grp.ast.comacq.tempitem.response.GetListAfterQcResponse;
import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.service.BaseService;
import com.cokreates.grp.util.constant.Code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("comAcqTempItemV1GetListAfterQcService")
public class GetListAfterQcService extends BaseService<GetListAfterQcRequest, GetListAfterQcResponse> {

	@Autowired
	@Qualifier("comAcqTempItemV1GetListAfterQcDao")
	private GetListAfterQcDao getListDao;
	
	@Override
	public GetListAfterQcResponse perform(AuthUser user, String url, String version, GetListAfterQcRequest request, GetListAfterQcResponse response) throws AppException {
		try {
			List<TempItemAfterQc> tempItem = getListDao.findAll(request, user); 
			log.info("Temporary Item after QC, count - {}", tempItem.size());
			response.getBody().setData(tempItem);
			response.getBody().setCount(getListDao.getDataCount(user, request));
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
	
}
