package com.mike4christ.storexapp.models.customer.CartModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CartId implements Serializable
{

    @SerializedName("cart_id")
    @Expose
    private String cartId;

    public CartId(String cartId) {
        super();
        this.cartId = cartId;
    }

    public String getCartId() {
        return cartId;
    }


}