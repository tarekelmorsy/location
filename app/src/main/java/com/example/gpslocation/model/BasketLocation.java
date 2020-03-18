
package com.example.gpslocation.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BasketLocation {

    @SerializedName("result")
    @Expose
    private ResultSupportwdLocation result;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Boolean status;

    public ResultSupportwdLocation getResult() {
        return result;
    }

    public void setResult(ResultSupportwdLocation result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }


}
