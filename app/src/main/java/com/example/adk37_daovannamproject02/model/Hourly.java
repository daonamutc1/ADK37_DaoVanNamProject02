package com.example.adk37_daovannamproject02.model;

import java.io.Serializable;

public class Hourly implements Serializable {
    String hour;
    String icon;
    Double hum;
    Double Temp;
    public Hourly() {
    }

    public Hourly(String hour, String icon, Double hum, Double temp) {
        this.hour = hour;
        this.icon = icon;
        this.hum = hum;
        Temp = temp;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Double getHum() {
        return hum;
    }

    public void setHum(Double hum) {
        this.hum = hum;
    }

    public Double getTemp() {
        return Temp;
    }

    public void setTemp(Double temp) {
        Temp = temp;
    }
}
