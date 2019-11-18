package com.cokreates.grp.ast.comacq.tempitem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cokreates.grp.ast.comacq.tempitem.dao.GetListDaoCommittee;
import com.cokreates.grp.ast.comacq.tempitem.model.TempItemCommittee;
import com.cokreates.grp.ast.comacq.tempitem.request.GetListRequestCommittee;
import com.cokreates.grp.ast.comacq.tempitem.response.GetListResponseCommittee;
import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.service.BaseService;
import com.cokreates.grp.util.constant.Code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("comAcqTempItemV1GetServiceCommittee")
public class GetServiceCommittee extends BaseService<GetListRequestCommittee, GetListResponseCommittee> {

	@Autowired
	@Qualifier("comAcqTempItemV1GetListDaoCommittee")
	private GetListDaoCommittee getListDao;
	
	@Override
	public GetListResponseCommittee perform(AuthUser user, String url, String version, GetListRequestCommittee request, GetListResponseCommittee response) throws AppException {
		try {
			List<TempItemCommittee> tempItem = getListDao.findAll(request, user); 
			log.info("Temporary Item count - {}", tempItem.size());
			response.getBody().setData(tempItem);
			response.getBody().setCount(getListDao.getCountDataSql(request, user.getOfficeOid()));
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
	
}
