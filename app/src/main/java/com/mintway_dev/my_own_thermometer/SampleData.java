package com.mintway_dev.my_own_thermometer;

public class SampleData {
    private String temperature;
    private String date;

    public SampleData(String temperature, String date){
        this.temperature = temperature;
        this.date = date;
    }


    public String getTemperature()
    {
        return this.temperature;
    }
    public String getDate()
    {
        return this.date;
    }
}