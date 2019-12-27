package com.example.adk37_daovannamproject02.view.main;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.RequiresApi;

import com.example.adk37_daovannamproject02.R;
import com.example.adk37_daovannamproject02.model.Daily;
import com.example.adk37_daovannamproject02.model.Hourly;
import com.example.adk37_daovannamproject02.model.ObjectACity;
import com.example.adk37_daovannamproject02.uliti.Defile;
import com.example.adk37_daovannamproject02.uliti.Defile_method;
import com.example.adk37_daovannamproject02.uliti.GetLocationHere;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class PresenterMain {
    InterfaceMain weather;
    Context context;
    public ArrayList<ObjectACity> objectACityfull = new ArrayList<>();
    public ArrayList<Integer> arrayID = new ArrayList<>();
    public ArrayList<String> saveNameCity = new ArrayList<>();
    StringBuilder strJsonFiveDay = new StringBuilder();
    StringBuilder strJsonCurrent = new StringBuilder();
    Double CorFDo = 274.15;
    StringBuilder CorFStr = new StringBuilder("°C");
    int saveID;
    public ProgressBar progressBar;
    Double lon, lat;
    boolean GPSpre = false;
    StringBuilder strFiveDay = new StringBuilder();
    StringBuilder strCurrent = new StringBuilder();
    public PresenterMain(InterfaceMain weather, Context context) {
        this.weather = weather;
        this.context = context;
    }


    public void loadFull(ArrayList<String> nameCity) {
        for (int n = 0; n < nameCity.size(); n++) {
            loadByCityName(nameCity.get(n), CorFDo, true);
        }
    }

    public void loadByLoacation(double unit) {
        if (GetLocationHere.loadGPS(context, context.getResources().getString(R.string.messGPS)) != null) {
            GPSpre = true;
            MainActivity.GPS = true;
            ArrayList<Double> locations = new ArrayList<>();
            locations.addAll(GetLocationHere.loadGPS(context, context.getResources().getString(R.string.messGPS)));
            lat = locations.get(0);
            lon = locations.get(1);
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
            //Vị trí hiện tại thì k lưu
            saveID = 0;
            new getAPI().execute(strFiveDay.toString(), strCurrent.toString());
        } else {
            MainActivity.GPS = false;
        }
    }

    public void loadByCityName(String namecity, double unit, boolean save) {
        GPSpre = false;
        //Hiện tại
        strCurrent.delete(0, strFiveDay.length());
        strCurrent.append(Defile.URL_HOME);
        strCurrent.append(Defile.URL_CURRENT);
        strCurrent.append(Defile.URL_CURRENTBYNAME);
        strCurrent.append(namecity);
        strCurrent.append(Defile.APPID);
        strCurrent.append(Defile.KEY);
        //5 ngày
        strFiveDay.delete(0, strFiveDay.length());
        strFiveDay.append(Defile.URL_HOME);
        strFiveDay.append(Defile.URL_3HOURS);
        strFiveDay.append(Defile.URL_3HOURSBYNAME);
        strFiveDay.append(namecity);
        strFiveDay.append(Defile.APPID);
        strFiveDay.append(Defile.KEY);
        if (unit == 274.15) {
            CorFDo = 274.15;
            CorFStr.delete(0, CorFStr.length());
            CorFStr.append("°C");
        } else {
            CorFDo = 0.0;
            CorFStr.delete(0, CorFStr.length());
            CorFStr.append("°F");
        }
        if (save) {
            saveID = 1;
        } else {
            saveID = 0;
        }
        new getAPI().execute(strFiveDay.toString(), strCurrent.toString());
    }

    private class getAPI extends AsyncTask<String, Void, String> {
        ObjectACity objectACity = new ObjectACity();
        ArrayList<Hourly> hourlies = new ArrayList<>();
        Hourly hourly = new Hourly();
        ArrayList<Daily> dailies = new ArrayList<>();
        Daily daily = new Daily();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            strJsonFiveDay.delete(0, strJsonFiveDay.length());
            strJsonCurrent.delete(0, strJsonCurrent.length());
            try {
                URL url = new URL(strings[0]);
                InputStreamReader inputFiveDay = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferFiveDay = new BufferedReader(inputFiveDay);
                String lineFiveDay;
                while ((lineFiveDay = bufferFiveDay.readLine()) != null) {
                    strJsonFiveDay.append(lineFiveDay);
                }
                bufferFiveDay.close();

                URL urlCurrent = new URL(strings[1]);
                InputStreamReader inputCurrent = new InputStreamReader(urlCurrent.openConnection().getInputStream());
                BufferedReader bufferCurrent = new BufferedReader(inputCurrent);
                String lineCurrent;
                while ((lineCurrent = bufferCurrent.readLine()) != null) {
                    strJsonCurrent.append(lineCurrent);
                }
                bufferCurrent.close();
                JSONObject objectCurrent = new JSONObject(strJsonCurrent.toString());
                JSONObject coord = new JSONObject(objectCurrent.getJSONObject(Defile.COORD).toString());
                objectACity.setLat(coord.getDouble(Defile.LATI));
                objectACity.setLon(coord.getDouble(Defile.LONG));
                //lấy tên thành phố

                objectACity.setNameCity("" + objectCurrent.getString(Defile.NAME));
                objectACity.setID(objectCurrent.getInt(Defile.ID));
                //Thời gian hiện tại
                long day = objectCurrent.getLong(Defile.DT) * 1000;
                //Lấy giờ, phút update
                Calendar calendar = Calendar.getInstance();
                int hours = calendar.get(Calendar.HOUR);
                int min = calendar.get(Calendar.MINUTE);
                String time = hours + ":" + min;
                objectACity.setTimeupdate(time);
                objectACity.setDaynow(Defile.simpleDateFormatfull.format(day));

                //icon chính hiện tại
                objectACity.setIconnow(objectCurrent.getJSONArray(Defile.WEATHER).getJSONObject(0).getString(Defile.ICON));
                //Trạng thái chính hiện tại
                objectACity.setStatusnow(objectCurrent.getJSONArray(Defile.WEATHER).getJSONObject(0).getString(Defile.MAIN));
                //Nhiệt độ hiện tại
                objectACity.setTeampnow(objectCurrent.getJSONObject(Defile.MAIN).getDouble(Defile.TEMP) - CorFDo);
                //5 ngày 3h
                JSONObject objectFiveday = new JSONObject(strJsonFiveDay.toString());
                JSONArray listFiveday = objectFiveday.getJSONArray(Defile.LIST);

                for (int i = 0; i < 8; i = i + 8) {
                    Double[] tempmax = new Double[8];
                    Double[] tempmin = new Double[8];
                    for (int j = 0; j < 8; j++) {
                        tempmax[j] = listFiveday.getJSONObject(j + i).getJSONObject(Defile.MAIN).getDouble(Defile.TEMPMAX);
                        tempmin[j] = listFiveday.getJSONObject(j + i).getJSONObject(Defile.MAIN).getDouble(Defile.TEMPMIN);
                    }
                    objectACity.setTempMaxToDay(Defile_method.Defile_Method_Max(tempmax) - CorFDo);
                    objectACity.setTempMinToDay(Defile_method.Defile_Method_Min(tempmin) - CorFDo);
                }
//                độ ẩm
                objectACity.setHumiditynow(listFiveday.getJSONObject(0).getJSONObject(Defile.MAIN).getDouble(Defile.HUMIDITY));

                //24 giờ tới
                for (int i = 0; i < 8; i++) {
                    hourly = new Hourly(Defile.simpleDateFormathour.format(listFiveday.getJSONObject(i).getLong(Defile.DT) * 1000)
                            , listFiveday.getJSONObject(i).getJSONArray(Defile.WEATHER).getJSONObject(0).getString(Defile.ICON)
                            , listFiveday.getJSONObject(i).getJSONObject(Defile.MAIN).getDouble(Defile.HUMIDITY)
                            , listFiveday.getJSONObject(i).getJSONObject(Defile.MAIN).getDouble(Defile.TEMP) - CorFDo);
                    hourlies.add(hourly);
                }
                objectACity.setHourlies(hourlies);

                //5 ngày gần nhất
                SimpleDateFormat simpleDateFormatday = new SimpleDateFormat("dd/MM");
                for (int i = 0; i < 33; i = i + 8) {
                    Double[] tempmax = new Double[8];
                    Double[] tempmin = new Double[8];
                    for (int j = 0; j < 8; j++) {
                        tempmax[j] = listFiveday.getJSONObject(j + i).getJSONObject(Defile.MAIN).getDouble(Defile.TEMPMAX);
                        tempmin[j] = listFiveday.getJSONObject(j + i).getJSONObject(Defile.MAIN).getDouble(Defile.TEMPMIN);
                    }
                    daily = new Daily(simpleDateFormatday.format(listFiveday.getJSONObject(i).getLong(Defile.DT) * 1000)
                            , listFiveday.getJSONObject(i).getJSONArray(Defile.WEATHER).getJSONObject(0).getString(Defile.ICON)
                            , listFiveday.getJSONObject(i).getJSONObject(Defile.MAIN).getDouble(Defile.HUMIDITY)
                            , Defile_method.Defile_Method_Max(tempmax) - CorFDo
                            , Defile_method.Defile_Method_Min(tempmin) - CorFDo);
                    dailies.add(daily);
                }
                objectACity.setDailies(dailies);
                objectACity.setUnit(CorFStr.toString());
                objectACityfull.add(objectACity);
            } catch (Exception e) {
                return context.getResources().getString(R.string.WrongL);
            }
            return strJsonFiveDay.toString();
        }

        //Xử lý dữ liệu
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s.compareToIgnoreCase(context.getResources().getString(R.string.WrongL)) == 0) {
                weather.onMessenger(context.getResources().getString(R.string.WrongL));
            } else {
                if (saveID == 1) {
                    arrayID.add(objectACity.getID());
                    weather.loadfullCity(objectACityfull);
                } else {
                    if (GPSpre) {
                        weather.loadlocation(objectACity);
                    } else {
                        int vitri = -1;
                        //Kiểm tra xem thành phố tìm kiếm có trong danh sách hay không thông qua ID
                        for (int i = 0; i < objectACityfull.size() - 1; i++) {
                            if (objectACity.getID() == objectACityfull.get(i).getID()) {
                                vitri = i;
                                objectACityfull.remove(objectACityfull.size() - 1);
                                break;
                            } else {
                                vitri = -1;
                            }
                        }
                        weather.loadACityForSearch(objectACity, vitri);
                    }
                }
                progressBar.setVisibility(View.GONE);
            }
        }
    }

    public void setUnitl(int i) {
        //Chuyển sang độ C
        if (i == 0) {
            CorFDo = 274.15;
            CorFStr.delete(0, CorFStr.length());
            CorFStr.append("°C");
            for (int x = 0; x < objectACityfull.size(); x++) {
                objectACityfull.get(x).setTeampnow(objectACityfull.get(x).Teampnow - CorFDo);
                objectACityfull.get(x).setTempMinToDay(objectACityfull.get(x).getTempMinToDay() - CorFDo);
                objectACityfull.get(x).setTempMaxToDay(objectACityfull.get(x).getTempMaxToDay() - CorFDo);
                objectACityfull.get(x).setUnit(CorFStr.toString());
                for (int y = 0; y < objectACityfull.get(x).getHourlies().size(); y++) {
                    objectACityfull.get(x).getHourlies().get(y).setTemp(objectACityfull.get(x).getHourlies().get(y).getTemp() - CorFDo);
                }
                for (int y = 0; y < objectACityfull.get(x).getDailies().size(); y++) {
                    objectACityfull.get(x).getDailies().get(y).setTempMax(objectACityfull.get(x).getDailies().get(y).getTempMax() - CorFDo);
                    objectACityfull.get(x).getDailies().get(y).setTempMin(objectACityfull.get(x).getDailies().get(y).getTempMin() - CorFDo);
                }
            }
            //Chuyển sang độ F
        } else {
            CorFDo = -274.15;
            CorFStr.delete(0, CorFStr.length());
            CorFStr.append("°F");
            for (int x = 0; x < objectACityfull.size(); x++) {
                objectACityfull.get(x).setTeampnow(objectACityfull.get(x).Teampnow - CorFDo);
                objectACityfull.get(x).setTempMinToDay(objectACityfull.get(x).getTempMinToDay() - CorFDo);
                objectACityfull.get(x).setTempMaxToDay(objectACityfull.get(x).getTempMaxToDay() - CorFDo);
                objectACityfull.get(x).setUnit(CorFStr.toString());
                for (int y = 0; y < objectACityfull.get(x).getHourlies().size(); y++) {
                    objectACityfull.get(x).getHourlies().get(y).setTemp(objectACityfull.get(x).getHourlies().get(y).getTemp() - CorFDo);
                }
                for (int y = 0; y < objectACityfull.get(x).getDailies().size(); y++) {
                    objectACityfull.get(x).getDailies().get(y).setTempMax(objectACityfull.get(x).getDailies().get(y).getTempMax() - CorFDo);
                    objectACityfull.get(x).getDailies().get(y).setTempMin(objectACityfull.get(x).getDailies().get(y).getTempMin() - CorFDo);
                }
            }
        }
    }
}
