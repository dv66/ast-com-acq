package com.cokreates.grp.ast.comacq.directin.model;


import com.cokreates.grp.util.constant.Constant;
import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetDirectIn {
    
	private @Expose String endUserOid, purposeOid, officeUnitOid, directInType, officeUnitPostOid;

    @Override
    public String toString() {
    	return Constant.print(this);
    }

}

