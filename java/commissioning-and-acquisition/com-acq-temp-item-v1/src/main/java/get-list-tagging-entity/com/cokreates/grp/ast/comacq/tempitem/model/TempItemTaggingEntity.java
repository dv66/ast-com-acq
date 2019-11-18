package com.cokreates.grp.ast.comacq.tempitem.model;

import org.joda.time.DateTime;

import com.cokreates.grp.util.constant.Constant;
import com.cokreates.grp.util.date.DateDeserializer;
import com.cokreates.grp.util.date.DateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TempItemTaggingEntity {

    private @Expose String oid, code, receivedBy, receivedByEn, receivedByBn, status, description, vendorOid, vendorNameEn, vendorNameBn, createdBy, updatedBy;
    
    @JsonSerialize(using=DateSerializer.class)
	@JsonDeserialize(using=DateDeserializer.class)
    private @Expose DateTime receivedAt, createdOn, updatedOn;
    
    
    @Override
    public String toString() {
    	return Constant.print(this);
    }

}
