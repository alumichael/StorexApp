package com.mike4christ.storexapp.models.customer.Product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


import java.io.Serializable;
import java.util.List;

public class ProductModel_Obj implements Serializable
{

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("rows")
    @Expose
    private List<ProductModel> rows ;


    public Integer getCount() {
        return count;
    }


    public List<ProductModel> getRows() {
        return rows;
    }


}