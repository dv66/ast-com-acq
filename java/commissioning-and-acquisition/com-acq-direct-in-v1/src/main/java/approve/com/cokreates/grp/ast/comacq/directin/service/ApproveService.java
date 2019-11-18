package com.cokreates.grp.ast.comacq.directin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cokreates.grp.ast.comacq.directin.dao.ApproveDao;
import com.cokreates.grp.ast.comacq.directin.model.ApproveDirectInDetail;
import com.cokreates.grp.ast.comacq.directin.model.GetDirectIn;
import com.cokreates.grp.ast.comacq.directin.request.ApproveRequest;
import com.cokreates.grp.ast.comacq.directin.response.ApproveResponse;
import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.service.BaseService;
import com.cokreates.grp.util.constant.Code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("comAcqDirectInV1ApproveService")
public class ApproveService extends BaseService<ApproveRequest, ApproveResponse> {

	@Autowired
	@Qualifier("comAcqDirectInV1ApproveDao")
	private ApproveDao approveDao;

	@Override
	@Transactional
    public ApproveResponse perform(AuthUser user, String url, String version, ApproveRequest request, ApproveResponse response) throws AppException {
		try {
			
			GetDirectIn directIn = approveDao.getDirectInByOid(request.getBody().getData().getOid());
			List<ApproveDirectInDetail> list = approveDao.getDirectInDetailByOid(request.getBody().getData().getOid());
			approveDao.updateDirectIn(user, request, list, directIn);
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
