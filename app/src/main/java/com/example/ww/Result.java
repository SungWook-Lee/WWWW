package com.example.ww;

import com.google.gson.annotations.SerializedName;

public class Result {
    @SerializedName("message") String message;
    @SerializedName("code") String code;

    public String getMessage() {return message;}
    public String getCode() {return code;}
}
