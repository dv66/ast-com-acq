package com.cokreates.grp.ast.comacq.assetinfo.response;

import java.util.List;

import com.cokreates.grp.ast.comacq.assetinfo.model.DraftGetListAssetInfo;
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
public class DraftGetListByTempOidResponseBody {

	private @Expose List<DraftGetListAssetInfo> data;

	@Override
	public String toString() {
		return Constant.print(this);
	}
	
}

