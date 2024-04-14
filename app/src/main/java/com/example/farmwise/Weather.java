package com.example.farmwise;

import java.util.HashMap;
import java.util.Map;

public class Weather {
    private String date;
    private String time;
    private String description;
    private double temperature;
    private int humidity;
    private double windSpeed;

    public Weather(String date, String time, String description, double temperature, int humidity, double windSpeed) {
        this.date = date;
        this.time = time;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.temperature = temperature;
        this.description = description;
    }
    public String getDay() {
        return date;
    }

    public int getHumidityValue() {
        return humidity;
    }

    public double getWindSpeedValue() {
        return windSpeed;
    }

    public double getTemperatureValue() {
        return temperature;
    }

    public String getDescriptionValue() {
        return description;
    }

}
