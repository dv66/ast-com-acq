package com.cokreates.grp.model;

import com.cokreates.grp.util.constant.Constant;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthUser {
	
	@JsonProperty("officeId")
	private String officeOid;
	
//	@JsonProperty("user_name")
//	private String username;
	
	@JsonProperty("userOid")
	private String userOid;
	
	@JsonProperty("employeeId")
	private String employeeOid;
	
	private String token;

	@Override
	public String toString() {
		return Constant.print(this);
	}

}
