package com.cokreates.grp.ast.comacq.directin.model;

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
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApproveDirectInDetail {	
		
	@NotNull(message=Message.REQUEST_MSG_PREFIX+"null_item_oid")
	private @Expose String itemOid, oid;
	
	private @Expose String remarks, serialNo, previousTag ;
	private @Expose Double expiryDuration;
	
    @JsonSerialize(using=DateSerializer.class)
	@JsonDeserialize(using=DateDeserializer.class)
	private @Expose DateTime allocationDate;
	
    @Override
    public String toString() {
    	return Constant.print(this);
    }

}
