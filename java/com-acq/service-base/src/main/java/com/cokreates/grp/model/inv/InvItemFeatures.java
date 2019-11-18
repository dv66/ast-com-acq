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
public class InvItemFeatures {

	@JsonProperty("id")
    private  @Expose String id;
    
    @JsonProperty("name")
    private @Expose String name;
    
    @JsonProperty("bnName")
    private @Expose String bnName;
    
    @JsonProperty("item_feature_category_id")
    private @Expose String itemFeatureCategoryId;
    
    @JsonProperty("uom_id")
    private @Expose String uomId;
    
    @JsonProperty("item_setup_id")
    private @Expose String itemSetupId;
    
    @JsonProperty("isMandatory")
    private @Expose boolean isMandatory;

    @JsonProperty("isUnique")
    private @Expose boolean isUnique;
    
    @JsonProperty("isDeleted")
    private @Expose boolean isDeleted;
    
    @JsonProperty("itemFeatureCategory")
    private @Expose InvItemFeatureCategory itemFeatureCategory;
    
    @JsonProperty("uom")
    private @Expose InvUom uom; 
    
    @JsonProperty("values")
    private @Expose List<String> values;
    
    @Override
    public String toString() {
        return Constant.print(this);
    }
}
