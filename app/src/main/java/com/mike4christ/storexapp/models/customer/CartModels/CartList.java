package com.mike4christ.storexapp.models.customer.CartModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CartList implements Serializable
{

    @SerializedName("item_id")
    @Expose
    private Integer itemId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("attributes")
    @Expose
    private String attributes;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("subtotal")
    @Expose
    private String subtotal;


    public Integer getItemId() {
        return itemId;
    }


    public String getName() {
        return name;
    }

    public String getAttributes() {
        return attributes;
    }

    public String getPrice() {
        return price;
    }


    public Integer getQuantity() {
        return quantity;
    }


    public String getSubtotal() {
        return subtotal;
    }


}
