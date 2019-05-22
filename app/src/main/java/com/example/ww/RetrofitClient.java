package com.example.ww;

import com.google.gson.annotations.SerializedName;

public class RetrofitClient {

    @SerializedName("result") Result result;
    @SerializedName("weather") Weather weather;

    public Result getResult() {return result;}
    public Weather getWeather() { return weather; }

}
