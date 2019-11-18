package com.cokreates.grp.ast.comacq.tempitem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cokreates.grp.ast.comacq.tempitem.dao.ReQcDao;
import com.cokreates.grp.ast.comacq.tempitem.request.ReQcRequest;
import com.cokreates.grp.ast.comacq.tempitem.response.ReQcResponse;
import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.service.BaseService;
import com.cokreates.grp.util.constant.Code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("comacqTempitemV1ReQcService")
public class ReQcService extends BaseService<ReQcRequest, ReQcResponse> {

	@Autowired
	@Qualifier("comacqTempitemV1ReQcDao")
	private ReQcDao reQcDao;
	
	@Override
	public ReQcResponse perform(AuthUser user, String url, String version, ReQcRequest request, ReQcResponse response) throws AppException {
		
		try {
			boolean status = reQcDao.getTemporaryItemStatus(user, request.getBody().getData().getOid());
			if(!status) {
				response.getBody().setData(false);
				response.getHeader().setResponseMessage("Status of Temporary item is not QC done");
				response.getHeader().setResponseCode(Code.C_500.get());
				return response;
			}
			
			reQcDao.updateTemporaryItem(user, request.getBody().getData());
			log.info("Temporary items are rejected");
			response.getBody().setData(true);
			response.getHeader().setResponseCode(Code.C_200.get());
			response.getHeader().setResponseMessage("Temporary items are rejected");
			
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
	
}
