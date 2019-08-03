package com.mike4christ.storexapp.models.customer.OrderModel;

public class PutOrderAddress {
   String address_1;
   String address_2;
   String city;
   String region;
   String postal_code;
   String country;
   int shipping_region_id;

    public PutOrderAddress(String address_1, String address_2, String city, String region, String postal_code, String country, int shipping_region_id) {
        this.address_1 = address_1;
        this.address_2 = address_2;
        this.city = city;
        this.region = region;
        this.postal_code = postal_code;
        this.country = country;
        this.shipping_region_id = shipping_region_id;
    }

}
