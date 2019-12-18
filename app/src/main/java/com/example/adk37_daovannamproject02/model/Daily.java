package com.example.adk37_daovannamproject02.model;

import java.io.Serializable;

public class Daily implements Serializable {
    String day;
    String icon;
    Double Hum;
    Double TempMax;
    Double TempMin;

    public Daily() {
    }

    public Daily(String day, String icon, Double hum, Double tempMax, Double tempMin) {
        this.day = day;
        this.icon = icon;
        Hum = hum;
        TempMax = tempMax;
        TempMin = tempMin;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Double getHum() {
        return Hum;
    }

    public void setHum(Double hum) {
        Hum = hum;
    }

    public Double getTempMax() {
        return TempMax;
    }

    public void setTempMax(Double tempMax) {
        TempMax = tempMax;
    }

    public Double getTempMin() {
        return TempMin;
    }

    public void setTempMin(Double tempMin) {
        TempMin = tempMin;
    }
}
