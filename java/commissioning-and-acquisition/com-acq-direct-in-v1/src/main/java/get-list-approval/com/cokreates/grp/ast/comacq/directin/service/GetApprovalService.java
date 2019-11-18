package com.cokreates.grp.ast.comacq.directin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cokreates.grp.ast.comacq.directin.dao.GetListApprovalDao;
import com.cokreates.grp.ast.comacq.directin.model.DirectInApproval;
import com.cokreates.grp.ast.comacq.directin.request.GetListApprovalRequest;
import com.cokreates.grp.ast.comacq.directin.response.GetListApprovalResponse;
import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.service.BaseService;
import com.cokreates.grp.util.constant.Code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("comAcqDirectInV1GetListApprovalService")
public class GetApprovalService extends BaseService<GetListApprovalRequest, GetListApprovalResponse> {

	@Autowired
	@Qualifier("comAcqDirectInV1GetListApprovalDao")
	private GetListApprovalDao getListDao;
	
	@Override
	public GetListApprovalResponse perform(AuthUser user, String url, String version, GetListApprovalRequest request, GetListApprovalResponse response) throws AppException {
		try {
			List<DirectInApproval> directIn = getListDao.findAll(request, user); 
			log.info("Direct In count - {}", directIn.size());
			response.getBody().setData(directIn);
			response.getBody().setCount(getListDao.getDataCount(request, user));
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
	
}
