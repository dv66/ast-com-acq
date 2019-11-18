package com.cokreates.grp.ast.comacq.tempitem.response;

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
public class GetByOidBeforeQcResponseBody {
	
	private @Expose String code, vendorOid, vendorNameEn, vendorNameBn, receivedBy, receivedByEn, receivedByBn, chalanNo, workorderNo,
	workOrderOid, status, description, contractNo, procurementMethod, officeInfo; //	decision_on_qc_note, decisionOnQcByBn, decisionOnQcByEn;
	
	@JsonSerialize(using=DateSerializer.class)
	@JsonDeserialize(using=DateDeserializer.class)
	private @Expose DateTime receivedAt, chalanDate, workorderDate, contractSigningDate; //decisionOnQcDate;

	private @Expose List<GetByOidBeforeQc> detail;

	@Override
	public String toString() {
		return Constant.print(this);
	}
	
}

