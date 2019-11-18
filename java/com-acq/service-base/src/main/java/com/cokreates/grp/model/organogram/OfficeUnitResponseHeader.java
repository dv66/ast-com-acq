package com.cokreates.grp.model.organogram;

import com.cokreates.grp.util.constant.Constant;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JsonSerialize
@JsonDeserialize
@NoArgsConstructor
public class OfficeUnitResponseHeader {

	private @Expose String requestReceivedTime, responseTime, requestId, responseCode,
		responseMessage, responseVersion, requestSourceService, traceId;    
	
	private @Expose int hopCount, responseProcessingTimeInMs;
	
	@Override
	public String toString() {
		return Constant.print(this);
	}
}
