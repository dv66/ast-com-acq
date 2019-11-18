package com.cokreates.grp.model.inv;

import java.util.List;

import com.cokreates.grp.util.constant.Constant;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvItemSetup {
	
	@JsonProperty("id")
    private  @Expose String id;
    
    @JsonProperty("category")
    private @Expose InvItemCategory category;
    
    @JsonProperty("catId")
    private @Expose String catId;
    
    @JsonProperty("uom")
    private  @Expose InvUom uom;
    
    @JsonProperty("unitId")
    private @Expose String unitId;
    
    @JsonProperty("itemName")
    private @Expose String itemName;
    
    @JsonProperty("bnItemName")
    private  @Expose String bnItemName;
    
    @JsonProperty("itemCode")
    private @Expose String itemCode;
    
    @JsonProperty("itemSetupType")
    private @Expose int itemSetupType;
    
    @JsonProperty("depreciationRate")
    private @Expose double depreciationRate;
    
    @JsonProperty("yearlyMaintananceCost")
    private  @Expose double yearlyMaintananceCost;
    
    @JsonProperty("hsCode")
    private @Expose String hsCode;
    
    @JsonProperty("subLedgerAccountCode")
    private @Expose int subLedgerAccountCode;
    
    @JsonProperty("maintainAs")
    private  @Expose String maintainAs;
    
    @JsonProperty("storingTemp")
    private @Expose Double storingTemp;
    
    @JsonProperty("storingTempUnit")
    private @Expose String storingTempUnit;
    
    @JsonProperty("lifeTime")
    private @Expose Double lifeTime;
    
    @JsonProperty("lifeTimeUnit")
    private @Expose String lifeTimeUnit;
    
    @JsonProperty("itemStatus")
    private  @Expose boolean itemStatus;
    
    @JsonProperty("traditional")
    private @Expose boolean traditional;
    
    @JsonProperty("itemFeatures")
    private @Expose List<InvItemFeatures> itemFeatures;
    
    @Override
    public String toString() {
        return Constant.print(this);
    }
}
