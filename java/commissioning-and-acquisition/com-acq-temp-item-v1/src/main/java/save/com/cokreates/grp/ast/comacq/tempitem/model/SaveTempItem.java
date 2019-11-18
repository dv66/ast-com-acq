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
import lombok.Setter;

@Getter
@Setter
public class SaveTempItem {
    
    @NotNull(message=Message.REQUEST_MSG_PREFIX+"null_chalan_no")
	private @Expose String chalanNo;
    
	private @Expose String workorderNo;
	private @Expose String workOrderOid;
	
    
	private @Expose String contractNo;
        
    @NotNull(message=Message.REQUEST_MSG_PREFIX+"null_vendor_oid")
	private @Expose String vendorOid;
    
	private @Expose String description;

	@NotNull(message=Message.REQUEST_MSG_PREFIX+"null_received_at")
    @JsonSerialize(using=DateSerializer.class)
	@JsonDeserialize(using=DateDeserializer.class)
	private @Expose DateTime receivedAt;
	
	@NotNull(message=Message.REQUEST_MSG_PREFIX+"null_chalan_date")
    @JsonSerialize(using=DateSerializer.class)
	@JsonDeserialize(using=DateDeserializer.class)
	private @Expose DateTime chalanDate;
	
    @JsonSerialize(using=DateSerializer.class)
	@JsonDeserialize(using=DateDeserializer.class)
	private @Expose DateTime workorderDate;
	
    @JsonSerialize(using=DateSerializer.class)
	@JsonDeserialize(using=DateDeserializer.class)
	private @Expose DateTime contractSigningDate;
	
	@NotNull(message=Message.REQUEST_MSG_PREFIX+"null_procurement_method")
	private @Expose String procurementMethod;
    
    @Valid
    @NotNull(message=Message.REQUEST_MSG_PREFIX+"null_save_temp_item_detail")
	private @Expose List<SaveTempItemDetail> saveTempItemDetail;
    
    @Override
    public String toString() {
    	return Constant.print(this);
    }

}

