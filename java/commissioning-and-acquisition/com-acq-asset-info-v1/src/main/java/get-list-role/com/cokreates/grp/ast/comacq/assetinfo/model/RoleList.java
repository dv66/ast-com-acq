package com.cokreates.grp.ast.comacq.assetinfo.model;

import com.cokreates.grp.util.constant.Constant;
import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleList {
	
	private @Expose String oid;
	private @Expose String nameEn;
	private @Expose String nameBn;

//    private @Expose String oid, code, receivedBy, receivedByEn, receivedByBn, description, status, vendorOid, vendorNameEn, vendorNameBn, createdBy, updatedBy;
    
//    @JsonSerialize(using=DateSerializer.class)
//	@JsonDeserialize(using=DateDeserializer.class)
//    private @Expose DateTime receivedAt, createdOn, updatedOn;
    
    
    @Override
    public String toString() {
    	return Constant.print(this);
    }

}
