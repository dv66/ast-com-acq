package com.cokreates.grp.ast.comacq.tempitem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cokreates.grp.ast.comacq.tempitem.dao.GetListDaoAssetManager;
import com.cokreates.grp.ast.comacq.tempitem.model.TempItemAssetManager;
import com.cokreates.grp.ast.comacq.tempitem.request.GetListRequestAssetManager;
import com.cokreates.grp.ast.comacq.tempitem.response.GetListResponseAssetManager;
import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.service.BaseService;
import com.cokreates.grp.util.constant.Code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("comAcqTempItemV1GetServiceAssetManager")
public class GetServiceAssetManager extends BaseService<GetListRequestAssetManager, GetListResponseAssetManager> {

	@Autowired
	@Qualifier("comAcqTempItemV1GetListDaoAssetManager")
	private GetListDaoAssetManager getListDao;
	
	@Override
	public GetListResponseAssetManager perform(AuthUser user, String url, String version, GetListRequestAssetManager request, GetListResponseAssetManager response) throws AppException {
		try {
			List<TempItemAssetManager> tempItem = getListDao.findAll(request, user.getOfficeOid()); 
			log.info("Temporary Item count - {}", tempItem.size());
			System.out.print(tempItem);
			response.getBody().setData(tempItem);
			response.getBody().setCount(getListDao.getDataCount(user.getOfficeOid(), request));
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
	
}
