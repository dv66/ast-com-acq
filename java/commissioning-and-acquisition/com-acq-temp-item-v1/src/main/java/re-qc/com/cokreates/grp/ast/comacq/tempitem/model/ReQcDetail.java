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
public class ReQcDetail {

	@NotNull(message=Message.REQUEST_MSG_PREFIX+"null_oid")
	private @Expose String oid,decisionOnQcNote,status;
	
//	private @Expose String remarks;
	
//	@NotNull(message=Message.REQUEST_MSG_PREFIX+"null_qualified_quantity")
//	private @Expose int qualifiedQuantity;
//	
//	@NotNull(message=Message.REQUEST_MSG_PREFIX+"null_disqualified_quantity")
//	private @Expose int disqualifiedQuantity;
	
    @Override
    public String toString() {
    	return Constant.print(this);
    }

}
