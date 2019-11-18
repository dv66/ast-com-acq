package com.cokreates.grp.model.organogram;


import java.util.Map;

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
public class OfficeUnitPostResponse {

	private @Expose OfficeUnitPostResponseHeader header;
	private @Expose Map<String, Object> meta;
	private @Expose OfficeUnitPostResponseBody body;

	@Override
	public String toString() {
		return Constant.print(this);
	}
}