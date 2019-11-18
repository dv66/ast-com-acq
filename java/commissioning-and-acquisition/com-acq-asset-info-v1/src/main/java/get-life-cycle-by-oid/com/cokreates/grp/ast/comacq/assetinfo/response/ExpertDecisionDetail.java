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
public class ExpertDecisionDetail {
	
	private @Expose String code, decisionBy, decisionByBn, decisionByEn, status, cost, vendorName, remarks, disposalProcess;
	
	@JsonSerialize(using=DateSerializer.class)
	@JsonDeserialize(using=DateDeserializer.class)
	private @Expose DateTime decisionOn, maintenanceDate;
	
	@Override
	public String toString() {
		return Constant.print(this);
	}
	
}

