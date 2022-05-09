package com.example.appclima.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Forecastday {

    @SerializedName("date")
    private String date;
    @SerializedName("day")
    private Day day;
    @SerializedName("hour")
    private ArrayList<Hour> hour;

    public Forecastday(String date, Day day) {
        this.date = date;
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public ArrayList<Hour> getHour() {
        return hour;
    }

    public void setHour(ArrayList<Hour> hour) {
        this.hour = hour;
    }
}
