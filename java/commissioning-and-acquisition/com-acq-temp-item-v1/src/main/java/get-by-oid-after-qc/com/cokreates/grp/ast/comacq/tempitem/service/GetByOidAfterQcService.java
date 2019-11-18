package com.cokreates.grp.ast.comacq.tempitem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cokreates.grp.ast.comacq.tempitem.dao.GetByOidAfterQcDao;
import com.cokreates.grp.ast.comacq.tempitem.request.GetByOidAfterQcRequest;
import com.cokreates.grp.ast.comacq.tempitem.response.GetByOidAfterQc;
import com.cokreates.grp.ast.comacq.tempitem.response.GetByOidAfterQcResponse;
import com.cokreates.grp.ast.comacq.tempitem.response.GetByOidAfterQcResponseBody;
import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.service.BaseService;
import com.cokreates.grp.util.constant.Code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("comacqTempitemV1GetByOidAfterQcService")
public class GetByOidAfterQcService extends BaseService<GetByOidAfterQcRequest, GetByOidAfterQcResponse> {

	@Autowired
	@Qualifier("comacqTempitemV1GetByOidAfterQcDao")
	private GetByOidAfterQcDao getListDao;
	
	@Override
	public GetByOidAfterQcResponse perform(AuthUser user, String url, String version, GetByOidAfterQcRequest request, GetByOidAfterQcResponse response) throws AppException {
		try {
			GetByOidAfterQcResponseBody temporaryItem = getListDao.getTemporaryItemByOid(user, request.getBody().getOid());
			List<GetByOidAfterQc> temporaryItemDetailList = getListDao.getTemporaryItemDetailByOid(user, request.getBody().getOid());
			temporaryItem.setDetail(temporaryItemDetailList);
			log.info("Successfully load data");
			response.setBody(temporaryItem);
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
	
}
