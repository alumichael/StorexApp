package com.mike4christ.storexapp.models.customer.OrderModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetOrderId implements Serializable
{

    @SerializedName("orderId")
    @Expose
    private Integer orderId;


    public Integer getOrderId() {
        return orderId;
    }


}