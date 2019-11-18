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

public class InvItem {
    
    @JsonProperty("id")
    private  @Expose String id;
    
    @JsonProperty("itemSetupId")
    private @Expose String itemSetupId;
    
    @JsonProperty("itemSetupDto")
    private @Expose InvItemSetup itemSetupDto;
    
    @JsonProperty("itemCode")
    private  @Expose String itemCode;
    
    @JsonProperty("itemFeatures")
    private @Expose List<InvItemMainFeatures> itemFeatures;
    
    @JsonProperty("bnItemName")
    private @Expose String bnItemName;
    
    @JsonProperty("itemName")
    private  @Expose String itemName;
    
    @JsonProperty("bnItemFeatureDisplayName")
    private @Expose String bnItemFeatureDisplayName;
    
    @JsonProperty("itemFeatureDisplayName")
    private @Expose String itemFeatureDisplayName;
    
    @Override
    public String toString() {
        return Constant.print(this);
    }

}