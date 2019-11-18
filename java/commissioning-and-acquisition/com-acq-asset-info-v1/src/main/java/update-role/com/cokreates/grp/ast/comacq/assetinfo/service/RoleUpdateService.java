package com.cokreates.grp.ast.comacq.assetinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cokreates.grp.ast.comacq.assetinfo.dao.RoleUpdateDao;
import com.cokreates.grp.ast.comacq.assetinfo.model.RoleDetailsUpdate;
import com.cokreates.grp.ast.comacq.assetinfo.request.RoleUpdateRequest;
import com.cokreates.grp.ast.comacq.assetinfo.response.RoleUpdateResponse;
import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.service.BaseService;
import com.cokreates.grp.util.constant.Code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("comacqAssetInfoV1RoleUpdateService")
public class RoleUpdateService extends BaseService<RoleUpdateRequest, RoleUpdateResponse> {

	@Autowired
	@Qualifier("comacqAssetInfoV1RoleUpdateDao")
	private RoleUpdateDao updateDao;
	
	
	@Override
	public RoleUpdateResponse perform(AuthUser user, String url, String version, RoleUpdateRequest request, RoleUpdateResponse response) throws AppException {
		
		try {			
			List<RoleDetailsUpdate> roleList = updateDao.getRoles(request.getBody().getData().getGrpUserOid());			
			updateDao.saveRoleUpdate(user, request.getBody().getData(), roleList);
			log.info("Successfully Updated Data - {}", request.getBody().getData());
			response.getBody().setData(true);
			response.getHeader().setResponseCode(Code.C_200.get());
			response.getHeader().setResponseMessage("Successfully Updated Data");
			
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
	
}
