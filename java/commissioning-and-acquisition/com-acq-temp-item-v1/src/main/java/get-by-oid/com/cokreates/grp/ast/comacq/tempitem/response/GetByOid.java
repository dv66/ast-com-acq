package com.cokreates.grp.ast.comacq.tempitem.response;

import com.cokreates.grp.model.inv.InvItem;
import com.cokreates.grp.util.constant.Constant;
import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetByOid {

    private @Expose String oid, workOrderDetailOid, itemOid, itemNameEn, itemNameBn, remarks, uomNameEn, uomNameBn, itemGroupOid, itemGroupNameEn, itemGroupNameBn, itemCategoryOid, itemCategoryNameEn, itemCategoryNameBn;
    private @Expose int receivedQuantity, qualifiedQuantity, disqualifiedQuantity, extraQuantity, quantity, previousReceivedQuantity;
    
    public GetByOid() {
    }
    
    public void itemValuesSetter(InvItem item) {
    	this.itemNameEn = item.getItemName();
    	this.itemNameBn = item.getBnItemName();
    	this.uomNameEn = item.getItemSetupDto().getUom().getUomId();
    	this.uomNameBn = item.getItemSetupDto().getUom().getBnUomId();
    	this.itemGroupOid = item.getItemSetupId();
    	this.itemGroupNameEn = item.getItemSetupDto().getItemName();
    	this.itemGroupNameBn = item.getItemSetupDto().getBnItemName();
    	this.itemCategoryOid = item.getItemSetupDto().getCategory().getCategoryId();
    	this.itemCategoryNameEn = item.getItemSetupDto().getCategory().getCatName();
    	this.itemCategoryNameBn = item.getItemSetupDto().getCategory().getBnCatName();
    }
    
    @Override
    public String toString() {
    	return Constant.print(this);
    }

}
