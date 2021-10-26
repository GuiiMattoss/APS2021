package com.example.cronosappaps.Model;

public class Main {

    private double temp;
    private double pressuro;
    private int humidity;
    private double temp_min;
    private double temp_max;

    public Main(double temp, double pressuro, int humidity, double temp_min, double temp_max) {
        this.temp = temp;
        this.pressuro = pressuro;
        this.humidity = humidity;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getPressuro() {
        return pressuro;
    }

    public void setPressuro(double pressuro) {
        this.pressuro = pressuro;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(double temp_min) {
        this.temp_min = temp_min;
    }

    public double getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(double temp_max) {
        this.temp_max = temp_max;
    }
}
