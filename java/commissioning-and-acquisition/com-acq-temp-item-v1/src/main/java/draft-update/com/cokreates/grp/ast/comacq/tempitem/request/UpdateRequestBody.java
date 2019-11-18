package com.cokreates.grp.ast.comacq.tempitem.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.cokreates.grp.ast.comacq.tempitem.model.UpdateTempItem;
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
public class UpdateRequestBody {

	@Valid 
	@NotNull(message=Message.REQUEST_MSG_PREFIX+"null_update_temp_item")
	private @Expose UpdateTempItem data;
	
	@Override
	public String toString() {
		return Constant.print(this);
	}

}
