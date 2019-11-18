package com.cokreates.grp.ast.comacq.directout.model;

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
public class SaveDirectOutDetail {	
		
	@NotNull(message=Message.REQUEST_MSG_PREFIX+"null_asset_oid")
	private @Expose String assetOid;
	
	private @Expose String remarks;
	
    @Override
    public String toString() {
    	return Constant.print(this);
    }

}
