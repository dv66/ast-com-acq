package com.cokreates.grp.ast.comacq.assetinfo.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.cokreates.grp.ast.comacq.assetinfo.model.RoleUpdate;
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
public class RoleUpdateRequestBody {
	
	@Valid
	@NotNull(message=Message.REQUEST_MSG_PREFIX+"null_data")
	private @Expose RoleUpdate data;


}
