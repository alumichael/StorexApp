package com.mike4christ.storexapp.models.customer.CartModels;

public class AddtoCart {

    String cart_id;
    Integer product_id;
    String attributes;

    public AddtoCart(String cart_id, Integer product_id, String attributes) {
        this.cart_id = cart_id;
        this.product_id = product_id;
        this.attributes = attributes;
    }
}
