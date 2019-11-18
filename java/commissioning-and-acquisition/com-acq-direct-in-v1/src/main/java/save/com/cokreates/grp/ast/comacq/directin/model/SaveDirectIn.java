package com.cokreates.grp.ast.comacq.directin.model;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.cokreates.grp.util.constant.Constant;
import com.cokreates.grp.util.constant.Message;
import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveDirectIn {
    
	private @Expose String remarks,
	                       storeOid, 
	                       endUserOid, 
	                       officeUnitOid, 
	                       purposeOid,
	                       officeUnitPostOid,
	                       directInType;
 
    @Valid
    @NotNull(message=Message.REQUEST_MSG_PREFIX+"null_save_direct_in_detail")
	private @Expose List<SaveDirectInDetail> saveDirectInDetail;
    
    @Override
    public String toString() {
    	return Constant.print(this);
    }

}

