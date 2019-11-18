package com.cokreates.grp.ast.comacq.tempitem.response;

import com.cokreates.grp.util.constant.Constant;
import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetByOidBeforeQc {

    private @Expose String oid, workOrderDetailOid, itemOid, itemNameEn, itemNameBn, remarks, uomNameEn, uomNameBn;
    private @Expose double receivedQuantity, extraQuantity;
    
    @Override
    public String toString() {
    	return Constant.print(this);
    }

}
