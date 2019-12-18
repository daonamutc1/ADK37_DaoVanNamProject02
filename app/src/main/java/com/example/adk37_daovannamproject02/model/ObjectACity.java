package com.example.adk37_daovannamproject02.model;


import java.io.Serializable;
import java.util.ArrayList;

public class ObjectACity implements Serializable {
    Double lat;
    Double lon;
    String nameCity;
    String daynow;
    String iconnow;
    String Statusnow;
    public Double Teampnow;
    Double TempMaxToDay;
    Double TempMinToDay;
    Double Humiditynow;
    ArrayList<Hourly> hourlies;
    ArrayList<Daily> dailies;
    String unit;
    String timeupdate;
    int ID;


    public ObjectACity() {
    }

    public ObjectACity(String nameCity, String daynow, String iconnow, String statusnow, Double teampnow, Double tempMaxToDay,
                       Double tempMinToDay, Double humiditynow, ArrayList<Hourly> hourlies, ArrayList<Daily> dailies, String unit, String timeupdate, int ID, double lat, double lon) {
        this.nameCity = nameCity;
        this.daynow = daynow;
        this.iconnow = iconnow;
        Statusnow = statusnow;
        Teampnow = teampnow;
        TempMaxToDay = tempMaxToDay;
        TempMinToDay = tempMinToDay;
        Humiditynow = humiditynow;
        this.hourlies = hourlies;
        this.dailies = dailies;
        this.unit = unit;
        this.timeupdate = timeupdate;
        this.ID=ID;
        this.lat = lat;
        this.lon = lon;
    }

    public String getNameCity() {
        return nameCity;
    }

    public void setNameCity(String nameCity) {
        this.nameCity = nameCity;
    }

    public String getDaynow() {
        return daynow;
    }

    public void setDaynow(String daynow) {
        this.daynow = daynow;
    }

    public String getIconnow() {
        return iconnow;
    }

    public void setIconnow(String iconnow) {
        this.iconnow = iconnow;
    }

    public String getStatusnow() {
        return Statusnow;
    }

    public void setStatusnow(String statusnow) {
        Statusnow = statusnow;
    }

    public Double getTeampnow() {
        return Teampnow;
    }

    public void setTeampnow(Double teampnow) {
        Teampnow = teampnow;
    }

    public Double getTempMaxToDay() {
        return TempMaxToDay;
    }

    public void setTempMaxToDay(Double tempMaxToDay) {
        TempMaxToDay = tempMaxToDay;
    }

    public Double getTempMinToDay() {
        return TempMinToDay;
    }

    public void setTempMinToDay(Double tempMinToDay) {
        TempMinToDay = tempMinToDay;
    }

    public Double getHumiditynow() {
        return Humiditynow;
    }

    public void setHumiditynow(Double humiditynow) {
        Humiditynow = humiditynow;
    }

    public ArrayList<Hourly> getHourlies() {
        return hourlies;
    }

    public void setHourlies(ArrayList<Hourly> hourlies) {
        this.hourlies = hourlies;
    }

    public ArrayList<Daily> getDailies() {
        return dailies;
    }

    public void setDailies(ArrayList<Daily> dailies) {
        this.dailies = dailies;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getTimeupdate() {
        return timeupdate;
    }

    public void setTimeupdate(String timeupdate) {
        this.timeupdate = timeupdate;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

}
