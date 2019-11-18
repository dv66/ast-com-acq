package com.cokreates.grp.ast.comacq.directout.model;

import com.cokreates.grp.util.constant.Constant;
import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApproveDirectOutDetail {	
		
	
	private @Expose String oid, assetOid, remarks;
	
	
    @Override
    public String toString() {
    	return Constant.print(this);
    }

}
