package com.mike4christ.storexapp.models.customer.Product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductDetailModel implements Serializable
{

    @SerializedName("product_id")
    @Expose
    private Integer productId;

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("discounted_price")
    @Expose
    private String discountedPrice;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("image_2")
    @Expose
    private String image2;


    public Integer getProductId() {
        return productId;
    }


    public String getName() {
        return name;
    }


    public String getDescription() {
        return description;
    }


    public String getPrice() {
        return price;
    }


    public String getDiscountedPrice() {
        return discountedPrice;
    }


    public String getImage() {
        return image;
    }


    public String getImage2() {
        return image2;
    }

}