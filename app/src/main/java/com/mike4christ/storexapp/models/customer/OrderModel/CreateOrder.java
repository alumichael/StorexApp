package com.mike4christ.storexapp.models.customer.OrderModel;

public class CreateOrder {
    String cart_id;
    Integer shipping_id;
    Integer tax_id;

    public CreateOrder(String cart_id, Integer shipping_id, Integer tax_id) {
        this.cart_id = cart_id;
        this.shipping_id = shipping_id;
        this.tax_id = tax_id;
    }
}
