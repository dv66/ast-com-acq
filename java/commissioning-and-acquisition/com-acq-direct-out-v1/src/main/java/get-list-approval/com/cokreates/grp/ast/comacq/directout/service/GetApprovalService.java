package com.cokreates.grp.ast.comacq.directout.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cokreates.grp.ast.comacq.directout.dao.GetListApprovalDao;
import com.cokreates.grp.ast.comacq.directout.model.DirectOutApproval;
import com.cokreates.grp.ast.comacq.directout.request.GetListApprovalRequest;
import com.cokreates.grp.ast.comacq.directout.response.GetListApprovalResponse;
import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.service.BaseService;
import com.cokreates.grp.util.constant.Code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("comAcqDirectOutV1GetListApprovalService")
public class GetApprovalService extends BaseService<GetListApprovalRequest, GetListApprovalResponse> {

	@Autowired
	@Qualifier("comAcqDirectOutV1GetListApprovalDao")
	private GetListApprovalDao getListDao;
	
	@Override
	public GetListApprovalResponse perform(AuthUser user, String url, String version, GetListApprovalRequest request, GetListApprovalResponse response) throws AppException {
		try {
			List<DirectOutApproval> directIn = getListDao.findAll(request, user); 
			log.info("Direct Out count - {}", directIn.size());
			response.getBody().setData(directIn);
			response.getBody().setCount(getListDao.getDataCount(request, user));
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
	
}
