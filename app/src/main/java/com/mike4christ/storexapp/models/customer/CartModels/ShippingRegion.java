package com.mike4christ.storexapp.models.customer.CartModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ShippingRegion implements Serializable
{

    @SerializedName("shipping_region_id")
    @Expose
    private Integer shippingRegionId;
    @SerializedName("shipping_region")
    @Expose
    private String shippingRegion;


    public Integer getShippingRegionId() {
        return shippingRegionId;
    }


    public String getShippingRegion() {
        return shippingRegion;
    }


}