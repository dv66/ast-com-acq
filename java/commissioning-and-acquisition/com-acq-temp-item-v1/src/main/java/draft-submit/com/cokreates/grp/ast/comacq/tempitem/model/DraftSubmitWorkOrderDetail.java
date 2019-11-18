package com.cokreates.grp.ast.comacq.tempitem.model;


import com.cokreates.grp.util.constant.Constant;
import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DraftSubmitWorkOrderDetail {
	
	private @Expose String oid;
	private @Expose int orderedQuantity, previosulyReceivedQuantity;
    
    @Override
    public String toString() {
    	return Constant.print(this);
    }

}