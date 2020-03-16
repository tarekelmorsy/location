
package com.example.gpslocation.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultSupportwdLocation {

    @SerializedName("basket_id")
    @Expose
    private Integer basketId;
    @SerializedName("address_title")
    @Expose
    private String addressTitle;
    @SerializedName("address_details")
    @Expose
    private Object addressDetails;
    @SerializedName("full_address")
    @Expose
    private String fullAddress;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("type_label")
    @Expose
    private String typeLabel;
    @SerializedName("property_number")
    @Expose
    private Object propertyNumber;
    @SerializedName("floor_number")
    @Expose
    private Object floorNumber;
    @SerializedName("default")
    @Expose
    private Boolean _default;
    @SerializedName("business_type_identifier")
    @Expose
    private Object businessTypeIdentifier;
    @SerializedName("business_type_label")
    @Expose
    private Object businessTypeLabel;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("image")
    @Expose
    private Object image;
    @SerializedName("vat")
    @Expose
    private Double vat;
    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("lng")
    @Expose
    private Double lng;
    @SerializedName("delivery_fee")
    @Expose
    private Double deliveryFee;
    @SerializedName("limit_value")
    @Expose
    private Integer limitValue;
    @SerializedName("free_shipping_threshold")
    @Expose
    private Double freeShippingThreshold;

    public Integer getBasketId() {
        return basketId;
    }

    public void setBasketId(Integer basketId) {
        this.basketId = basketId;
    }

    public String getAddressTitle() {
        return addressTitle;
    }

    public void setAddressTitle(String addressTitle) {
        this.addressTitle = addressTitle;
    }

    public Object getAddressDetails() {
        return addressDetails;
    }

    public void setAddressDetails(Object addressDetails) {
        this.addressDetails = addressDetails;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
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

    public Object getPropertyNumber() {
        return propertyNumber;
    }

    public void setPropertyNumber(Object propertyNumber) {
        this.propertyNumber = propertyNumber;
    }

    public Object getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(Object floorNumber) {
        this.floorNumber = floorNumber;
    }

    public Boolean getDefault() {
        return _default;
    }

    public void setDefault(Boolean _default) {
        this._default = _default;
    }

    public Object getBusinessTypeIdentifier() {
        return businessTypeIdentifier;
    }

    public void setBusinessTypeIdentifier(Object businessTypeIdentifier) {
        this.businessTypeIdentifier = businessTypeIdentifier;
    }

    public Object getBusinessTypeLabel() {
        return businessTypeLabel;
    }

    public void setBusinessTypeLabel(Object businessTypeLabel) {
        this.businessTypeLabel = businessTypeLabel;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Object getImage() {
        return image;
    }

    public void setImage(Object image) {
        this.image = image;
    }

    public Double getVat() {
        return vat;
    }

    public void setVat(Double vat) {
        this.vat = vat;
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

    public Double getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(Double deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public Integer getLimitValue() {
        return limitValue;
    }

    public void setLimitValue(Integer limitValue) {
        this.limitValue = limitValue;
    }

    public Double getFreeShippingThreshold() {
        return freeShippingThreshold;
    }

    public void setFreeShippingThreshold(Double freeShippingThreshold) {
        this.freeShippingThreshold = freeShippingThreshold;
    }


}
