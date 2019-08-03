package com.mike4christ.storexapp.models.customer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Attribute implements Serializable {

    @SerializedName("attribute_value_id")
    @Expose
    private Integer attributeValueId;
    @SerializedName("value")
    @Expose
    private String value;


    public Integer getAttributeValueId() {
        return attributeValueId;
    }



    public String getValue() {
        return value;
    }



}