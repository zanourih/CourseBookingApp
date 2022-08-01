package com.example.coursebookingapp;

import java.io.Serializable;
import java.sql.Time;

public class Lecture implements Serializable {
    String day;
    long startTime, endTime, time;


    public Lecture(){}

    public Lecture(String day, long startTime, long endTime){
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }


    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public int getHours(long time){
        //int hour = (int) Math.floor(time/10000);
        int hour = (int) Math.floor(time/100);
        return hour;
    }
    public int getMinutes(long time){
        //int hour = (int) Math.floor(time/10000);
        //int minutes = (int) (time-(hour*10000))/100;
        int hour = (int) Math.floor(time/100);
        int minutes = (int) time-(hour*100);
        return minutes;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }


    public String toString(){
        // format = DAY HH:MM - HH:MM
        String starthour = String.valueOf(getHours(startTime));
        String startminutes = String.valueOf(getMinutes(startTime));
        String endhour = String.valueOf(getHours(endTime));
        String endminutes = String.valueOf(getMinutes(endTime));

        return day + " " + starthour + ":" + startminutes + " - " + endhour + ":" + endminutes;
    }


}
