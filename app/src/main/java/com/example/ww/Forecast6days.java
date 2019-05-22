package com.example.ww;

import com.google.gson.annotations.SerializedName;

public class Forecast6days {

    @SerializedName("sky")
    Sky sky;
    @SerializedName("temperature")
    Temperature temperature;
    @SerializedName("timeRelease")
    String timeRelease;

    public String getTimeRelease() {
        return timeRelease;
    }

    public class Sky {

        @SerializedName("amName2day") String am2day;
        @SerializedName("pmName2day") String pm2day;
        @SerializedName("amName3day") String am3day;
        @SerializedName("pmName3day") String pm3day;
        @SerializedName("amName4day") String am4day;
        @SerializedName("pmName4day") String pm4day;
        @SerializedName("amName5day") String am5day;
        @SerializedName("pmName5day") String pm5day;
        @SerializedName("amName6day") String am6day;
        @SerializedName("pmName6day") String pm6day;
        @SerializedName("amName7day") String am7day;
        @SerializedName("pmName7day") String pm7day;
        @SerializedName("amName8day") String am8day;
        @SerializedName("pmName8day") String pm8day;
        @SerializedName("amName9day") String am9day;
        @SerializedName("pmName9day") String pm9day;
        @SerializedName("amName10day") String am10day;
        @SerializedName("pmName10day") String pm10day;


        public String getAm2day() {
            return am2day;
        }

        public void setAm2day(String am2day) {
            this.am2day = am2day;
        }

        public String getPm2day() {
            return pm2day;
        }

        public void setPm2day(String pm2day) {
            this.pm2day = pm2day;
        }

        public String getAm3day() {
            return am3day;
        }

        public void setAm3day(String am3day) {
            this.am3day = am3day;
        }

        public String getPm3day() {
            return pm3day;
        }

        public void setPm3day(String pm3day) {
            this.pm3day = pm3day;
        }

        public String getAm4day() {
            return am4day;
        }

        public void setAm4day(String am4day) {
            this.am4day = am4day;
        }

        public String getPm4day() {
            return pm4day;
        }

        public void setPm4day(String pm4day) {
            this.pm4day = pm4day;
        }

        public String getAm5day() {
            return am5day;
        }

        public void setAm5day(String am5day) {
            this.am5day = am5day;
        }

        public String getPm5day() {
            return pm5day;
        }

        public void setPm5day(String pm5day) {
            this.pm5day = pm5day;
        }

        public String getAm6day() {
            return am6day;
        }

        public void setAm6day(String am6day) {
            this.am6day = am6day;
        }

        public String getPm6day() {
            return pm6day;
        }

        public void setPm6day(String pm6day) {
            this.pm6day = pm6day;
        }

        public String getAm7day() {
            return am7day;
        }

        public void setAm7day(String am7day) {
            this.am7day = am7day;
        }

        public String getPm7day() {
            return pm7day;
        }

        public void setPm7day(String pm7day) {
            this.pm7day = pm7day;
        }

        public String getAm8day() {
            return am8day;
        }

        public void setAm8day(String am8day) {
            this.am8day = am8day;
        }

        public String getPm8day() {
            return pm8day;
        }

        public void setPm8day(String pm8day) {
            this.pm8day = pm8day;
        }

        public String getAm9day() {
            return am9day;
        }

        public void setAm9day(String am9day) {
            this.am9day = am9day;
        }

        public String getPm9day() {
            return pm9day;
        }

        public void setPm9day(String pm9day) {
            this.pm9day = pm9day;
        }

        public String getAm10day() {
            return am10day;
        }

        public void setAm10day(String am10day) {
            this.am10day = am10day;
        }

        public String getPm10day() {
            return pm10day;
        }

        public void setPm10day(String pm10day) {
            this.pm10day = pm10day;
        }
    }
    public class Temperature {

