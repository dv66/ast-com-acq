package com.cokreates.grp.ast.comacq.tempitem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cokreates.grp.ast.comacq.tempitem.dao.GetQcReportDao;
import com.cokreates.grp.ast.comacq.tempitem.request.GetQcReportRequest;
//import com.cokreates.grp.ast.comacq.tempitem.response.GetByOid;
import com.cokreates.grp.ast.comacq.tempitem.response.GetQcReportResponse;
import com.cokreates.grp.ast.comacq.tempitem.response.GetQcReportResponseBody;
import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.service.BaseService;
import com.cokreates.grp.util.constant.Code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("comacqTempitemV1GetQcReportService")
public class GetQcReportService extends BaseService<GetQcReportRequest, GetQcReportResponse> {

	@Autowired
	@Qualifier("comacqTempitemV1GetQcReportDao")
	private GetQcReportDao getListDao;
	
	@Override
	public GetQcReportResponse perform(AuthUser user, String url, String version, GetQcReportRequest request, GetQcReportResponse response) throws AppException {
		try {
			GetQcReportResponseBody temporaryItem = getListDao.getTemporaryItemByOid(user, request.getBody().getOid());
			log.info("Successfully load data");
			response.setBody(temporaryItem);
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
	
}
