package com.cokreates.grp.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.multipart.MultipartFile;

import com.cokreates.grp.model.file.FileResponse;
import com.cokreates.grp.util.constant.Api;

import feign.Param;
import feign.RequestLine;

@FeignClient(name="${cmn.service.file.management.name}", url="${cmn.service.file.management.url}")
public interface CmnServiceFileManagement {
	
	@RequestLine(value="POST "+Api.COMMON_FILE_UPLOAD_V1_GET_PATH)
	public FileResponse uploadFile(@Param("file") MultipartFile file, @Param("tag") String tag, @Param("title") String title, @Param("description") String description, @Param("oid") String oid, @Param("createdBy") String createdBy);
	
}