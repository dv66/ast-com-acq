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
public class InvItemCategory {

	@JsonProperty("ownedBy")
    private  @Expose String ownedBy;
    
    @JsonProperty("createdDate")
    private @Expose String createdDate;
    
    @JsonProperty("categoryId")
    private @Expose String categoryId;
    
    @JsonProperty("parentCategoryId")
    private @Expose String parentCategoryId;
    
    @JsonProperty("catName")
    private @Expose String catName;
    
    @JsonProperty("bnCatName")
    private @Expose String bnCatName;
    
    @JsonProperty("catDescription")
    private @Expose String catDescription;

    @JsonProperty("status")
    private @Expose boolean status;
    
    @JsonProperty("accountsCategory")
    private @Expose String accountsCategory;
    
    @JsonProperty("id")
    private @Expose String id;
    
    @JsonProperty("trackable")
    private @Expose boolean trackable;
    
    @JsonProperty("deleted")
    private @Expose boolean deleted;
    
    @Override
    public String toString() {
        return Constant.print(this);
    }
}
