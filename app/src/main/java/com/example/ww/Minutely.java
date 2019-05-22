package com.example.ww;

import com.google.gson.annotations.SerializedName;

public class Minutely {
    int index;
    @SerializedName("sky")
     Sky sky;
    @SerializedName("rain")
    Rain rain;
    @SerializedName("temperature")
     Temperature temperature;

    public class Sky {
        @SerializedName("name") String name;

        public String getName() { return name; }
    }

    public class Rain {
        @SerializedName("rain") String sinceOntime;

        public String getSinceOntime() {
            return sinceOntime;
        }
    }

    public class Temperature {
        @SerializedName("tc") String tc;
        @SerializedName("tmax") String tmax;
        @SerializedName("tmin") String tmin;

        public String getTc() {
            return tc;
        }

        public String getTmax() {
            return tmax;
        }

        public String getTmin() {
            return tmin;
        }
    }

    public Sky getSky(){return sky;}
    public Rain getRain(){return rain;}
    public Temperature getTemperature(){return temperature;}

}
