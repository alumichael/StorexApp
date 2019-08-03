package com.mike4christ.storexapp.models.customer.OrderModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetOrderList implements Serializable
{

    @SerializedName("order_id")
    @Expose
    private Integer orderId;
    @SerializedName("total_amount")
    @Expose
    private String totalAmount;
    @SerializedName("created_on")
    @Expose
    private String createdOn;
    @SerializedName("shipped_on")
    @Expose
    private Object shippedOn;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("name")
    @Expose
    private String name;


    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public Object getShippedOn() {
        return shippedOn;
    }

    public void setShippedOn(Object shippedOn) {
        this.shippedOn = shippedOn;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}