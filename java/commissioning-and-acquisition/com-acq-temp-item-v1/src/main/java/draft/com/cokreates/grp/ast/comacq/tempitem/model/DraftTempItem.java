package com.cokreates.grp.ast.comacq.tempitem.model;

import java.util.List;

import javax.validation.Valid;

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
public class DraftTempItem {

    private @Expose String  description, vendorOid, chalanNo, workorderNo, workOrderOid, contractNo, procurementMethod;

    @JsonSerialize(using=DateSerializer.class)
	@JsonDeserialize(using=DateDeserializer.class)
	private @Expose DateTime receivedAt, chalanDate, workorderDate, contractSigningDate;
    
    @Valid
	private @Expose List<DraftTempItemDetail> draftTempItemDetail;
    
    @Override
    public String toString() {
    	return Constant.print(this);
    }

}

