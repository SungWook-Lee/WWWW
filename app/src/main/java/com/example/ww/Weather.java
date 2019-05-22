package com.example.ww;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Weather {

    @SerializedName("minutely")
    public List<Minutely> minutely = new ArrayList<>();
    public List<Minutely> getMinutely(){return minutely;}

    @SerializedName("forecast3days")
    public List<Forecast3days> forecast3days = new ArrayList<>();
    public List<Forecast3days> getForecast3days(){return forecast3days;}

    @SerializedName("forecast6days")
    public List<Forecast6days> forecast6days = new ArrayList<>();
    public List<Forecast6days> getForecast6days(){return forecast6days;}

}
