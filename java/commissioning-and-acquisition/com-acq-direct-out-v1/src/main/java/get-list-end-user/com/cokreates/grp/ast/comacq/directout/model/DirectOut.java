package com.cokreates.grp.ast.comacq.directout.model;

import com.cokreates.grp.util.constant.Constant;
import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DirectOut {

    private @Expose String oid, code, status, endUserNameEn, endUserNameBn, endUserOid;
    

    @Override
    public String toString() {
    	return Constant.print(this);
    }

}
