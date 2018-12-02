package com.jifisher.infohouses.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jifisher.infohouses.SupportClasses.Company;
import com.jifisher.infohouses.SupportClasses.House;

public class DBCompanies {

    private static final String DB_NAME = "companies";
    private static final int DB_VERSION = 1;
    private static final String DB_TABLE = "companies";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_HREF = "href";
    public static final String COLUMN_NUMBER = "number";
    public static final String COLUMN_ADRESS = "adress";
    public static final String COLUMN_SUM_PROJECT = "sum_project";
    public static final String COLUMN_END_PROJECT = "end_project";
    public static final String COLUMN_DAY = "day";

    private static final String DB_CREATE =
            "CREATE TABLE `companies` (\n" +
                    "\t`" + COLUMN_ID  + "`\tINTEGER,\n" +
                    "\t`" + COLUMN_NAME  + "`\tTEXT,\n" +
                    "\t`" + COLUMN_HREF   + "`\t TEXT,\n" +
                    "\t`" + COLUMN_NUMBER   + "`\t TEXT,\n" +
                    "\t`" + COLUMN_ADRESS   + "`\t TEXT,\n" +
                    "\t`" + COLUMN_END_PROJECT   + "`\t TEXT,\n" +
                    "\t`" + COLUMN_SUM_PROJECT   + "`\t TEXT,\n" +
                    "\t`" + COLUMN_DAY   + "`\t REAL,\n" +
                    "\tPRIMARY KEY(`_id`)\n" +
                    ");";

    private final Context mCtx;
    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;
    String[][] companies;

    public DBCompanies(Context ctx) {
        mCtx = ctx;
    }

    // открыть подключение
    public void open() {
        mDBHelper = new DBHelper(mCtx, DB_NAME, null, DB_VERSION);
        mDB = mDBHelper.getWritableDatabase();
    }
    // открыть подключение
    public void open(String[][] companies) {
        this.companies = companies;
        mDBHelper = new DBHelper(mCtx, DB_NAME, null, DB_VERSION);
        mDB = mDBHelper.getWritableDatabase();
    }

    public Company getcompany(int id) {

        open();
        Cursor cursor;
        cursor = mDB.query(DB_TABLE, null, COLUMN_ID+" = ?", new String[]{id+""}, null, null, null);
        cursor.moveToFirst();
        Company company=Company.fromCursor(cursor);
        close();
        return company;
    }
    public Cursor getAll(){
        open();
        Cursor cursor;
        cursor = mDB.query(DB_TABLE, null, null,null, null, null, COLUMN_DAY);
        return cursor;
    }

    public void update(int id, int builds, int days, int end){
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_SUM_PROJECT,builds);
        cv.put(COLUMN_END_PROJECT,end);
        cv.put(COLUMN_DAY,(int)(days/(float)builds*100)/100f);
        open();
        mDB.update(DB_TABLE,cv,COLUMN_ID+ " = ?",new String[]{id+""});
        close();
    }

    public Cursor getHref(String company,boolean flagFavourite) {

        open();
        Cursor cursor;
        cursor = mDB.query(DB_TABLE, null, COLUMN_NAME+" = ?", new String[]{company}, null, null, null);
        return cursor;
    }


    // закрыть подключение
    public void close() {
        if (mDBHelper != null) mDBHelper.close();
    }


    // класс по созданию и управлению БД
    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                        int version) {
            super(context, name, factory, version);
        }

        // создаем и заполняем БД
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE);
            ContentValues cv = new ContentValues();
            for (int i = 0; i < companies[0].length-1; i++) {
                cv.put("name", companies[0][i+1]);
                cv.put("href", companies[1][i]);
                cv.put("adress", companies[2][i]);
                cv.put("number", companies[3][i]);
                db.insert(DB_TABLE, null, cv);
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.delete(DB_TABLE, null, null);
            ContentValues cv = new ContentValues();
            for (int i = 0; i < companies[0].length; i++) {
                cv.put("name", companies[0][i]);
                cv.put("href", companies[1][i]);
                cv.put("adress", companies[2][i]);
                cv.put("number", companies[3][i]);
                db.insert(DB_TABLE, null, cv);
            }
        }

    }


}