package com.cokreates.grp.ast.comacq.directout.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cokreates.grp.ast.comacq.directout.dao.ApproveDao;
import com.cokreates.grp.ast.comacq.directout.model.ApproveDirectOut;
import com.cokreates.grp.ast.comacq.directout.model.ApproveDirectOutDetail;
import com.cokreates.grp.ast.comacq.directout.request.ApproveRequest;
import com.cokreates.grp.ast.comacq.directout.response.ApproveResponse;
import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.service.BaseService;
import com.cokreates.grp.util.constant.Code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("comAcqDirectOutV1ApproveService")
public class ApproveService extends BaseService<ApproveRequest, ApproveResponse> {

	@Autowired
	@Qualifier("comAcqDirectOutV1ApproveDao")
	private ApproveDao approveDao;

	@Override
	@Transactional
    public ApproveResponse perform(AuthUser user, String url, String version, ApproveRequest request, ApproveResponse response) throws AppException {
		try {
			
			ApproveDirectOut directOut = approveDao.getDirectOutByOid(request.getBody().getData().getOid());			
			List<ApproveDirectOutDetail> list = approveDao.getDirectOutDetailByOid(request.getBody().getData().getOid());
			approveDao.updateDirectOut(user, request, list, directOut);
			log.info("Successfully approved");
			response.getBody().setData(true);
			response.getHeader().setResponseCode(Code.C_200.get());
			response.getHeader().setResponseMessage("Successfully approved");
			
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
	
}
