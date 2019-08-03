package com.mike4christ.storexapp.models.customer.OrderModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetOrderAddress implements Serializable
{

    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("credit_card")
    @Expose
    private Object creditCard;
    @SerializedName("address_1")
    @Expose
    private String address1;
    @SerializedName("address_2")
    @Expose
    private String address2;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("region")
    @Expose
    private String region;
    @SerializedName("postal_code")
    @Expose
    private String postalCode;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("shipping_region_id")
    @Expose
    private Integer shippingRegionId;
    @SerializedName("day_phone")
    @Expose
    private Object dayPhone;
    @SerializedName("eve_phone")
    @Expose
    private Object evePhone;
    @SerializedName("mob_phone")
    @Expose
    private Object mobPhone;


    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Object getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(Object creditCard) {
        this.creditCard = creditCard;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getShippingRegionId() {
        return shippingRegionId;
    }

    public void setShippingRegionId(Integer shippingRegionId) {
        this.shippingRegionId = shippingRegionId;
    }

    public Object getDayPhone() {
        return dayPhone;
    }

    public void setDayPhone(Object dayPhone) {
        this.dayPhone = dayPhone;
    }

    public Object getEvePhone() {
        return evePhone;
    }

    public void setEvePhone(Object evePhone) {
        this.evePhone = evePhone;
    }

    public Object getMobPhone() {
        return mobPhone;
    }

    public void setMobPhone(Object mobPhone) {
        this.mobPhone = mobPhone;
    }

}