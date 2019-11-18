package com.cokreates.grp.ast.comacq.directout.response;

import com.cokreates.grp.util.constant.Constant;
import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetByOid {

    private @Expose String oid,
                           remarks,
                           assetOid,
                           assetCode;
                           
    @Override
    public String toString() {
    	return Constant.print(this);
    }

}
