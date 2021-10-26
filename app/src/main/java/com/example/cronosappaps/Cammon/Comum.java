package com.example.cronosappaps.Cammon;

import androidx.annotation.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Comum {
    public static String API_KEY = "4ae62288d81b17d2a016c979a8489b5c";
    public static String API_LINK = "http://api.openweathermap.org/data/2.5/weather";

    @NonNull
    public static String apiRequest (String latitude, String longitude){
        StringBuilder sb = new StringBuilder(API_LINK);
        sb.append(String.format("?lat-%s&lon=%s&APPID-%s&units-metric", latitude, longitude, API_KEY));
        return  sb.toString();
    }

    public static String unixTimeStampToDateTime (double unixTimeStamp) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        date.setTime((long)unixTimeStamp*1000);
        return  dateFormat.format(date);
    }

    public static String getImagem (String icon) {
        return String.format("http://openweathermap.org/img/w/%s.png",icon);
    }

    public static String getDateNow(){
        DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
