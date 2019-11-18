package com.cokreates.grp.ast.comacq.directin.response;

import java.util.List;

import com.cokreates.grp.ast.comacq.directin.model.DirectIn;
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
public class GetListResponseBody {

	private @Expose List<DirectIn> data;
	private @Expose int count;

	@Override
	public String toString() {
		return Constant.print(this);
	}
	
}

