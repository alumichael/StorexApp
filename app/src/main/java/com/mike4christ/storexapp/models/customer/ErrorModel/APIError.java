package com.mike4christ.storexapp.models.customer.ErrorModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class APIError implements Serializable
{

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("field")
    @Expose
    private String field;


    public String getCode() {
        return code;
    }


    public String getMessage() {
        return message;
    }


    public String getField() {
        return field;
    }


}
