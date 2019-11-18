package com.cokreates.grp.ast.comacq.tempitem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cokreates.grp.ast.comacq.tempitem.dao.GetListDaoStoreManager;
import com.cokreates.grp.ast.comacq.tempitem.model.TempItemStoreManager;
import com.cokreates.grp.ast.comacq.tempitem.request.GetListRequestStoreManager;
import com.cokreates.grp.ast.comacq.tempitem.response.GetListResponseStoreManager;
import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.service.BaseService;
import com.cokreates.grp.util.constant.Code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("comAcqTempItemV1GetServiceStoreManager")
public class GetServiceStoreManager extends BaseService<GetListRequestStoreManager, GetListResponseStoreManager> {

	@Autowired
	@Qualifier("comAcqTempItemV1GetListDaoStoreManager")
	private GetListDaoStoreManager getListDao;
	
	@Override
	public GetListResponseStoreManager perform(AuthUser user, String url, String version, GetListRequestStoreManager request, GetListResponseStoreManager response) throws AppException {
		try {
			List<TempItemStoreManager> tempItem = getListDao.findAll(user, request); 
			log.info("Temporary Item count - {}", tempItem.size());
			response.getBody().setData(tempItem);
			response.getBody().setCount(getListDao.getDataCount(user, request));
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
	
}
