package com.cokreates.grp.model.file;

import com.cokreates.grp.util.constant.Constant;
import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FileResponseBody {

    private @Expose String fileOid, requestOid, fileName, fileDownloadUri, fileType, createdBy, description;
    private @Expose int size;
    
    @Override
    public String toString() {
    	return Constant.print(this);
    }

}
