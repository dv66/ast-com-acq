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
public class GetByOidResponseBody {
	
	private @Expose String code, vendorOid, vendorNameEn, vendorNameBn, receivedBy,receivedByBn, contractNo, procurementMethod,
	receivedByEn, status, description, qcBy, qcByEn, qcByBn, assetAddedByEn, assetAddedByBn, assetAddedBy, chalanNo, workorderNo,
	workOrderOid, decision_on_qc_note, decisionOnQcBy, decisionOnQcByBn, decisionOnQcByEn, qcReportPath;
	
	@JsonSerialize(using=DateSerializer.class)
	@JsonDeserialize(using=DateDeserializer.class)
	private @Expose DateTime receivedAt, qcOn, assetAddedOn, chalanDate, workorderDate, contractSigningDate, decisionOnQcDate;

	private @Expose List<GetByOid> detail;

	@Override
	public String toString() {
		return Constant.print(this);
	}
	
}

