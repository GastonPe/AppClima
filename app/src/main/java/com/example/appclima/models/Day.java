package com.example.appclima.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Day {

    @SerializedName("maxtemp_c")
    private double maxtemp_c;
    @SerializedName("mintemp_c")
    private double mintemp_c;
    @SerializedName("hour")
    private ArrayList<Hour> hour;
    @SerializedName("condition")
    private Condition condition;

    public double getMaxtemp_c() {
        return maxtemp_c;
    }

    public void setMaxtemp_c(double maxtemp_c) {
        this.maxtemp_c = maxtemp_c;
    }

    public double getMintemp_c() {
        return mintemp_c;
    }

    public void setMintemp_c(double mintemp_c) {
        this.mintemp_c = mintemp_c;
    }

    public ArrayList<Hour> getHour() {
        return hour;
    }

    public void setHour(ArrayList<Hour> hour) {
        this.hour = hour;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }
}
