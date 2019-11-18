package com.cokreates.grp.model.hrm;

import java.util.Map;

import com.cokreates.grp.util.constant.Constant;
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
public class EmployeeInfoResponse {
	
	private @Expose Map<String, Object> meta;
	private @Expose EmployeeInfoResponseBody body;

	@Override
	public String toString() {
		return Constant.print(this);
	}

}