package com.cokreates.grp.ast.comacq.tempitem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cokreates.grp.ast.comacq.tempitem.dao.GetListDaoDataEntryOperator;
import com.cokreates.grp.ast.comacq.tempitem.model.TempItemDataEntryOperator;
import com.cokreates.grp.ast.comacq.tempitem.request.GetListRequestDataEntryOperator;
import com.cokreates.grp.ast.comacq.tempitem.response.GetListResponseDataEntryOperator;
import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.service.BaseService;
import com.cokreates.grp.util.constant.Code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("comAcqTempItemV1GetServiceDataEntryOperator")
public class GetServiceDataEntryOperator extends BaseService<GetListRequestDataEntryOperator, GetListResponseDataEntryOperator> {

	@Autowired
	@Qualifier("comAcqTempItemV1GetListDaoDataEntryOperator")
	private GetListDaoDataEntryOperator getListDao;
	
	@Override
	public GetListResponseDataEntryOperator perform(AuthUser user, String url, String version, GetListRequestDataEntryOperator request, GetListResponseDataEntryOperator response) throws AppException {
		try {
			List<TempItemDataEntryOperator> tempItem = getListDao.findAll(user, request); 
			log.info("Temporary Item count - {}", tempItem.size());
			response.getBody().setData(tempItem);
			response.getBody().setCount(getListDao.getDataCount(user, request));
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
	
}
