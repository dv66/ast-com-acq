package com.cokreates.grp.ast.comacq.directout.request;

import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.cokreates.grp.request.ServiceRequestHeader;
import com.cokreates.grp.util.constant.Constant;
import com.cokreates.grp.util.constant.Message;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JsonSerialize
@JsonDeserialize
@NoArgsConstructor
public class GetListRequest {
	
	@Valid
	@NotNull(message=Message.REQUEST_MSG_PREFIX+"null_header")
	private @Expose ServiceRequestHeader header;

	@NotNull(message=Message.REQUEST_MSG_PREFIX+"null_meta")
	private @Expose Map<String, Object> meta;

	@Valid
	@NotNull(message=Message.REQUEST_MSG_PREFIX+"null_body")
	private @Expose GetListRequestBody body;

	@Override
	public String toString() {
		return Constant.print(this);
	}
	
}
