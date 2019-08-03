package com.mike4christ.storexapp.models.customer.OrderModel;

public class CreateOrder {
    String CartId;
    Integer shipping_id;
    Integer tax_id;

    public CreateOrder(String cartId, Integer shipping_id, Integer tax_id) {
        CartId = cartId;
        this.shipping_id = shipping_id;
        this.tax_id = tax_id;
    }
}
