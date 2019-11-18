package com.cokreates.grp.ast.comacq.directout.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cokreates.grp.ast.comacq.directout.dao.RejectDao;
import com.cokreates.grp.ast.comacq.directout.model.RejectDirectOutDetail;
import com.cokreates.grp.ast.comacq.directout.request.RejectRequest;
import com.cokreates.grp.ast.comacq.directout.response.RejectResponse;
import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.service.BaseService;
import com.cokreates.grp.util.constant.Code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("comAcqDirectOutV1RejectService")
public class RejectService extends BaseService<RejectRequest, RejectResponse> {

	@Autowired
	@Qualifier("comAcqDirectOutV1RejectDao")
	private RejectDao rejectDao;

	@Override
	@Transactional
    public RejectResponse perform(AuthUser user, String url, String version, RejectRequest request, RejectResponse response) throws AppException {
		try {
			
			List<RejectDirectOutDetail> list = rejectDao.getDirectOutDetailByOid(request.getBody().getData().getOid());
			rejectDao.updateDirectOut(user, request, list);
			log.info("Successfully rejected");
			response.getBody().setData(true);
			response.getHeader().setResponseCode(Code.C_200.get());
			response.getHeader().setResponseMessage("Successfully rejected");
			
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
	
}
