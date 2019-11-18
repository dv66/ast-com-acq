package com.cokreates.grp.model.inv;

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
public class InvItemMainFeatures {

	@JsonProperty("id")
    private  @Expose String id;
    
    @JsonProperty("itemSetupFeature")
    private @Expose InvItemFeatures itemSetupFeature;
    
    @JsonProperty("itemSetupFeatureId")
    private @Expose String itemSetupFeatureId;
    
    @JsonProperty("value")
    private @Expose String value;
    
    @JsonProperty("bnValue")
    private @Expose String bnValue;
    
    @Override
    public String toString() {
        return Constant.print(this);
    }
}
