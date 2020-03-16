
package com.example.gpslocation.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SupportwdLocationDetails {

    @SerializedName("address_title")
    @Expose
    private String addressTitle;
    @SerializedName("full_address")
    @Expose
    private String fullAddress;
    @SerializedName("default")
    @Expose
    private Boolean _default;
    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("lng")
    @Expose
    private Double lng;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("type_label")
    @Expose
    private String typeLabel;

    public SupportwdLocationDetails(String addressTitle, String fullAddress, Boolean _default, Double lat, Double lng, Integer type, String typeLabel) {
        this.addressTitle = addressTitle;
        this.fullAddress = fullAddress;
        this._default = _default;
        this.lat = lat;
        this.lng = lng;
        this.type = type;
        this.typeLabel = typeLabel;
    }

    public String getAddressTitle() {
        return addressTitle;
    }

    public void setAddressTitle(String addressTitle) {
        this.addressTitle = addressTitle;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public Boolean getDefault() {
        return _default;
    }

    public void setDefault(Boolean _default) {
        this._default = _default;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeLabel() {
        return typeLabel;
    }

    public void setTypeLabel(String typeLabel) {
        this.typeLabel = typeLabel;
    }



}
