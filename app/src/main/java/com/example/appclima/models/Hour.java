package com.example.appclima.models;

import com.google.gson.annotations.SerializedName;

public class Hour {

    @SerializedName("time")
    private String time;
    @SerializedName("temp_c")
    private double temp_c;
    @SerializedName("condition")
    private Condition condition;

    public Hour(String time, double temp_c, Condition condition) {
        this.time = time;
        this.temp_c = temp_c;
        this.condition = condition;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getTemp_c() {
        return temp_c;
    }

    public void setTemp_c(double temp_c) {
        this.temp_c = temp_c;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }
}
