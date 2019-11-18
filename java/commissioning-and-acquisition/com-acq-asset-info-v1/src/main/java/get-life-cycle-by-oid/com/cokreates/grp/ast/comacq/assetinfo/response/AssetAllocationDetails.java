package com.cokreates.grp.ast.comacq.assetinfo.response;

import org.joda.time.DateTime;
import com.cokreates.grp.util.constant.Constant;
import com.cokreates.grp.util.date.DateDeserializer;
import com.cokreates.grp.util.date.DateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.Expose;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonSerialize
@JsonDeserialize
@NoArgsConstructor
@AllArgsConstructor
public class AssetAllocationDetails {
	
	private @Expose String assetAllocationcode, assetRequisitionCode, allocatorOid, endUserOid, qcPassed, 
							allocatorNameBn, allocatorNameEn, endUserNameBn, endUserNameEn;
	
	@JsonSerialize(using=DateSerializer.class)
	@JsonDeserialize(using=DateDeserializer.class)
	private @Expose DateTime allocationDate, receivedDate, requestedDate;
	
	@Override
	public String toString() {
		return Constant.print(this);
	}
	
}

