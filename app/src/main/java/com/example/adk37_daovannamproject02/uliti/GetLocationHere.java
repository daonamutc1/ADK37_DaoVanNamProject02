package com.example.adk37_daovannamproject02.uliti;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;

public class GetLocationHere {
    //Trả về 1 mảng có 2 phần tử: 0 là Latitude, 1 là Longitude
    //Hứng bằng 1 mảng arraylist, dùng lệnh addAll
    private static final int REQUEST_LOCATION = 1;

    public static ArrayList<Double> loadGPS(final Context context, String message) {

        ArrayList<Double> locations = new ArrayList<>();
        LocationManager locationManager;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        //Kiểm tra quyền
        while (!checkLOCATIONlPermission(context)) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        //Nếu không có GPS hiển thị thông báo bật GPS
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, final int id) {
                            context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                            System.exit(0);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, final int id) {
                            dialog.cancel();
                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();
            return null;
            //Nếu có GPS thì lấy tọa độ
        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            //Kiểm tra quyền
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                    (context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

            } else {
                Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                Location location2 = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

                if (location != null) {
                    double latti = location.getLatitude();
                    double longi = location.getLongitude();
                    locations.add(latti);
                    locations.add(longi);
                } else if (location1 != null) {
                    double latti = location1.getLatitude();
                    double longi = location1.getLongitude();
                    locations.add(latti);
                    locations.add(longi);
                } else if (location2 != null) {
                    double latti = location2.getLatitude();
                    double longi = location2.getLongitude();
                    locations.add(latti);
                    locations.add(longi);
                } else {
                    return null;
                }
            }
            return locations;
        } else {
            return null;
        }
    }

    public static boolean checkLOCATIONlPermission(Context context) {
        String permission = android.Manifest.permission.ACCESS_FINE_LOCATION;
        int res = context.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }
}
