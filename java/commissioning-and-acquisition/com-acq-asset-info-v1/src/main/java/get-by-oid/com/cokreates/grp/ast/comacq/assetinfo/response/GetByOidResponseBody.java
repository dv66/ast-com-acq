package com.cokreates.grp.ast.comacq.assetinfo.response;



import org.joda.time.DateTime;

import com.cokreates.grp.util.constant.Constant;
import com.cokreates.grp.util.date.DateDeserializer;
import com.cokreates.grp.util.date.DateSerializer;
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
public class GetByOidResponseBody {
	
	private @Expose String code, description, status, createdBy, updatedBy, temporaryItemCode,
	                        serialNo, expiryDuration, remarks, itemNameEn, itemNameBn, qcBy, qcByEn,
	                        qcByBn, assetAddedBy, assetAddedByEn, assetAddedByBn, itemOid;
	
	@JsonSerialize(using=DateSerializer.class)
	@JsonDeserialize(using=DateDeserializer.class)
	private @Expose DateTime createdOn, updatedOn, qcOn, assetAddedOn;

	@Override
	public String toString() {
		return Constant.print(this);
	}
	
}

