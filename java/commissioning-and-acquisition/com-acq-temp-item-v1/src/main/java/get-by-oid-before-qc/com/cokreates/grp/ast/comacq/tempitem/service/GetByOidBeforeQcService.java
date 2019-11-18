package com.cokreates.grp.ast.comacq.tempitem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cokreates.grp.ast.comacq.tempitem.dao.GetByOidBeforeQcDao;
import com.cokreates.grp.ast.comacq.tempitem.request.GetByOidBeforeQcRequest;
import com.cokreates.grp.ast.comacq.tempitem.response.GetByOidBeforeQc;
import com.cokreates.grp.ast.comacq.tempitem.response.GetByOidBeforeQcResponse;
import com.cokreates.grp.ast.comacq.tempitem.response.GetByOidBeforeQcResponseBody;
import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.service.BaseService;
import com.cokreates.grp.util.constant.Code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("comacqTempitemV1GetByOidBeforeQcService")
public class GetByOidBeforeQcService extends BaseService<GetByOidBeforeQcRequest, GetByOidBeforeQcResponse> {

	@Autowired
	@Qualifier("comacqTempitemV1GetByOidBeforeQcDao")
	private GetByOidBeforeQcDao getListDao;
	
	@Override
	public GetByOidBeforeQcResponse perform(AuthUser user, String url, String version, GetByOidBeforeQcRequest request, GetByOidBeforeQcResponse response) throws AppException {
		try {
			GetByOidBeforeQcResponseBody temporaryItem = getListDao.getTemporaryItemByOid(user,request.getBody().getOid());
			List<GetByOidBeforeQc> temporaryItemDetailList = getListDao.getTemporaryItemDetailByOid(user,request.getBody().getOid());
			temporaryItem.setDetail(temporaryItemDetailList);
			log.info(temporaryItem.getChalanNo());
			log.info("Successfully load data");
			response.setBody(temporaryItem);
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
	
}
