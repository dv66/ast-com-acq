package com.cokreates.grp.ast.comacq.assetinfo.model;

import com.cokreates.grp.util.constant.Constant;
import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DraftGetListAssetInfo {

	private @Expose String oid, code, description, status, itemNameEn,
	itemNameBn, tempItemCode, serialNo, expiryDuration, remarks, itemOid;

    @Override
    public String toString() {
    	return Constant.print(this);
    }
}
