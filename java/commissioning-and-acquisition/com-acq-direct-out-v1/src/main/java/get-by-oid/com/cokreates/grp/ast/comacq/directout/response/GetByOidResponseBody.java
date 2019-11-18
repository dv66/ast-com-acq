package com.cokreates.grp.ast.comacq.directout.response;

import java.util.List;

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
	
	private @Expose String oid,
                           code,
                           requestedBy,
                           requestedByNameBn,
                           requestedByNameEn,
                           status,
                           decisionBy,
                           decisionByNameBn,
                           decisionByNameEn,
                           endUserOid,
                           endUserNameEn,
                           endUserNameBn,
                           officeUnitOid,
                           officeUnitNameBn,
                           officeUnitNameEn,
                           officeUnitPostOid,
                           postNameBn,
                           postNameEn,
                           allocationOid,
                           allocationCode,
                           purposeOid,
                           purposeNameEn,
                           purposeNameBn,
                           remarks;
	
	@JsonSerialize(using=DateSerializer.class)
	@JsonDeserialize(using=DateDeserializer.class)
	private @Expose DateTime requestedOn, decisionOn, allocationDate;

	private @Expose List<GetByOid> detail;

	@Override
	public String toString() {
		return Constant.print(this);
	}
	
}

