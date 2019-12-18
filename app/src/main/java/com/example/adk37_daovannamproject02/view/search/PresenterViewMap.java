package com.example.adk37_daovannamproject02.view.search;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.adk37_daovannamproject02.R;
import com.example.adk37_daovannamproject02.model.Daily;
import com.example.adk37_daovannamproject02.model.Hourly;
import com.example.adk37_daovannamproject02.model.ObjectACity;
import com.example.adk37_daovannamproject02.uliti.Defile;
import com.example.adk37_daovannamproject02.uliti.Defile_method;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class PresenterViewMap {
    InterfaceViewMap interfaceViewMap;
    Context context;
    Double lon, lat;
    Double CorFDo = 274.15;
    StringBuilder CorFStr = new StringBuilder("°C");
    public ProgressBar progressBar;
    ObjectACity objectACitySend = new ObjectACity();
    ArrayList<Hourly> hourlies = new ArrayList<>();
    Hourly[] hourly = {new Hourly()};
    ArrayList<Daily> dailies = new ArrayList<>();
    Daily[] daily = {new Daily()};
    public PresenterViewMap(InterfaceViewMap interfaceViewMap, Context context) {
        this.interfaceViewMap = interfaceViewMap;
        this.context = context;
    }

    public void loadBYLoacationClick(double latitude, double longitude, double unit) {
        StringBuilder strCurrent = new StringBuilder();
        lat = latitude;
        lon = longitude;
        //Hiện tại
        strCurrent.append(Defile.URL_HOME);
        strCurrent.append(Defile.URL_CURRENT);
        strCurrent.append(Defile.LAT);
        strCurrent.append(lat);
        strCurrent.append(Defile.LON);
        strCurrent.append(lon);
        strCurrent.append(Defile.APPID);
        strCurrent.append(Defile.KEY);
        if (unit == 274.15) {
            CorFDo = 274.15;
            CorFStr.delete(0, CorFStr.length());
            CorFStr.append("°C");
        } else {
            CorFDo = 0.0;
            CorFStr.delete(0, CorFStr.length());
            CorFStr.append("°F");
        }
        //Vị trí hiện tại thì k lưu
        getAPIClick(strCurrent.toString());
    }

    private void getAPIClick(String str) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET, str,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            ObjectACity objectACityCurrent = new ObjectACity();
                            JSONObject objectCurrent = new JSONObject(response);
                            JSONObject coord = new JSONObject(objectCurrent.getJSONObject(Defile.COORD).toString());
                            objectACityCurrent.setLat(coord.getDouble(Defile.LATI));
                            objectACityCurrent.setLon(coord.getDouble(Defile.LONG));
                            objectACityCurrent.setNameCity("" + objectCurrent.getString(Defile.NAME));
                            objectACityCurrent.setIconnow(objectCurrent.getJSONArray(Defile.WEATHER).getJSONObject(0).getString(Defile.ICON));
                            objectACityCurrent.setTeampnow(objectCurrent.getJSONObject(Defile.MAIN).getDouble(Defile.TEMP) - CorFDo);
                            objectACityCurrent.setUnit(CorFStr.toString());
                            interfaceViewMap.searchClick(objectACityCurrent);
                        } catch (Exception e) {
                            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public void loadBYLoacationAdd(double latitude, double longitude, double unit) {
        progressBar.setVisibility(View.VISIBLE);
        StringBuilder strFiveDay = new StringBuilder();
        StringBuilder strCurrent = new StringBuilder();
        lat = latitude;
        lon = longitude;
        //5 ngày
        strFiveDay.delete(0, strFiveDay.length());
        strFiveDay.append(Defile.URL_HOME);
        strFiveDay.append(Defile.URL_3HOURS);
        strFiveDay.append(Defile.LAT);
        strFiveDay.append(lat);
        strFiveDay.append(Defile.LON);
        strFiveDay.append(lon);
        strFiveDay.append(Defile.APPID);
        strFiveDay.append(Defile.KEY);
        //Hiện tại
        strCurrent.delete(0, strCurrent.length());
        strCurrent.append(Defile.URL_HOME);
        strCurrent.append(Defile.URL_CURRENT);
        strCurrent.append(Defile.LAT);
        strCurrent.append(lat);
        strCurrent.append(Defile.LON);
        strCurrent.append(lon);
        strCurrent.append(Defile.APPID);
        strCurrent.append(Defile.KEY);
        if (unit == 274.15) {
            CorFDo = 274.15;
            CorFStr.delete(0, CorFStr.length());
            CorFStr.append("°C");
        } else {
            CorFDo = 0.0;
            CorFStr.delete(0, CorFStr.length());
            CorFStr.append("°F");
        }
        getAPIClickadd(strFiveDay.toString(), strCurrent.toString());
    }

    private void getAPIClickadd(String strFiveDay, String strCurrent) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET, strCurrent,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject objectCurrent = new JSONObject(response);
                            JSONObject coord = new JSONObject(objectCurrent.getJSONObject(Defile.COORD).toString());
                            objectACitySend.setLat(coord.getDouble(Defile.LATI));
                            objectACitySend.setLon(coord.getDouble(Defile.LONG));
                            //lấy tên thành phố
                            objectACitySend.setNameCity("" + objectCurrent.getString(Defile.NAME));
                            objectACitySend.setID(objectCurrent.getInt(Defile.ID));
                            //Thời gian hiện tại
                            long day = objectCurrent.getLong(Defile.DT) * 1000;
                            //Lấy giờ, phút update
                            Calendar calendar = Calendar.getInstance();
                            int hours = calendar.get(Calendar.HOUR);
                            int min = calendar.get(Calendar.MINUTE);
                            String time = hours + ":" + min;
                            objectACitySend.setTimeupdate(time);
                            objectACitySend.setDaynow(Defile.simpleDateFormatfull.format(day));
                            //icon chính hiện tại
                            objectACitySend.setIconnow(objectCurrent.getJSONArray(Defile.WEATHER).getJSONObject(0).getString(Defile.ICON));
                            //Trạng thái chính hiện tại
                            objectACitySend.setStatusnow(objectCurrent.getJSONArray(Defile.WEATHER).getJSONObject(0).getString(Defile.MAIN));
                            //Nhiệt độ hiện tại
                            objectACitySend.setTeampnow(objectCurrent.getJSONObject(Defile.MAIN).getDouble(Defile.TEMP) - CorFDo);
                        } catch (Exception e) {
                            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

        StringRequest stringRequestFiveDay = new StringRequest(
                Request.Method.GET, strFiveDay,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //5 ngày 3h
                            JSONObject objectFiveday = new JSONObject(response);
                            JSONArray listFiveday = objectFiveday.getJSONArray(Defile.LIST);
                            for (int i = 0; i < 8; i = i + 8) {
                                Double[] tempmax = new Double[8];
                                Double[] tempmin = new Double[8];
                                for (int j = 0; j < 8; j++) {
                                    tempmax[j] = listFiveday.getJSONObject(j + i).getJSONObject(Defile.MAIN).getDouble(Defile.TEMPMAX);
                                    tempmin[j] = listFiveday.getJSONObject(j + i).getJSONObject(Defile.MAIN).getDouble(Defile.TEMPMIN);
                                }
                                objectACitySend.setTempMaxToDay(Defile_method.Defile_Method_Max(tempmax) - CorFDo);
                                objectACitySend.setTempMinToDay(Defile_method.Defile_Method_Min(tempmin) - CorFDo);
                            }
                            //độ ẩm
                            objectACitySend.setHumiditynow(listFiveday.getJSONObject(0).getJSONObject(Defile.MAIN).getDouble(Defile.HUMIDITY));

                            //24 giờ tới
                            for (int i = 0; i < 8; i++) {
                                hourly[0] = new Hourly(Defile.simpleDateFormathour.format(listFiveday.getJSONObject(i).getLong(Defile.DT) * 1000)
                                        , listFiveday.getJSONObject(i).getJSONArray(Defile.WEATHER).getJSONObject(0).getString(Defile.ICON)
                                        , listFiveday.getJSONObject(i).getJSONObject(Defile.MAIN).getDouble(Defile.HUMIDITY)
                                        , listFiveday.getJSONObject(i).getJSONObject(Defile.MAIN).getDouble(Defile.TEMP) - CorFDo);
                                hourlies.add(hourly[0]);
                            }
                            objectACitySend.setHourlies(hourlies);
                            //5 ngày gần nhất
                            SimpleDateFormat simpleDateFormatday = new SimpleDateFormat("dd/MM");
                            for (int i = 0; i < 33; i = i + 8) {
                                Double[] tempmax = new Double[8];
                                Double[] tempmin = new Double[8];
                                for (int j = 0; j < 8; j++) {
                                    tempmax[j] = listFiveday.getJSONObject(j + i).getJSONObject(Defile.MAIN).getDouble(Defile.TEMPMAX);
                                    tempmin[j] = listFiveday.getJSONObject(j + i).getJSONObject(Defile.MAIN).getDouble(Defile.TEMPMIN);
                                }
                                daily[0] = new Daily(simpleDateFormatday.format(listFiveday.getJSONObject(i).getLong(Defile.DT) * 1000)
                                        , listFiveday.getJSONObject(i).getJSONArray(Defile.WEATHER).getJSONObject(0).getString(Defile.ICON)
                                        , listFiveday.getJSONObject(i).getJSONObject(Defile.MAIN).getDouble(Defile.HUMIDITY)
                                        , Defile_method.Defile_Method_Max(tempmax) - CorFDo
                                        , Defile_method.Defile_Method_Min(tempmin) - CorFDo);
                                dailies.add(daily[0]);
                            }
                            objectACitySend.setDailies(dailies);
                            objectACitySend.setUnit(CorFStr.toString());
                        } catch (Exception e) {
                            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                        int vitri = -1;
                        for (int i = 0; i < ViewMap.objectViewMap.size(); i++) {
                            if (objectACitySend.getID() == ViewMap.objectViewMap.get(i).getID()) {
                                vitri = i;
                                break;
                            }
                        }
                        //Đã đủ nhưng có vấn đề gì đó ấy
                        interfaceViewMap.searchAdd(objectACitySend, vitri);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueueFiveDay = Volley.newRequestQueue(context);
        requestQueueFiveDay.add(stringRequestFiveDay);
    }

//    private class getAPIClickadd extends AsyncTask<String, Void, String> {
//        ObjectACity objectACity = new ObjectACity();
//        ArrayList<Hourly> hourlies = new ArrayList<>();
//        Hourly hourly = new Hourly();
//        ArrayList<Daily> dailies = new ArrayList<>();
//        Daily daily = new Daily();
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            progressBar.setVisibility(View.VISIBLE);
//        }
//
//        @Override
//        protected String doInBackground(String... strings) {
//            strJsonFiveDay.delete(0, strJsonFiveDay.length());
//            strJsonCurrent.delete(0, strJsonCurrent.length());
//            try {
//                URL url = new URL(strings[0]);
//                InputStreamReader inputFiveDay = new InputStreamReader(url.openConnection().getInputStream());
//                BufferedReader bufferFiveDay = new BufferedReader(inputFiveDay);
//                String lineFiveDay;
//                while ((lineFiveDay = bufferFiveDay.readLine()) != null) {
//                    strJsonFiveDay.append(lineFiveDay);
//                }
//                bufferFiveDay.close();
//
//                URL urlCurrent = new URL(strings[1]);
//                InputStreamReader inputCurrent = new InputStreamReader(urlCurrent.openConnection().getInputStream());
//                BufferedReader bufferCurrent = new BufferedReader(inputCurrent);
//                String lineCurrent;
//                while ((lineCurrent = bufferCurrent.readLine()) != null) {
//                    strJsonCurrent.append(lineCurrent);
//                }
//                bufferCurrent.close();
//                JSONObject objectCurrent = new JSONObject(strJsonCurrent.toString());
//                JSONObject coord = new JSONObject(objectCurrent.getJSONObject(Defile.COORD).toString());
//                objectACity.setLat(coord.getDouble(Defile.LATI));
//                objectACity.setLon(coord.getDouble(Defile.LONG));
//                //lấy tên thành phố
//                objectACity.setNameCity("" + objectCurrent.getString(Defile.NAME));
//                objectACity.setID(objectCurrent.getInt(Defile.ID));
//                //Thời gian hiện tại
//                long day = objectCurrent.getLong(Defile.DT) * 1000;
//                //Lấy giờ, phút update
//                Calendar calendar = Calendar.getInstance();
//                int hours = calendar.get(Calendar.HOUR);
//                int min = calendar.get(Calendar.MINUTE);
//                String time = hours + ":" + min;
//                objectACity.setTimeupdate(time);
//                objectACity.setDaynow(Defile.simpleDateFormatfull.format(day));
//                //icon chính hiện tại
//                objectACity.setIconnow(objectCurrent.getJSONArray(Defile.WEATHER).getJSONObject(0).getString(Defile.ICON));
//                //Trạng thái chính hiện tại
//                objectACity.setStatusnow(objectCurrent.getJSONArray(Defile.WEATHER).getJSONObject(0).getString(Defile.MAIN));
//                //Nhiệt độ hiện tại
//                objectACity.setTeampnow(objectCurrent.getJSONObject(Defile.MAIN).getDouble(Defile.TEMP) - CorFDo);
//                //5 ngày 3h
//                JSONObject objectFiveday = new JSONObject(strJsonFiveDay.toString());
//                JSONArray listFiveday = objectFiveday.getJSONArray(Defile.LIST);
//
//                for (int i = 0; i < 8; i = i + 8) {
//                    Double[] tempmax = new Double[8];
//                    Double[] tempmin = new Double[8];
//                    for (int j = 0; j < 8; j++) {
//                        tempmax[j] = listFiveday.getJSONObject(j + i).getJSONObject(Defile.MAIN).getDouble(Defile.TEMPMAX);
//                        tempmin[j] = listFiveday.getJSONObject(j + i).getJSONObject(Defile.MAIN).getDouble(Defile.TEMPMIN);
//                    }
//                    objectACity.setTempMaxToDay(Defile_method.Defile_Method_Max(tempmax) - CorFDo);
//                    objectACity.setTempMinToDay(Defile_method.Defile_Method_Min(tempmin) - CorFDo);
//                }
////                độ ẩm
//                objectACity.setHumiditynow(listFiveday.getJSONObject(0).getJSONObject(Defile.MAIN).getDouble(Defile.HUMIDITY));
//
//                //24 giờ tới
//                for (int i = 0; i < 8; i++) {
//                    hourly = new Hourly(Defile.simpleDateFormathour.format(listFiveday.getJSONObject(i).getLong(Defile.DT) * 1000)
//                            , listFiveday.getJSONObject(i).getJSONArray(Defile.WEATHER).getJSONObject(0).getString(Defile.ICON)
//                            , listFiveday.getJSONObject(i).getJSONObject(Defile.MAIN).getDouble(Defile.HUMIDITY)
//                            , listFiveday.getJSONObject(i).getJSONObject(Defile.MAIN).getDouble(Defile.TEMP) - CorFDo);
//                    hourlies.add(hourly);
//                }
//                objectACity.setHourlies(hourlies);
//                //5 ngày gần nhất
//                SimpleDateFormat simpleDateFormatday = new SimpleDateFormat("dd/MM");
//                for (int i = 0; i < 33; i = i + 8) {
//                    Double[] tempmax = new Double[8];
//                    Double[] tempmin = new Double[8];
//                    for (int j = 0; j < 8; j++) {
//                        tempmax[j] = listFiveday.getJSONObject(j + i).getJSONObject(Defile.MAIN).getDouble(Defile.TEMPMAX);
//                        tempmin[j] = listFiveday.getJSONObject(j + i).getJSONObject(Defile.MAIN).getDouble(Defile.TEMPMIN);
//                    }
//                    daily = new Daily(simpleDateFormatday.format(listFiveday.getJSONObject(i).getLong(Defile.DT) * 1000)
//                            , listFiveday.getJSONObject(i).getJSONArray(Defile.WEATHER).getJSONObject(0).getString(Defile.ICON)
//                            , listFiveday.getJSONObject(i).getJSONObject(Defile.MAIN).getDouble(Defile.HUMIDITY)
//                            , Defile_method.Defile_Method_Max(tempmax) - CorFDo
//                            , Defile_method.Defile_Method_Min(tempmin) - CorFDo);
//                    dailies.add(daily);
//                }
//                objectACity.setDailies(dailies);
//                objectACity.setUnit(CorFStr.toString());
//            } catch (Exception e) {
//                return context.getResources().getString(R.string.WrongL);
//            }
//            return strCurrent.toString();
//        }
//
//        //Xử lý dữ liệu
//        @RequiresApi(api = Build.VERSION_CODES.O)
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            int vitri = -1;
//            for (int i = 0; i < ViewMap.objectViewMap.size(); i++) {
//                if (objectACity.getID() == ViewMap.objectViewMap.get(i).getID()) {
//                    vitri = i;
//                    break;
//                }
//            }
//            interfaceViewMap.searchAdd(objectACity, vitri);
//            progressBar.setVisibility(View.GONE);
//        }
//    }
}