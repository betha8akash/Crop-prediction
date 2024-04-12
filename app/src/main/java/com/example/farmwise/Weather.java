package com.example.farmwise;

public class Weather {
    private String date;
    private String time;
    private String description;
    private double temperature;
    private int humidity;

    public Weather(String date, String time, String description, double temperature, int humidity) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.temperature = temperature;
        this.humidity = humidity;
    }

    // getters and setters for each field
}
