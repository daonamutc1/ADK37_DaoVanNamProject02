package com.example.adk37_daovannamproject02.SaveData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.adk37_daovannamproject02.model.Daily;
import com.example.adk37_daovannamproject02.model.Hourly;
import com.example.adk37_daovannamproject02.model.ObjectACity;
import com.example.adk37_daovannamproject02.uliti.Defile;

import java.util.ArrayList;

public class SQLHelper extends SQLiteOpenHelper {

    SQLiteDatabase sqLiteDatabase;
    ContentValues contentValues;
    Cursor cursor;


    public SQLHelper(Context context) {
        super(context, Defile.DB_NAME, null, Defile.DB_VERSION);
    }

    //    NOT NULL PRIMARY KEY AUTOINCREMENT: Tự động điền id, nhược điểm không xóa được id cũ
    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryCreaTable = "CREATE TABLE Product ( " +
                "id INTEGER," +
                "lat REAL," +
                "lon REAL," +
                "nameCity Text," +
                "dayNow Text," +
                "iconNow Text," +
                "StatusNow Text," +
                "TeampNow REAL," +
                "TempMaxToDay REAL," +
                "TempMinToDay REAL," +
                "HumidityNow REAL," +
                "hour1 Text," +
                "iconHour1 Text," +
                "hum1 REAL," +
                "temp1 REAL," +
                "hour2 Text," +
                "iconHour2 Text," +
                "hum2 REAL," +
                "temp2 REAL," +
                "hour3 Text," +
                "iconHour3 Text," +
                "hum3 REAL," +
                "temp3 REAL," +
                "hour4 Text," +
                "iconHour4 Text," +
                "hum4 REAL," +
                "temp4 REAL," +
                "hour5 Text," +
                "iconHour5 Text," +
                "hum5 REAL," +
                "temp5 REAL," +
                "hour6 Text," +
                "iconHour6 Text," +
                "hum6 REAL," +
                "temp6 REAL," +
                "hour7 Text," +
                "iconHour7 Text," +
                "hum7 REAL," +
                "temp7 REAL," +
                "hour8 Text," +
                "iconHour8 Text," +
                "hum8 REAL," +
                "temp8 REAL," +
                "day1 Text," +
                "iconDay1 Text," +
                "humDay1 REAL," +
                "tempMaxDay1 REAL," +
                "tempMinDay1 REAL," +
                "day2 Text," +
                "iconDay2 Text," +
                "humDay2 REAL," +
                "tempMaxDay2 REAL," +
                "tempMinDay2 REAL," +
                "day3 Text," +
                "iconDay3 Text," +
                "humDay3 REAL," +
                "tempMaxDay3 REAL," +
                "tempMinDay3 REAL," +
                "day4 Text," +
                "iconDay4 Text," +
                "humDay4 REAL," +
                "tempMaxDay4 REAL," +
                "tempMinDay4 REAL," +
                "day5 Text," +
                "iconDay5 Text," +
                "humDay5 REAL," +
                "tempMaxDay5 REAL," +
                "tempMinDay5 REAL," +
                "timeupdate Text," +
                "unit Text," +
                "idCity INTEGER )";
        //Chạy câu lệnh tạo bảng product
        db.execSQL(queryCreaTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        if (oldVersion != newVersion) {
//            db.execSQL("drop table if exists " + Defile.DB_NAME_TABLE);
//            onCreate(db);
//        }
    }

    public void insertProduct(ArrayList<ObjectACity> objectACityArrayList) {
        for (int i = 0; i < objectACityArrayList.size(); i++) {
            ObjectACity objectACity = new ObjectACity();
            objectACity = objectACityArrayList.get(i);
            int id = i;
            sqLiteDatabase = getWritableDatabase();
            contentValues = new ContentValues();

            contentValues.put("id", id);
            contentValues.put("lat", objectACity.getLat());
            contentValues.put("lon", objectACity.getLon());
            contentValues.put("nameCity", objectACity.getNameCity());
            contentValues.put("dayNow", objectACity.getDaynow());
            contentValues.put("iconNow", objectACity.getIconnow());
            contentValues.put("StatusNow", objectACity.getStatusnow());
            contentValues.put("TeampNow", objectACity.getTeampnow());
            contentValues.put("TempMaxToDay", objectACity.getTempMaxToDay());
            contentValues.put("TempMinToDay", objectACity.getTempMinToDay());
            contentValues.put("HumidityNow", objectACity.getHumiditynow());

            contentValues.put("hour1", objectACity.getHourlies().get(0).getHour());
            contentValues.put("iconHour1", objectACity.getHourlies().get(0).getIcon());
            contentValues.put("hum1", objectACity.getHourlies().get(0).getHum());
            contentValues.put("temp1", objectACity.getHourlies().get(0).getTemp());

            contentValues.put("hour2", objectACity.getHourlies().get(1).getHour());
            contentValues.put("iconHour2", objectACity.getHourlies().get(1).getIcon());
            contentValues.put("hum2", objectACity.getHourlies().get(1).getHum());
            contentValues.put("temp2", objectACity.getHourlies().get(1).getTemp());

            contentValues.put("hour3", objectACity.getHourlies().get(2).getHour());
            contentValues.put("iconHour3", objectACity.getHourlies().get(2).getIcon());
            contentValues.put("hum3", objectACity.getHourlies().get(2).getHum());
            contentValues.put("temp3", objectACity.getHourlies().get(2).getTemp());

            contentValues.put("hour4", objectACity.getHourlies().get(3).getHour());
            contentValues.put("iconHour4", objectACity.getHourlies().get(3).getIcon());
            contentValues.put("hum4", objectACity.getHourlies().get(3).getHum());
            contentValues.put("temp4", objectACity.getHourlies().get(3).getTemp());

            contentValues.put("hour5", objectACity.getHourlies().get(4).getHour());
            contentValues.put("iconHour5", objectACity.getHourlies().get(4).getIcon());
            contentValues.put("hum5", objectACity.getHourlies().get(4).getHum());
            contentValues.put("temp5", objectACity.getHourlies().get(4).getTemp());

            contentValues.put("hour6", objectACity.getHourlies().get(5).getHour());
            contentValues.put("iconHour6", objectACity.getHourlies().get(5).getIcon());
            contentValues.put("hum6", objectACity.getHourlies().get(5).getHum());
            contentValues.put("temp6", objectACity.getHourlies().get(5).getTemp());
            contentValues.put("hour7", objectACity.getHourlies().get(6).getHour());
            contentValues.put("iconHour7", objectACity.getHourlies().get(6).getIcon());

            contentValues.put("hum7", objectACity.getHourlies().get(6).getHum());
            contentValues.put("temp7", objectACity.getHourlies().get(6).getTemp());
            contentValues.put("hour8", objectACity.getHourlies().get(7).getHour());
            contentValues.put("iconHour8", objectACity.getHourlies().get(7).getIcon());
            contentValues.put("hum8", objectACity.getHourlies().get(7).getHum());
            contentValues.put("temp8", objectACity.getHourlies().get(7).getTemp());

            contentValues.put("day1", objectACity.getDailies().get(0).getDay());
            contentValues.put("iconDay1", objectACity.getDailies().get(0).getIcon());
            contentValues.put("humDay1", objectACity.getDailies().get(0).getHum());
            contentValues.put("tempMaxDay1", objectACity.getDailies().get(0).getTempMax());
            contentValues.put("tempMinDay1", objectACity.getDailies().get(0).getTempMin());

            contentValues.put("day2", objectACity.getDailies().get(1).getDay());
            contentValues.put("iconDay2", objectACity.getDailies().get(1).getIcon());
            contentValues.put("humDay2", objectACity.getDailies().get(1).getHum());
            contentValues.put("tempMaxDay2", objectACity.getDailies().get(1).getTempMax());
            contentValues.put("tempMinDay2", objectACity.getDailies().get(1).getTempMin());

            contentValues.put("day3", objectACity.getDailies().get(2).getDay());
            contentValues.put("iconDay3", objectACity.getDailies().get(2).getIcon());
            contentValues.put("humDay3", objectACity.getDailies().get(2).getHum());
            contentValues.put("tempMaxDay3", objectACity.getDailies().get(2).getTempMax());
            contentValues.put("tempMinDay3", objectACity.getDailies().get(2).getTempMin());

            contentValues.put("day4", objectACity.getDailies().get(3).getDay());
            contentValues.put("iconDay4", objectACity.getDailies().get(3).getIcon());
            contentValues.put("humDay4", objectACity.getDailies().get(3).getHum());
            contentValues.put("tempMaxDay4", objectACity.getDailies().get(3).getTempMax());
            contentValues.put("tempMinDay4", objectACity.getDailies().get(3).getTempMin());

            contentValues.put("day5", objectACity.getDailies().get(4).getDay());
            contentValues.put("iconDay5", objectACity.getDailies().get(4).getIcon());
            contentValues.put("humDay5", objectACity.getDailies().get(4).getHum());
            contentValues.put("tempMaxDay5", objectACity.getDailies().get(4).getTempMax());
            contentValues.put("tempMinDay5", objectACity.getDailies().get(4).getTempMin());


            contentValues.put("timeupdate", objectACity.getTimeupdate());
            contentValues.put("unit", objectACity.getUnit());
            contentValues.put("idCity", objectACity.getID());
            sqLiteDatabase.insert(Defile.DB_NAME_TABLE, null, contentValues);
            closeDB();
        }
    }

//    public int deleteNote(int id) {
//        sqLiteDatabase = getWritableDatabase();
//        return Long.valueOf(sqLiteDatabase.delete(Defile.DB_NAME_TABLE, " id=?", new String[]{String.valueOf(id)})).intValue();
//    }

    public boolean deleteNoteAll() {
        sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(Defile.DB_NAME_TABLE, null, null);
        closeDB();
        return true;
    }

//    public void updateProduct(String id, String name, String quantity) {
//        sqLiteDatabase = getWritableDatabase();
//        contentValues = new ContentValues();
//        contentValues.put("name", name);
//        contentValues.put("quantity", quantity);
//
//        sqLiteDatabase.update(Defile.DB_NAME_TABLE, contentValues, "id=?", new String[]{String.valueOf(id)});
//        closeDB();
//    }

    public ArrayList<ObjectACity> getAllProduct() {
        try {
            sqLiteDatabase = getReadableDatabase();
            cursor = sqLiteDatabase.query(false, Defile.DB_NAME_TABLE, null, null, null
                    , null, null, null, null);
            ArrayList<ObjectACity> objectACityArrayList = new ArrayList<>();

            while (cursor.moveToNext()) {
                ObjectACity objectACity = new ObjectACity();
                ArrayList<Hourly> hourlies = new ArrayList<>();
                Hourly hourly = new Hourly();
                ArrayList<Daily> dailies = new ArrayList<>();
                Daily daily = new Daily();
                double lat = cursor.getDouble(cursor.getColumnIndex("lat"));
                double lon = cursor.getDouble(cursor.getColumnIndex("lon"));
                String nameCity = cursor.getString(cursor.getColumnIndex("nameCity"));
                String dayNow = cursor.getString(cursor.getColumnIndex("dayNow"));
                String iconNow = cursor.getString(cursor.getColumnIndex("iconNow"));
                String StatusNow = cursor.getString(cursor.getColumnIndex("StatusNow"));
                double TeampNow = cursor.getDouble(cursor.getColumnIndex("TeampNow"));
                double TempMaxToDay = cursor.getDouble(cursor.getColumnIndex("TempMaxToDay"));
                double TempMinToDay = cursor.getDouble(cursor.getColumnIndex("TempMinToDay"));
                double HumidityNow = cursor.getDouble(cursor.getColumnIndex("HumidityNow"));

                String hour1 = cursor.getString(cursor.getColumnIndex("hour1"));
                String iconHour1 = cursor.getString(cursor.getColumnIndex("iconHour1"));
                double hum1 = cursor.getDouble(cursor.getColumnIndex("hum1"));
                double temp1 = cursor.getDouble(cursor.getColumnIndex("temp1"));
                hourly = new Hourly(hour1, iconHour1, hum1, temp1);
                hourlies.add(hourly);

                String hour2 = cursor.getString(cursor.getColumnIndex("hour2"));
                String iconHour2 = cursor.getString(cursor.getColumnIndex("iconHour2"));
                double hum2 = cursor.getDouble(cursor.getColumnIndex("hum2"));
                double temp2 = cursor.getDouble(cursor.getColumnIndex("temp2"));
                hourly = new Hourly(hour2, iconHour2, hum2, temp2);
                hourlies.add(hourly);

                String hour3 = cursor.getString(cursor.getColumnIndex("hour3"));
                String iconHour3 = cursor.getString(cursor.getColumnIndex("iconHour3"));
                double hum3 = cursor.getDouble(cursor.getColumnIndex("hum3"));
                double temp3 = cursor.getDouble(cursor.getColumnIndex("temp3"));
                hourly = new Hourly(hour3, iconHour3, hum3, temp3);
                hourlies.add(hourly);

                String hour4 = cursor.getString(cursor.getColumnIndex("hour4"));
                String iconHour4 = cursor.getString(cursor.getColumnIndex("iconHour4"));
                double hum4 = cursor.getDouble(cursor.getColumnIndex("hum4"));
                double temp4 = cursor.getDouble(cursor.getColumnIndex("temp4"));
                hourly = new Hourly(hour4, iconHour4, hum4, temp4);
                hourlies.add(hourly);

                String hour5 = cursor.getString(cursor.getColumnIndex("hour5"));
                String iconHour5 = cursor.getString(cursor.getColumnIndex("iconHour5"));
                double hum5 = cursor.getDouble(cursor.getColumnIndex("hum5"));
                double temp5 = cursor.getDouble(cursor.getColumnIndex("temp5"));
                hourly = new Hourly(hour5, iconHour5, hum5, temp5);
                hourlies.add(hourly);

                String hour6 = cursor.getString(cursor.getColumnIndex("hour6"));
                String iconHour6 = cursor.getString(cursor.getColumnIndex("iconHour6"));
                double hum6 = cursor.getDouble(cursor.getColumnIndex("hum6"));
                double temp6 = cursor.getDouble(cursor.getColumnIndex("temp6"));
                hourly = new Hourly(hour6, iconHour6, hum6, temp6);
                hourlies.add(hourly);

                String hour7 = cursor.getString(cursor.getColumnIndex("hour7"));
                String iconHour7 = cursor.getString(cursor.getColumnIndex("iconHour7"));
                double hum7 = cursor.getDouble(cursor.getColumnIndex("hum7"));
                double temp7 = cursor.getDouble(cursor.getColumnIndex("temp7"));
                hourly = new Hourly(hour7, iconHour7, hum7, temp7);
                hourlies.add(hourly);

                String hour8 = cursor.getString(cursor.getColumnIndex("hour8"));
                String iconHour8 = cursor.getString(cursor.getColumnIndex("iconHour8"));
                double hum8 = cursor.getDouble(cursor.getColumnIndex("hum8"));
                double temp8 = cursor.getDouble(cursor.getColumnIndex("temp8"));
                hourly = new Hourly(hour8, iconHour8, hum8, temp8);
                hourlies.add(hourly);

                String day1 = cursor.getString(cursor.getColumnIndex("day1"));
                String iconDay1 = cursor.getString(cursor.getColumnIndex("iconDay1"));
                double humDay1 = cursor.getDouble(cursor.getColumnIndex("humDay1"));
                double tempMaxDay1 = cursor.getDouble(cursor.getColumnIndex("tempMaxDay1"));
                double tempMinDay1 = cursor.getDouble(cursor.getColumnIndex("tempMinDay1"));
                daily = new Daily(day1, iconDay1, humDay1, tempMaxDay1, tempMinDay1);
                dailies.add(daily);

                String day2 = cursor.getString(cursor.getColumnIndex("day2"));
                String iconDay2 = cursor.getString(cursor.getColumnIndex("iconDay2"));
                double humDay2 = cursor.getDouble(cursor.getColumnIndex("humDay2"));
                double tempMaxDay2 = cursor.getDouble(cursor.getColumnIndex("tempMaxDay2"));
                double tempMinDay2 = cursor.getDouble(cursor.getColumnIndex("tempMinDay2"));
                daily = new Daily(day2, iconDay2, humDay2, tempMaxDay2, tempMinDay2);
                dailies.add(daily);

                String day3 = cursor.getString(cursor.getColumnIndex("day3"));
                String iconDay3 = cursor.getString(cursor.getColumnIndex("iconDay3"));
                double humDay3 = cursor.getDouble(cursor.getColumnIndex("humDay3"));
                double tempMaxDay3 = cursor.getDouble(cursor.getColumnIndex("tempMaxDay3"));
                double tempMinDay3 = cursor.getDouble(cursor.getColumnIndex("tempMinDay3"));
                daily = new Daily(day3, iconDay3, humDay3, tempMaxDay3, tempMinDay3);
                dailies.add(daily);

                String day4 = cursor.getString(cursor.getColumnIndex("day4"));
                String iconDay4 = cursor.getString(cursor.getColumnIndex("iconDay4"));
                double humDay4 = cursor.getDouble(cursor.getColumnIndex("humDay4"));
                double tempMaxDay4 = cursor.getDouble(cursor.getColumnIndex("tempMaxDay4"));
                double tempMinDay4 = cursor.getDouble(cursor.getColumnIndex("tempMinDay4"));
                daily = new Daily(day4, iconDay4, humDay4, tempMaxDay4, tempMinDay4);
                dailies.add(daily);

                String day5 = cursor.getString(cursor.getColumnIndex("day5"));
                String iconDay5 = cursor.getString(cursor.getColumnIndex("iconDay5"));
                double humDay5 = cursor.getDouble(cursor.getColumnIndex("humDay5"));
                double tempMaxDay5 = cursor.getDouble(cursor.getColumnIndex("tempMaxDay5"));
                double tempMinDay5 = cursor.getDouble(cursor.getColumnIndex("tempMinDay5"));
                daily = new Daily(day5, iconDay5, humDay5, tempMaxDay5, tempMinDay5);
                dailies.add(daily);

                String timeupdate = cursor.getString(cursor.getColumnIndex("timeupdate"));
                String unit = cursor.getString(cursor.getColumnIndex("unit"));
                int idCity = cursor.getInt(cursor.getColumnIndex("idCity"));
                objectACity = new ObjectACity(nameCity, dayNow, iconNow, StatusNow, TeampNow, TempMaxToDay, TempMinToDay, HumidityNow, hourlies, dailies, unit, timeupdate, idCity, lat, lon);
                objectACityArrayList.add(objectACity);
            }
            closeDB();
            if (objectACityArrayList.size() == 0) {
                return null;
            } else {
                return objectACityArrayList;
            }
        } catch (Exception e) {
            return null;
        }
    }

    private void closeDB() {
        if (sqLiteDatabase != null) sqLiteDatabase.close();
        if (contentValues != null) contentValues.clear();
        if (cursor != null) cursor.close();
    }
}
