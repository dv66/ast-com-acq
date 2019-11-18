package com.cokreates.grp.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.cokreates.grp.util.constant.Constant;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Getter
@Setter
@NoArgsConstructor
@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class Employee {

	@JsonProperty("oid")
	private String oid;
	
	@JsonProperty("nameEn")
	private String nameEn;
		
	@JsonProperty("nameBn")
	private String nameBn;
	
	@Override
	public String toString() {
	return Constant.print(this);
		}
}
