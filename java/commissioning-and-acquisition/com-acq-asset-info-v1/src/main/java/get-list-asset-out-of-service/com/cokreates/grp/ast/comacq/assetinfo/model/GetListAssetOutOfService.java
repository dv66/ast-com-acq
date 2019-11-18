package com.cokreates.grp.ast.comacq.assetinfo.model;

import com.cokreates.grp.model.inv.InvItem;
import com.cokreates.grp.util.constant.Constant;
import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetListAssetOutOfService {

	private @Expose String oid, code, description, status, itemOid, itemNameEn,
	itemNameBn, tempItemCode;

	public void itemValuesSetter(InvItem item) {
    	this.itemNameEn = item.getItemName();
    	this.itemNameBn = item.getBnItemName();
    }
	
    @Override
    public String toString() {
    	return Constant.print(this);
    }
}
