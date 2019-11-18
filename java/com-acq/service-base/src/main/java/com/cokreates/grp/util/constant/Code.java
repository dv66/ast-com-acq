package com.cokreates.grp.util.constant;

public enum Code {

	C_500("500-ast"), 
	C_501("501-ast"), 
	C_200("200");

	private String code;

	Code(String code) {
		this.code = code;
	}

	public String get() {
		return code;
	}

}
