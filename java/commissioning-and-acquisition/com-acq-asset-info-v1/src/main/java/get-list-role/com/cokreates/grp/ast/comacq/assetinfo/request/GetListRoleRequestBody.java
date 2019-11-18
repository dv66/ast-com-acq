package com.cokreates.grp.ast.comacq.assetinfo.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.cokreates.grp.model.SearchParams;
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
public class GetListRoleRequestBody {

	@Valid
	@NotNull(message=Message.REQUEST_MSG_PREFIX+"null_operation")
	private @Expose SearchParams searchParams;

	@Override
	public String toString() {
		return Constant.print(this);
	}

}
