package com.cokreates.grp.ast.comacq.assetinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cokreates.grp.ast.comacq.assetinfo.dao.GetListRoleDao;
import com.cokreates.grp.ast.comacq.assetinfo.model.RoleList;
import com.cokreates.grp.ast.comacq.assetinfo.request.GetListRoleRequest;
import com.cokreates.grp.ast.comacq.assetinfo.response.GetListRoleResponse;
import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.service.BaseService;
import com.cokreates.grp.util.constant.Code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("comAcqAssetInfoV1GetListRoleService")
public class GetListRoleService extends BaseService<GetListRoleRequest, GetListRoleResponse> {

	@Autowired
	@Qualifier("comAcqAssetInfoV1GetListRoleDao")
	private GetListRoleDao getListRoleDao;
	
	@Override
	public GetListRoleResponse perform(AuthUser user, String url, String version, GetListRoleRequest request, GetListRoleResponse response) throws AppException {
		try {
			List<RoleList> roleList = getListRoleDao.findAll(request); 
			log.info("Role list count - {}", roleList.size());
			response.getBody().setData(roleList);
			response.getBody().setCount(getListRoleDao.getDataCount(request));
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
	
}
