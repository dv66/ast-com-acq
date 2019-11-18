
package com.cokreates.grp.model.hrm;

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
public class EmployeeInfo {
	
	private @Expose String oid, govtId, nameEn, nameBn, officeNameEn, officeNameBn, officeUnitNameEn, officeUnitNameBn, 
	postNameEn, postNameBn, batchNameEn, batchNameBn, joinDate, gender, photoUrl, signatureUrl, phone, email;
	
	@Override
	public String toString() {
		return Constant.print(this);
	}

}