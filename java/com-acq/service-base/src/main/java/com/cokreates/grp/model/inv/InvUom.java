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
public class InvUom {

	@JsonProperty("unitId")
    private  @Expose String unitId;
    
    @JsonProperty("uomId")
    private @Expose String uomId;
    
    @JsonProperty("bnUomId")
    private @Expose String bnUomId;
    
    @JsonProperty("unitName")
    private @Expose String unitName;
    
    @JsonProperty("uomTypeId")
    private @Expose String uomTypeId;
    
    @JsonProperty("abbreviation")
    private @Expose String abbreviation;
    
    @JsonProperty("isDeleted")
    private @Expose boolean isDeleted;
    
    @Override
    public String toString() {
        return Constant.print(this);
    }
}
