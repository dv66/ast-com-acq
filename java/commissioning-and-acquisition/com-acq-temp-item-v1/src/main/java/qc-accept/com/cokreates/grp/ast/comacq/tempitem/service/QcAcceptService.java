package com.cokreates.grp.ast.comacq.tempitem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cokreates.grp.ast.comacq.tempitem.dao.QcAcceptDao;
import com.cokreates.grp.ast.comacq.tempitem.request.QcAcceptRequest;
import com.cokreates.grp.ast.comacq.tempitem.response.QcAcceptResponse;
import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.service.BaseService;
import com.cokreates.grp.util.constant.Code;
import com.cokreates.grp.util.constant.Constant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("comacqTempitemV1QcAcceptService")
public class QcAcceptService extends BaseService<QcAcceptRequest, QcAcceptResponse> {

	@Autowired
	@Qualifier("comacqTempitemV1QcAcceptDao")
	private QcAcceptDao updateDao;
	
	
	@Override
	public QcAcceptResponse perform(AuthUser user, String url, String version, QcAcceptRequest request, QcAcceptResponse response) throws AppException {
		
		try {
			String status = updateDao.checkTemporaryItemStatus(user, request.getBody().getData().getOid());
			if(!status.equals(Constant.TI_STATUS_QC_DONE) ) {
				response.getBody().setData(false);
				response.getHeader().setResponseMessage("Status of Temporary item is not QC Done");
				response.getHeader().setResponseCode(Code.C_500.get());
				return response;
			}
			
			String qualifiedQuantitySum = updateDao.getQualifiedQuantity(user, request.getBody().getData().getOid());
			
			if(Double.parseDouble(qualifiedQuantitySum)== 0.0)
	        {
				updateDao.updateRejectedTemporaryItem(user, request.getBody(), Constant.TI_STATUS_REJECTED);
				log.info("Successfully Temporary Item QC updated and status updated as rejected for zero qualified quantity");
				response.getBody().setData(true);
				response.getHeader().setResponseCode(Code.C_200.get());
				response.getHeader().setResponseMessage("Successfully Temporary Item QC updated and status updated as rejected for zero qualified quantity");
	            
	        }
	        else {
	        	updateDao.updateAcceptedTemporaryItem(user, request.getBody(), Constant.TI_STATUS_QC_APPROVED);
				log.info("Successfully Temporary Item QC accepted");
				response.getBody().setData(true);
				response.getHeader().setResponseCode(Code.C_200.get());
				response.getHeader().setResponseMessage("Successfully Temporary Item QC accepted");

	        }
			
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
	
}
