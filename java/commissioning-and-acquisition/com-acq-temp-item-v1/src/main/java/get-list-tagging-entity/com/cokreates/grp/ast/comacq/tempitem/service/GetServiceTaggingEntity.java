package com.cokreates.grp.ast.comacq.tempitem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cokreates.grp.ast.comacq.tempitem.dao.GetListDaoTaggingEntity;
import com.cokreates.grp.ast.comacq.tempitem.model.TempItemTaggingEntity;
import com.cokreates.grp.ast.comacq.tempitem.request.GetListRequestTaggingEntity;
import com.cokreates.grp.ast.comacq.tempitem.response.GetListResponseTaggingEntity;
import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.service.BaseService;
import com.cokreates.grp.util.constant.Code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("comAcqTempItemV1GetServiceTaggingEntity")
public class GetServiceTaggingEntity extends BaseService<GetListRequestTaggingEntity, GetListResponseTaggingEntity> {

	@Autowired
	@Qualifier("comAcqTempItemV1GetListDaoTaggingEntity")
	private GetListDaoTaggingEntity getListDao;
	
	@Override
	public GetListResponseTaggingEntity perform(AuthUser user, String url, String version, GetListRequestTaggingEntity request, GetListResponseTaggingEntity response) throws AppException {
		try {
			List<TempItemTaggingEntity> tempItem = getListDao.findAll(request, user.getOfficeOid()); 
			log.info("Temporary Item count - {}", tempItem.size());
			response.getBody().setData(tempItem);
			response.getBody().setCount(getListDao.getDataCount(user.getOfficeOid(), request));
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
	
}
