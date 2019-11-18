package com.cokreates.grp.ast.comacq.tempitem.model;

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
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateTempItem {
	
	@NotNull(message=Message.REQUEST_MSG_PREFIX+"null_oid")
	private @Expose String oid;

	private @Expose String chalanNo;
	
	@JsonSerialize(using=DateSerializer.class)
	@JsonDeserialize(using=DateDeserializer.class)
	private @Expose DateTime chalanDate;

	private @Expose String workorderNo, workOrderOid;

	@JsonSerialize(using=DateSerializer.class)
	@JsonDeserialize(using=DateDeserializer.class)
	private @Expose DateTime workorderDate;
	
	private @Expose String contractNo;

	@JsonSerialize(using=DateSerializer.class)
	@JsonDeserialize(using=DateDeserializer.class)
	private @Expose DateTime contractSigningDate;

	@JsonSerialize(using=DateSerializer.class)
	@JsonDeserialize(using=DateDeserializer.class)
	private @Expose DateTime receivedAt;
	
	private @Expose String procurementMethod;
	
	private @Expose String vendorOid;
	
	private @Expose String description;
	
	@Valid
	@NotNull(message=Message.REQUEST_MSG_PREFIX+"null_update_temp_item_detail")
	private @Expose List<UpdateTempItemDetail> updateTempItemDetail;
		
    @Override
    public String toString() {
    	return Constant.print(this);
    }

}
