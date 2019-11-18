package com.cokreates.grp.model;

import com.cokreates.grp.util.constant.Constant;
import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeResponse {

	private @Expose String errorMessage;
	private @Expose int status;
	private @Expose Employee data;
	    
	    @Override
	   public String toString() {
	   	return Constant.print(this);
	   }
}
