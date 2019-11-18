package com.cokreates.grp.ast.comacq.tempitem.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.cokreates.grp.util.constant.Constant;
import com.cokreates.grp.util.constant.Message;
import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DraftTempItemDetail {

	@NotNull(message=Message.REQUEST_MSG_PREFIX+"null itemOid")
	@NotEmpty(message=Message.REQUEST_MSG_PREFIX+"empty itemOid")
	private @Expose String itemOid;
	
	private @Expose String remarks, workOrderDetailOid;
	
	private @Expose int receivedQuantity, extraQuantity;
	
    @Override
    public String toString() {
    	return Constant.print(this);
    }

}
