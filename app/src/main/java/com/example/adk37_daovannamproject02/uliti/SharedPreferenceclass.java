package com.example.adk37_daovannamproject02.uliti;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;


public class SharedPreferenceclass {

    public static SharedPreferences getSharedPreferences(Context context, String fileName) {
        //Lưu dữ liệu với tên DataWeather
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences;
    }

    public static void save(Context context, String key, ArrayList<String> saveNameCity, String fileName) {
        if (key != null) {
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < saveNameCity.size(); i++) {
                jsonArray.put(saveNameCity.get(i));
            }
            //Lưu dữ liệu với tên DataWeather
            SharedPreferences.Editor editor = getSharedPreferences(context, fileName).edit();
            editor.putString(key, String.valueOf(jsonArray));
            editor.commit();
        }
    }

    public static ArrayList<String> readfile(Context context, String key, String fileName) {
        ArrayList<String> saveNameCity = new ArrayList<>();
        try {
            SharedPreferences sharedPreferences = getSharedPreferences(context, fileName);
            JSONArray jsonArrays = new JSONArray(sharedPreferences.getString(key, "Sai"));
            if (!jsonArrays.get(0).equals("Sai") || jsonArrays != null) {
                for (int i = 0; i < jsonArrays.length(); i++) {
                    saveNameCity.add(jsonArrays.get(i).toString());
                }
            }
            return saveNameCity;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
