package com.cokreates.grp.ast.comacq.tempitem.model;

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
public class UpdateTempItemDetail {
		
	@NotNull(message=Message.REQUEST_MSG_PREFIX+"null_item_oid")
	private @Expose String itemOid;
	
	private @Expose String remarks, workOrderDetailOid;
	
	
	private @Expose int receivedQuantity;
	
	
	private @Expose int extraQuantity;
	
    @Override
    public String toString() {
    	return Constant.print(this);
    }

}
