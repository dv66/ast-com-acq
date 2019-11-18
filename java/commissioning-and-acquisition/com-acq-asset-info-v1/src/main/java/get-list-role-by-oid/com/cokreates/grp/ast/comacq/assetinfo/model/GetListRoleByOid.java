package com.cokreates.grp.ast.comacq.assetinfo.model;

import com.cokreates.grp.util.constant.Constant;
import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetListRoleByOid {

	private @Expose String grpUserOid;
	private @Expose String nameEn;
	private @Expose String nameBn;

//@JsonSerialize(using=DateSerializer.class)
//@JsonDeserialize(using=DateDeserializer.class)
//private @Expose DateTime maintenanceDate, decisionOn;
    
    @Override
    public String toString() {
    	return Constant.print(this);
    }

}
