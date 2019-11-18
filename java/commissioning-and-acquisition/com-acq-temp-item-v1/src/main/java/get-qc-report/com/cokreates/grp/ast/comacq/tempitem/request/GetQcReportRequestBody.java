package com.cokreates.grp.ast.comacq.tempitem.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
public class GetQcReportRequestBody {

	@NotNull(message=Message.REQUEST_MSG_PREFIX+"null_oid")
	@NotBlank(message=Message.REQUEST_MSG_PREFIX+"empty_oid")
	private @Expose String oid;

	@Override
	public String toString() {
		return Constant.print(this);
	}

}
