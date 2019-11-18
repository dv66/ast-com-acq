package com.cokreates.grp.model.organogram;


import java.util.List;

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
public class OfficeUnitResponseBody {

	private @Expose List<OfficeUnit> data;

	@Override
	public String toString() {
		return Constant.print(this);
	}
}