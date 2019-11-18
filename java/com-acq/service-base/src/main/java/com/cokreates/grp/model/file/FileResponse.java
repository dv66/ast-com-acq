package com.cokreates.grp.model.file;

import java.util.List;

import com.cokreates.grp.util.constant.Constant;
import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FileResponse {

    private @Expose String message, error, option, timestamp;
    private @Expose int status;
    private @Expose List<FileResponseBody> data;
    
    @Override
    public String toString() {
    	return Constant.print(this);
    }

}
