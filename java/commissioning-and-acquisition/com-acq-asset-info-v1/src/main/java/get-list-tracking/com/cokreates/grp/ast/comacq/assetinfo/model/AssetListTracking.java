package com.cokreates.grp.ast.comacq.assetinfo.model;

import com.cokreates.grp.model.inv.InvItem;
import com.cokreates.grp.util.constant.Constant;
import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssetListTracking {

    private @Expose String itemOid, itemCode, itemNameBn, itemNameEn, itemCategoryOid, employeeOid,
    itemCategoryNameBn, itemCategoryNameEn, itemGroupOid, itemGroupNameBn, itemGroupNameEn, recievedBy, recievedByNameBn, 
    recievedByNameEn, assetOid, assetCode, assetStatus, designationNameEn, designationNameBn, officeUnitNameBn, officeUnitNameEn,
    reqTypeBn, reqTypeEn;
    
//    @JsonSerialize(using=DateSerializer.class)
//	@JsonDeserialize(using=DateDeserializer.class)
//    private @Expose DateTime receivedAt;
    
    public void itemValuesSetter(InvItem item) {
    	this.itemNameEn = item.getItemName();
    	this.itemNameBn = item.getBnItemName();
    	this.itemCategoryOid = item.getItemSetupDto().getCategory().getCategoryId();
    	this.itemCategoryNameBn = item.getItemSetupDto().getCategory().getBnCatName();
    	this.itemCategoryNameEn = item.getItemSetupDto().getCategory().getCatName();
    	this.itemGroupOid = item.getItemSetupId();
    	this.itemGroupNameBn = item.getItemSetupDto().getBnItemName();
    	this.itemGroupNameEn = item.getItemSetupDto().getItemName();
    	this.itemCode = item.getItemCode();
    }
    
    @Override
    public String toString() {
    	return Constant.print(this);
    }

}
