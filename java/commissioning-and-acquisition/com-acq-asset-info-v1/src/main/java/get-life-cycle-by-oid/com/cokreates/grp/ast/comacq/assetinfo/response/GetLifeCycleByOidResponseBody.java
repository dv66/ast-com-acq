package com.cokreates.grp.ast.comacq.assetinfo.response;



import java.util.List;

import org.joda.time.DateTime;

import com.cokreates.grp.model.inv.InvItem;
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
public class GetLifeCycleByOidResponseBody {
	
	private @Expose String code, description, status, createdBy, updatedBy, temporaryItemCode, workOrderNo, chalanNo,
	                        decisionOnQcBy,receivedBy, taggedBy, vendorNameEn, vendorNameBn, tempCreatedBy, tempUpdatedBy,
	                        itemNameEn, itemNameBn, qcBy, qcByEn, qcByBn, assetAddedBy, assetAddedByEn, assetAddedByBn,
	                        procurementMethod, contractNo, decisionOnQcByNameEn, decisionOnQcByNameBn, receivedByEn,
	                        receivedByBn, taggedByEn, taggedByBn, itemOid;
	
	@JsonSerialize(using=DateSerializer.class)
	@JsonDeserialize(using=DateDeserializer.class)
	private @Expose DateTime createdOn, updatedOn, qcOn, assetAddedOn, receivedAt, chalanDate, workOrderDate,
	                         tempCreatedOn, tempUpdatedOn, taggedOn, contractSigningDate, decisionOnQcDate;

	private @Expose List<AssetAllocationDetails> allocationDetail;
	private @Expose List<ExpertDecisionDetail> maintenanceDetail;
	private @Expose List<ExpertDecisionDetail> disposalDetail;
	private @Expose List<ExpertDecisionDetail> deniedOrWaitigDetail;
	
	public void itemValuesSetter(InvItem item) {
    	this.itemNameEn = item.getItemName();
    	this.itemNameBn = item.getBnItemName();
    }
	
	@Override
	public String toString() {
		return Constant.print(this);
	}
	
}

