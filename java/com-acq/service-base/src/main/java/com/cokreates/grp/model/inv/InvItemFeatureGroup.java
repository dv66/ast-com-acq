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
public class InvItemFeatureGroup {

	@JsonProperty("id")
    private  @Expose String id;
    
    @JsonProperty("createdBy")
    private @Expose String createdBy;
    
    @JsonProperty("employeeId")
    private @Expose String employeeId;
    
    @JsonProperty("name")
    private @Expose String name;
    
    @Override
    public String toString() {
        return Constant.print(this);
    }
}
