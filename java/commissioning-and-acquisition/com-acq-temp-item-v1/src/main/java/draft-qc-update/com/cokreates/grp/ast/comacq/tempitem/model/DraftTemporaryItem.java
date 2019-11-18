package com.cokreates.grp.ast.comacq.tempitem.model;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.cokreates.grp.util.constant.Constant;
import com.cokreates.grp.util.constant.Message;
import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DraftTemporaryItem {
	
	@NotNull(message=Message.REQUEST_MSG_PREFIX+"null_data")
	private @Expose String oid;
    
	@Valid
	@NotNull(message=Message.REQUEST_MSG_PREFIX+"null_data")
	private @Expose List<DraftTemporaryItemDetail> tempItemDetails;
	
    @Override
    public String toString() {
    	return Constant.print(this);
    }

}
