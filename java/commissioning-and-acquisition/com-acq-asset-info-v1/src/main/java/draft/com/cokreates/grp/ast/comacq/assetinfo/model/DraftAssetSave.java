package com.cokreates.grp.ast.comacq.assetinfo.model;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.cokreates.grp.util.constant.Message;
import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DraftAssetSave {
	
	@NotNull(message=Message.REQUEST_MSG_PREFIX+"null_temp_oid")
	private @Expose String tempOid;
	
	@Valid
	@NotNull(message=Message.REQUEST_MSG_PREFIX+"null_asset_items")
	private @Expose List<DraftAssetInfo> assetItems;
	

}
