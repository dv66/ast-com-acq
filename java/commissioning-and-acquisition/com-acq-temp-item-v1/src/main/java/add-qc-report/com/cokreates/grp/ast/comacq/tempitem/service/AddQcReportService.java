package com.cokreates.grp.ast.comacq.tempitem.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.cokreates.grp.ast.comacq.tempitem.dao.QcReportDao;
import com.cokreates.grp.ast.comacq.tempitem.request.AddQcReportRequest;
import com.cokreates.grp.ast.comacq.tempitem.response.AddQcReportResponse;
import com.cokreates.grp.client.CmnServiceFileManagement;
import com.cokreates.grp.client.FeignSpringFormEncoder;
import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.model.file.FileResponse;
import com.cokreates.grp.service.BaseService;
import com.cokreates.grp.util.IdGenerator;
import com.cokreates.grp.util.constant.Code;
import feign.Feign;
import feign.jackson.JacksonDecoder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("comAcqTempItemV1AddQcReportService")
public class AddQcReportService extends BaseService<AddQcReportRequest, AddQcReportResponse> {

	private Path fileStorageLocation;
	
	@Autowired
	private Environment env;
	
	@Autowired
	@Qualifier("comacqTempitemV1QcReportDao")
	private QcReportDao updateDao;
	
	@Autowired
	@Qualifier("idGenerator")
	private IdGenerator idGenerator;

    
	@Override
	public AddQcReportResponse perform(AuthUser user, String url, String version, AddQcReportRequest request, AddQcReportResponse response) throws AppException {
		try {
			
			MultipartFile file = request.getBody().getFile();
			String fileName = idGenerator.generateId() + "_" + StringUtils.cleanPath(file.getOriginalFilename());

//			Path targetLocation = this.fileStorageLocation.resolve(fileName);
//			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
//          
			Feign.Builder encoder = Feign.builder()
					                .decoder(new JacksonDecoder())
					                .encoder(new FeignSpringFormEncoder());
			
			String fileUrl = null;
	        String serviceUrl = env.getProperty("cmn.service.file.management.url");
	        if(org.apache.commons.lang3.StringUtils.isNotBlank(serviceUrl)) {
	        	fileUrl = new String(serviceUrl);
	        } else {
	        	fileUrl = "http://"+env.getProperty("cmn.service.file.management.name")+"/";
	        }
	        
	        CmnServiceFileManagement cmnServiceFileManagement = encoder.target(CmnServiceFileManagement.class, fileUrl);
	        FileResponse res = cmnServiceFileManagement.uploadFile(file, fileName, fileName, "N/A", fileName, user.getUserOid());
			if(res == null || res.getStatus() != 200) {
				log.error("File response : {}", res);
				throw new AppException(request.getHeader(), Code.C_500.get(), "Unable to upload file");
			}
			
			log.info("File Response : {}", res);
			fileName = res.getData().get(0).getFileOid();
			//+"."+res.getData().get(0).getFileType();
			System.out.println(fileName);
			updateDao.updateTemporaryItem(user, request.getBody(), fileName);
			log.info("Successfully store file");
			response.getBody().setFileName(fileName);
			response.getHeader().setResponseCode(Code.C_200.get());
			response.getHeader().setResponseMessage("Successfully store file");

		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}

}
