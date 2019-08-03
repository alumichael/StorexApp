package com.mike4christ.storexapp.models.customer.CartModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ShippingRegionDetail implements Serializable
{

    @SerializedName("shipping_id")
    @Expose
    private Integer shippingId;
    @SerializedName("shipping_type")
    @Expose
    private String shippingType;
    @SerializedName("shipping_cost")
    @Expose
    private String shippingCost;
    @SerializedName("shipping_region_id")
    @Expose
    private String shippingRegionId;

    public Integer getShippingId() {
        return shippingId;
    }

    public String getShippingType() {
        return shippingType;
    }

    public String getShippingCost() {
        return shippingCost;
    }

    public String getShippingRegionId() {
        return shippingRegionId;
    }
}


