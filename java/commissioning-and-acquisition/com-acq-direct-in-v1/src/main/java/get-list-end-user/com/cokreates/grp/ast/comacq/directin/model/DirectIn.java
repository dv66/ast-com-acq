package com.cokreates.grp.ast.comacq.directin.model;

import com.cokreates.grp.util.constant.Constant;
import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DirectIn {

    private @Expose String oid, code, status;
    

    @Override
    public String toString() {
    	return Constant.print(this);
    }

}
