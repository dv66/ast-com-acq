package com.cokreates.grp.ast.comacq.assetinfo.model;

import javax.validation.constraints.NotNull;

import com.cokreates.grp.util.constant.Constant;
import com.cokreates.grp.util.constant.Message;
import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssetInfo {

	@NotNull(message=Message.REQUEST_MSG_PREFIX+"null_asset_info_oid")
	private @Expose String oid;
    
    private @Expose String description, serialNo, remarks;
    private @Expose Double expiryDuration;

    @Override
    public String toString() {
    	return Constant.print(this);
    }

}
