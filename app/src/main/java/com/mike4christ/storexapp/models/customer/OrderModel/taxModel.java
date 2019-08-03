package com.mike4christ.storexapp.models.customer.OrderModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class taxModel implements Serializable
{

    @SerializedName("tax_id")
    @Expose
    private Integer taxId;
    @SerializedName("tax_type")
    @Expose
    private String taxType;
    @SerializedName("tax_percentage")
    @Expose
    private String taxPercentage;


    public Integer getTaxId() {
        return taxId;
    }

    public void setTaxId(Integer taxId) {
        this.taxId = taxId;
    }

    public String getTaxType() {
        return taxType;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

    public String getTaxPercentage() {
        return taxPercentage;
    }

    public void setTaxPercentage(String taxPercentage) {
        this.taxPercentage = taxPercentage;
    }

}