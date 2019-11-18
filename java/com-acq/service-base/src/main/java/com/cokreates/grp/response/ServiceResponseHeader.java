package com.cokreates.grp.response;

import org.joda.time.DateTime;

import com.cokreates.grp.util.constant.Constant;
import com.cokreates.grp.util.date.DateTimeDeserializer;
import com.cokreates.grp.util.date.DateTimeSerializer;
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
public class ServiceResponseHeader 							{
	
	@JsonSerialize(using=DateTimeSerializer.class)
	@JsonDeserialize(using=DateTimeDeserializer.class)
	private @Expose DateTime requestReceivedTime, responseTime;	
	
	private @Expose int hopCount, responseProcessingTimeInMs;
	private @Expose String requestId, responseCode, responseMessage, responseVersion, requestSourceService, traceId;

	@Override
	public String toString() {
		return Constant.print(this);
	}
	
}
