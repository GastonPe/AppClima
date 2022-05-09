package com.example.appclima.models;

import com.google.gson.annotations.SerializedName;

public class Current {
    @SerializedName("temp_c")
    private String temp_c;
    @SerializedName("condition")
    private Condition condition;
    @SerializedName("wind_kph")
    private String wind_kph;
    @SerializedName("humidity")
    private String humidity;
    @SerializedName("cloud")
    private String cloud;

    public String getTemp_c() {
        return temp_c;
    }

    public void setTemp_c(String temp_c) {
        this.temp_c = temp_c;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public String getWind_kph() {
        return wind_kph;
    }

    public void setWind_kph(String wind_kph) {
        this.wind_kph = wind_kph;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getCloud() {
        return cloud;
    }

    public void setCloud(String cloud) {
        this.cloud = cloud;
    }
}
