package com.example.adk37_daovannamproject02.uliti;

import java.text.SimpleDateFormat;

public class Defile {
    //Chung
    public static final String URL_HOME = "http://api.openweathermap.org/data/2.5/";
    public static final String KEY = "9f7bbc8e2c7bce17dcd9f758887603bf";
    //3h 5 ngày
    public static final String URL_3HOURS = "forecast?";
    public static final String URL_3HOURSBYNAME = "q=";
    public static final String APPID = "&APPID=";
    //Hiện tại
    public static final String URL_CURRENT = "weather?";
    public static final String URL_CURRENTBYNAME = "q=";
    //Lấy tọa độ
    public static final String LAT = "lat=";
    public static final String LON = "&lon=";
    //Key ở Json
    public static final String CITY = "city";
    public static final String NAME = "name";
    public static final String ID = "id";
    public static final String LIST = "list";
    public static final String DT = "dt";
    public static final String WEATHER = "weather";
    public static final String ICON = "icon";
    public static final String MAIN = "main";
    public static final String TEMP = "temp";
    public static final String TEMPMAX = "temp_max";
    public static final String TEMPMIN = "temp_min";
    public static final String HUMIDITY = "humidity";
    public static final String COORD = "coord";
    public static final String LATI = "lat";
    public static final String LONG = "lon";
    //Lấy icon
    public static final String URL_HOMEICON = "http://openweathermap.org/img/wn/";
    public static final String PNG = ".png";

    public static final String SAVE = "Data";
    public static final String SAVEFILENAME = "DataWeather";
    //Định dạng ngày giờ
    public static final SimpleDateFormat simpleDateFormatfull = new SimpleDateFormat("EEEE dd/MM/yyyy");
    public static final SimpleDateFormat simpleDateFormathour = new SimpleDateFormat("HH:mm");

    //Lấy vị trí hiện tại
    //3h 5 ngày
//    Link load theo toa do
//    http://api.openweathermap.org/data/2.5/forecast?lat=20.7310&lon=106.64036&APPID=9f7bbc8e2c7bce17dcd9f758887603bf
//    http://api.openweathermap.org/data/2.5/forecast?lat=20.730&lon=106.64552&APPID=9f7bbc8e2c7bce17dcd9f758887603bf

    //Hiện tại
//    http://api.openweathermap.org/data/2.5/weather?q=Hanoi&APPID=9f7bbc8e2c7bce17dcd9f758887603bf
//    http://api.openweathermap.org/data/2.5/weather?lat=20.7310&lon=106.64036&APPID=9f7bbc8e2c7bce17dcd9f758887603bf
//    http://api.openweathermap.org/data/2.5/weather?q=?London&APPID=9f7bbc8e2c7bce17dcd9f758887603bf

}
