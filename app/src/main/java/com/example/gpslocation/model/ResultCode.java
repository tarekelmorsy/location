
package com.example.gpslocation.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultCode {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("email")
    @Expose
    private Object email;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("user_phone")
    @Expose
    private String userPhone;
    @SerializedName("is_verified")
    @Expose
    private Boolean isVerified;
    @SerializedName("is_blocked")
    @Expose
    private Boolean isBlocked;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("has_favorite_location")
    @Expose
    private Boolean hasFavoriteLocation;
    @SerializedName("roles")
    @Expose
    private List<Object> roles = null;
    @SerializedName("is_staff")
    @Expose
    private Boolean isStaff;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public Boolean getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }

    public Boolean getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(Boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getHasFavoriteLocation() {
        return hasFavoriteLocation;
    }

    public void setHasFavoriteLocation(Boolean hasFavoriteLocation) {
        this.hasFavoriteLocation = hasFavoriteLocation;
    }

    public List<Object> getRoles() {
        return roles;
    }

    public void setRoles(List<Object> roles) {
        this.roles = roles;
    }

    public Boolean getIsStaff() {
        return isStaff;
    }

    public void setIsStaff(Boolean isStaff) {
        this.isStaff = isStaff;
    }

}
