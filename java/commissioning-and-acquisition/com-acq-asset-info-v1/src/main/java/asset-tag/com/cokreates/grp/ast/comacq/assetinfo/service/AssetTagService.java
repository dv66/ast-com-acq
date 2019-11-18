package com.cokreates.grp.ast.comacq.assetinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cokreates.grp.ast.comacq.assetinfo.dao.AssetTagDao;
import com.cokreates.grp.ast.comacq.assetinfo.request.AssetTagRequest;
import com.cokreates.grp.ast.comacq.assetinfo.request.AssetTagRequestBody;
import com.cokreates.grp.ast.comacq.assetinfo.response.AssetTagResponse;
import com.cokreates.grp.ast.comacq.assetinfo.response.GetByOidAssetTagResponseBody;
import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.service.BaseService;
import com.cokreates.grp.util.constant.Code;
import com.cokreates.grp.util.constant.Constant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("comacqAssetInfoV1AssetTagService")
public class AssetTagService extends BaseService<AssetTagRequest, AssetTagResponse> {

	@Autowired
	@Qualifier("comacqAssetInfoV1AssetTagDao")
	private AssetTagDao assetTagDao;
	
	@Override
	public AssetTagResponse perform(AuthUser user, String url, String version, AssetTagRequest request, AssetTagResponse response) throws AppException {
		
		try {
			 String tempitemForChecking="";
			AssetTagRequestBody assetTagRequestBody=request.getBody();
			
			for(String oid: assetTagRequestBody.getData()) {
				GetByOidAssetTagResponseBody status=assetTagDao.getAssetStatusByOid(oid);
				tempitemForChecking=status.getTempItemOid();
				if(status.getStatus().equals(Constant.AI_STATUS_READY_FOR_TAGGING)) {
					assetTagDao.saveAssetTaggedInfo(user, oid);
					log.info("Successfully asset tagging completed");
					response.getBody().setData(true);
					response.getHeader().setResponseMessage("Successfully asset tagging completed");
				}else {
					log.info("Unsuccessfully asset tagging completed");
					response.getBody().setData(false);
					response.getHeader().setResponseCode(Code.C_500.get());
					response.getHeader().setResponseMessage("Unsuccessfully asset tagging completed");
				}
				
			}
			List<GetByOidAssetTagResponseBody> getAllStatus=assetTagDao.getAssetStatusAfterAssetTag(tempitemForChecking);
			if(getAllStatus.size()==0) {
				assetTagDao.updateTempItemStatusAfterAssetTagSql(tempitemForChecking);
			}else {
				log.info("Asset tagging Not Updated");
			}
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
	
}
