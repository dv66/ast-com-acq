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
public class OfficeUnit {
	
	@JsonProperty("nameEn")
    private  @Expose String nameEn;
    
    @JsonProperty("nameBn")
    private @Expose String nameBn;

	@Override
	public String toString() {
		return Constant.print(this);
	}


}
