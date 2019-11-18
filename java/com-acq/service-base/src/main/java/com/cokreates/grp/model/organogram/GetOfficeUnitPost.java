package com.cokreates.grp.model.organogram;


import com.cokreates.grp.util.constant.Constant;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class GetOfficeUnitPost {
	
    @JsonProperty("post")
    private @Expose OfficeUnitPost post;

	@Override
	public String toString() {
		return Constant.print(this);
	}


}
