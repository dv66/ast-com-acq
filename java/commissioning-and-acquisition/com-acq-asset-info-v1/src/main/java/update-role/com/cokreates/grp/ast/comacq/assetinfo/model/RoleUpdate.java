package com.cokreates.grp.ast.comacq.assetinfo.model;

import java.util.List;

import javax.validation.Valid;
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
public class RoleUpdate {
		
	@NotNull(message = Message.REQUEST_MSG_PREFIX + "null_grp_user_oid")
	private @Expose String grpUserOid;

	@Valid
	@NotNull(message = Message.REQUEST_MSG_PREFIX + "null_details_data")
	private @Expose List<RoleDetailsUpdate> roleDetails;

	@Override
	public String toString() {
		return Constant.print(this);
	}
}
