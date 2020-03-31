
package com.example.gpslocation.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CodeModel {

    @SerializedName("result")
    @Expose
    private ResultCode resultCode;
    @SerializedName("status")
    @Expose
    private Boolean status;

    public ResultCode getResultCode() {
        return resultCode;
    }

    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

}
