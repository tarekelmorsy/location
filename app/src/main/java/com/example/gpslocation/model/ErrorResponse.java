package com.example.gpslocation.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ErrorResponse {



        @SerializedName("message_en")
        @Expose
        private String messageEn;
        @SerializedName("message_ar")
        @Expose
        private String messageAr;
        @SerializedName("status")
        @Expose
        private Boolean status;

        public String getMessageEn() {
            return messageEn;
        }

        public void setMessageEn(String messageEn) {
            this.messageEn = messageEn;
        }

        public String getMessageAr() {
            return messageAr;
        }

        public void setMessageAr(String messageAr) {
            this.messageAr = messageAr;
        }

        public Boolean getStatus() {
            return status;
        }

        public void setStatus(Boolean status) {
            this.status = status;


    }
}
