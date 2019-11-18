package com.cokreates.grp.ast.comacq.directin.model;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;


import com.cokreates.grp.util.constant.Constant;
import com.cokreates.grp.util.constant.Message;
import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RejectDirectIn {
    
 
    @Valid
    @NotNull(message=Message.REQUEST_MSG_PREFIX+"null_oid")
	private @Expose String oid;
    
    @Override
    public String toString() {
    	return Constant.print(this);
    }

}

