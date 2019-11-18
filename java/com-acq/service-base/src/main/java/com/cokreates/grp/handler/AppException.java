package com.cokreates.grp.handler;

import com.cokreates.grp.request.ServiceRequestHeader;
import com.cokreates.grp.util.constant.Constant;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonSerialize
@JsonDeserialize
@SuppressWarnings("serial")
public class AppException extends Exception {
	
	private @Expose String code;
	private @Expose String message;
	private @Expose ServiceRequestHeader header;

	public AppException(ServiceRequestHeader header, String message) {
		super(message);
		this.message = message;
		this.header = header;
	}

	public AppException(ServiceRequestHeader header, String code, String message) {
		super(message);
		this.code = code;
		this.message = message;
		this.header = header;
	}

	@Override
	public String toString() {
		return Constant.print(this);
	}
}