package com.cokreates.grp.ast.comacq.directin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cokreates.grp.ast.comacq.directin.dao.GetListDao;
import com.cokreates.grp.ast.comacq.directin.model.DirectIn;
import com.cokreates.grp.ast.comacq.directin.request.GetListRequest;
import com.cokreates.grp.ast.comacq.directin.response.GetListResponse;
import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.service.BaseService;
import com.cokreates.grp.util.constant.Code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("comAcqDirectInV1GetListEndUserService")
public class GetService extends BaseService<GetListRequest, GetListResponse> {

	@Autowired
	@Qualifier("comAcqDirectInV1GetListEndUserDao")
	private GetListDao getListDao;
	
	@Override
	public GetListResponse perform(AuthUser user, String url, String version, GetListRequest request, GetListResponse response) throws AppException {
		try {
			List<DirectIn> directIn = getListDao.findAll(request, user); 
			log.info("Direct In count - {}", directIn.size());
			response.getBody().setData(directIn);
			response.getBody().setCount(getListDao.getDataCount(request, user));
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
	
}
