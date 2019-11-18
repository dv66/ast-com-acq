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
public class DraftSubmitTempItem {
	
	@NotNull(message=Message.REQUEST_MSG_PREFIX+"null_oid")
	private @Expose String oid;
	
	@NotNull(message=Message.REQUEST_MSG_PREFIX+"null_received_at")
	@JsonSerialize(using=DateSerializer.class)
	@JsonDeserialize(using=DateDeserializer.class)
	private @Expose DateTime receivedAt;
		
	@NotNull(message=Message.REQUEST_MSG_PREFIX+"null_chalan_no")
	private @Expose String chalanNo;
	
	@NotNull(message=Message.REQUEST_MSG_PREFIX+"null_chalan_date")
	@JsonSerialize(using=DateSerializer.class)
	@JsonDeserialize(using=DateDeserializer.class)
	private @Expose DateTime chalanDate;
	
	private @Expose String workorderNo;
	private @Expose String workOrderOid;
	
	@JsonSerialize(using=DateSerializer.class)
	@JsonDeserialize(using=DateDeserializer.class)
	private @Expose DateTime workorderDate;
	
	private @Expose String contractNo;
	
	@JsonSerialize(using=DateSerializer.class)
	@JsonDeserialize(using=DateDeserializer.class)
	private @Expose DateTime contractSigningDate;
	
	@NotNull(message=Message.REQUEST_MSG_PREFIX+"null_procurement_method")
	private @Expose String procurementMethod;
	
	private @Expose String description;
	
	@NotNull(message=Message.REQUEST_MSG_PREFIX+"null_vendor_oid")
	private @Expose String vendorOid;
	
	@Valid
	@NotNull(message=Message.REQUEST_MSG_PREFIX+"null_update_temp_item_detail")
	private @Expose List<DraftSubmitTempItemDetail> draftSubmitTempItemDetail;
		
    @Override
    public String toString() {
    	return Constant.print(this);
    }

}
