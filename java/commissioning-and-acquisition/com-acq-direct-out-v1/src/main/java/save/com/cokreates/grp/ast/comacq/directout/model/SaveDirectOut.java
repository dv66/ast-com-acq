package com.cokreates.grp.ast.comacq.directout.model;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.joda.time.DateTime;

import com.cokreates.grp.util.constant.Constant;
import com.cokreates.grp.util.constant.Message;
import com.cokreates.grp.util.date.DateDeserializer;
import com.cokreates.grp.util.date.DateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveDirectOut {
    
	private @Expose String remarks, storeOid, endUserOid, officeUnitOid, purposeOid, officeUnitPostOid;
 
    @Valid
    @NotNull(message=Message.REQUEST_MSG_PREFIX+"null_save_direct_out_detail")
	private @Expose List<SaveDirectOutDetail> saveDirectOutDetail;
    
    @JsonSerialize(using=DateSerializer.class)
	@JsonDeserialize(using=DateDeserializer.class)
	private @Expose DateTime allocationDate;
    
    @Override
    public String toString() {
    	return Constant.print(this);
    }

}

