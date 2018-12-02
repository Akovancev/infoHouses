package com.jifisher.infohouses.SupportClasses;

import android.database.Cursor;

import com.jifisher.infohouses.Databases.DBCompanies;
import com.jifisher.infohouses.Databases.DBHouses;

import java.util.ArrayList;

public class Company {

    public int id;
    public String name;
    public String sum;
    public String reliability = "Задержка сдачи в среднем 33.4 дня/дом";
    public ArrayList<String> image;
    public String number;
    public String end;
    public String adress;

    public Company(int id, String name, String image, String reliability, String number, String sum, String end, String adress) {
        this.id = id;
        this.name = name;
        this.sum = sum;
        this.end = end;
        this.number = number;
        this.adress = adress;
        this.image = getList(image);
        this.reliability = "Задержка сдачи в среднем " + reliability + " дня/дом";
    }

    public static Company fromCursor(Cursor cursor) {

        return new Company(cursor.getInt(cursor.getColumnIndex(DBHouses.COLUMN_ID)), cursor.getString(cursor.getColumnIndex(DBCompanies.COLUMN_NAME)), cursor.getString(cursor.getColumnIndex(DBCompanies.COLUMN_HREF)), cursor.getString(cursor.getColumnIndex(DBCompanies.COLUMN_DAY)), cursor.getString(cursor.getColumnIndex(DBCompanies.COLUMN_NUMBER)), cursor.getString(cursor.getColumnIndex(DBCompanies.COLUMN_SUM_PROJECT)), cursor.getString(cursor.getColumnIndex(DBCompanies.COLUMN_END_PROJECT)), cursor.getString(cursor.getColumnIndex(DBCompanies.COLUMN_ADRESS)));
    }

    private ArrayList<String> getList(String str) {
        ArrayList<String> result = new ArrayList<>();
        while (str.indexOf("jifisher//38//jifisher") != -1) {
            result.add(str.substring(0, str.indexOf("jifisher//38//jifisher")));
            str = str.substring(str.indexOf("jifisher//38//jifisher") + ("jifisher//38//jifisher").length());
        }
        result.add(str);
        return result;
    }

}