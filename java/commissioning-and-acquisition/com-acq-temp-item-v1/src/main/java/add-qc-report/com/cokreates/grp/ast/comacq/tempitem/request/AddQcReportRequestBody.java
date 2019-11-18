package com.cokreates.grp.ast.comacq.tempitem.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.cokreates.grp.util.constant.Constant;
import com.cokreates.grp.util.constant.Message;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonSerialize
@JsonDeserialize
@NoArgsConstructor
@AllArgsConstructor
public class AddQcReportRequestBody {

	@Valid
	@NotNull(message=Message.REQUEST_MSG_PREFIX+"null_file")
	private MultipartFile file;
	
	@Valid
	@NotNull(message=Message.REQUEST_MSG_PREFIX+"null_oid")
	private String oid;
	
	@Override
	public String toString() {
		return Constant.print(this);
	}

}
