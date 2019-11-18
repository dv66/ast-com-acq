package com.cokreates.grp.ast.comacq.tempitem.response;

import com.cokreates.grp.model.inv.InvItem;
import com.cokreates.grp.util.constant.Constant;
import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetByOidAfterQc {

    private @Expose String oid, workOrderDetailOid, itemOid, itemNameEn, itemNameBn, remarks, uomNameEn, uomNameBn;
    private @Expose double receivedQuantity, qualifiedQuantity, disqualifiedQuantity, extraQuantity;
    
    public void itemValuesSetter(InvItem item) {
    	this.itemNameEn = item.getItemName();
    	this.itemNameBn = item.getBnItemName();
    	this.uomNameEn = item.getItemSetupDto().getUom().getUomId();
    	this.uomNameBn = item.getItemSetupDto().getUom().getBnUomId();
    }
    
    @Override
    public String toString() {
    	return Constant.print(this);
    }

}
