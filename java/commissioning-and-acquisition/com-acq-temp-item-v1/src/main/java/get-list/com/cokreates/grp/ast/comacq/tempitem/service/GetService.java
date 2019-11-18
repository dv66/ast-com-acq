package com.cokreates.grp.ast.comacq.tempitem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cokreates.grp.ast.comacq.tempitem.dao.GetListDao;
import com.cokreates.grp.ast.comacq.tempitem.model.TempItem;
import com.cokreates.grp.ast.comacq.tempitem.request.GetListRequest;
import com.cokreates.grp.ast.comacq.tempitem.response.GetListResponse;
import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.service.BaseService;
import com.cokreates.grp.util.constant.Code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("comAcqTempItemV1GetService")
public class GetService extends BaseService<GetListRequest, GetListResponse> {

	@Autowired
	@Qualifier("comAcqTempItemV1GetListDao")
	private GetListDao getListDao;
	
	@Override
	public GetListResponse perform(AuthUser user, String url, String version, GetListRequest request, GetListResponse response) throws AppException {
		try {
			List<TempItem> tempItem = getListDao.findAll(request); 
			log.info("Temporary Item count - {}", tempItem.size());
			response.getBody().setData(tempItem);
			response.getBody().setCount(getListDao.getDataCount(request));
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
	
}
