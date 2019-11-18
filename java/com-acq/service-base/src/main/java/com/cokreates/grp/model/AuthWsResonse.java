package com.cokreates.grp.model;

import com.cokreates.grp.util.constant.Constant;
import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthWsResonse {

    private @Expose String errorMessage, data;
    private @Expose int status;
    
    @Override
    public String toString() {
    	return Constant.print(this);
    }

}