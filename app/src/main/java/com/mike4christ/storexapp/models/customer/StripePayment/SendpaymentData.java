package com.mike4christ.storexapp.models.customer.StripePayment;

public class SendpaymentData {


    String stripeToken;
    int order_id;
    String description;
    int amount;
    String currency;

    public SendpaymentData(String stripeToken, int order_id, String description, int amount, String currency) {
        this.stripeToken = stripeToken;
        this.order_id = order_id;
        this.description = description;
        this.amount = amount;
        this.currency = currency;
    }
}
