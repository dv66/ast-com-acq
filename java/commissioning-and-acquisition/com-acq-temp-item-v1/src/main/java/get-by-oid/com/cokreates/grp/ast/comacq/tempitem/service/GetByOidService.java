package com.cokreates.grp.ast.comacq.tempitem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cokreates.grp.ast.comacq.tempitem.dao.GetByOidDao;
import com.cokreates.grp.ast.comacq.tempitem.request.GetByOidRequest;
import com.cokreates.grp.ast.comacq.tempitem.response.GetByOid;
import com.cokreates.grp.ast.comacq.tempitem.response.GetByOidResponse;
import com.cokreates.grp.ast.comacq.tempitem.response.GetByOidResponseBody;
import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.service.BaseService;
import com.cokreates.grp.util.constant.Code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("comacqTempitemV1GetByOidService")
public class GetByOidService extends BaseService<GetByOidRequest, GetByOidResponse> {

	@Autowired
	@Qualifier("comacqTempitemV1GetByOidDao")
	private GetByOidDao getListDao;
	
	@Override
	public GetByOidResponse perform(AuthUser user, String url, String version, GetByOidRequest request, GetByOidResponse response) throws AppException {
		try {
			GetByOidResponseBody temporaryItem = getListDao.getTemporaryItemByOid(user, request.getBody().getOid());
			List<GetByOid> temporaryItemDetailList = getListDao.getTemporaryItemDetailByOid(user, request.getBody().getOid());
			temporaryItem.setDetail(temporaryItemDetailList);
			log.info("Successfully load data");
			response.setBody(temporaryItem);
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
	
}
