
package com.example.gpslocation.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SupportwdLocation {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("result")
    @Expose
    private Result result;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }


}
