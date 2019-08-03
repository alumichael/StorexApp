package com.mike4christ.storexapp.models.customer.AppEntryModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mike4christ.storexapp.models.customer.Customer;

import java.io.Serializable;

public class LoginGetData implements Serializable {

    @SerializedName("user")
    @Expose 
    private Customer customer;
    @SerializedName("accessToken")
    @Expose
    private String accessToken;
    @SerializedName("expires_in")
    @Expose
    private String expiresIn;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }


}
