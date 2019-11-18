package com.cokreates.grp.ast.comacq.assetinfo.model;

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
public class RoleDetailsUpdate {	
				
	@NotNull(message = Message.REQUEST_MSG_PREFIX + "null_grp_role_oid")
	private @Expose String grpRoleOid;
	
	private @Expose String defaultStatus;
	
	
    @Override
    public String toString() {
    	return Constant.print(this);
    }

}