        @SerializedName("tmax2day") String tmax2day;
        @SerializedName("tmin2day") String tmin2day;
        @SerializedName("tmax3day") String tmax3day;
        @SerializedName("tmin3day") String tmin3day;
        @SerializedName("tmax4day") String tmax4day;
        @SerializedName("tmin4day") String tmin4day;
        @SerializedName("tmax5day") String tmax5day;
        @SerializedName("tmin5day") String tmin5day;
        @SerializedName("tmax6day") String tmax6day;
        @SerializedName("tmin6day") String tmin6day;
        @SerializedName("tmax7day") String tmax7day;
        @SerializedName("tmin7day") String tmin7day;
        @SerializedName("tmax8day") String tmax8day;
        @SerializedName("tmin8day") String tmin8day;
        @SerializedName("tmax9day") String tmax9day;
        @SerializedName("tmin9day") String tmin9day;
        @SerializedName("tmax10day") String tmax10day;
        @SerializedName("tmin10day") String tmin10day;

        public String getTmax2day() {
            return tmax2day;
        }

        public void setTmax2day(String tmax2day) {
            this.tmax2day = tmax2day;
        }

        public String getTmin2day() {
            return tmin2day;
        }

        public void setTmin2day(String tmin2day) {
            this.tmin2day = tmin2day;
        }

        public String getTmax3day() {
            return tmax3day;
        }

        public void setTmax3day(String tmax3day) {
            this.tmax3day = tmax3day;
        }

        public String getTmin3day() {
            return tmin3day;
        }

        public void setTmin3day(String tmin3day) {
            this.tmin3day = tmin3day;
        }

        public String getTmax4day() {
            return tmax4day;
        }

        public void setTmax4day(String tmax4day) {
            this.tmax4day = tmax4day;
        }

        public String getTmin4day() {
            return tmin4day;
        }

        public void setTmin4day(String tmin4day) {
            this.tmin4day = tmin4day;
        }

        public String getTmax5day() {
            return tmax5day;
        }

        public void setTmax5day(String tmax5day) {
            this.tmax5day = tmax5day;
        }

        public String getTmin5day() {
            return tmin5day;
        }

        public void setTmin5day(String tmin5day) {
            this.tmin5day = tmin5day;
        }

        public String getTmax6day() {
            return tmax6day;
        }

        public void setTmax6day(String tmax6day) {
            this.tmax6day = tmax6day;
        }

        public String getTmin6day() {
            return tmin6day;
        }

        public void setTmin6day(String tmin6day) {
            this.tmin6day = tmin6day;
        }

        public String getTmax7day() {
            return tmax7day;
        }

        public void setTmax7day(String tmax7day) {
            this.tmax7day = tmax7day;
        }

        public String getTmin7day() {
            return tmin7day;
        }

        public void setTmin7day(String tmin7day) {
            this.tmin7day = tmin7day;
        }

        public String getTmax8day() {
            return tmax8day;
        }

        public void setTmax8day(String tmax8day) {
            this.tmax8day = tmax8day;
        }

        public String getTmin8day() {
            return tmin8day;
        }

        public void setTmin8day(String tmin8day) {
            this.tmin8day = tmin8day;
        }

        public String getTmax9day() {
            return tmax9day;
        }

        public void setTmax9day(String tmax9day) {
            this.tmax9day = tmax9day;
        }

        public String getTmin9day() {
            return tmin9day;
        }

        public void setTmin9day(String tmin9day) {
            this.tmin9day = tmin9day;
        }

        public String getTmax10day() {
            return tmax10day;
        }

        public void setTmax10day(String tmax10day) {
            this.tmax10day = tmax10day;
        }

        public String getTmin10day() {
            return tmin10day;
        }

        public void setTmin10day(String tmin10day) {
            this.tmin10day = tmin10day;
        }
    }

    public Sky getSky() {
        return sky;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setSky(Sky sky) {
        this.sky = sky;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }
}
