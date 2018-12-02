package com.jifisher.infohouses.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jifisher.infohouses.SupportClasses.House;

public class DBHouses {

    private static final String DB_NAME = "houses";
    private static final int DB_VERSION = 1;
    private static final String DB_TABLE = "houses";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ADRESS = "adress";
    public static final String COLUMN_SPACE1 = "space1";
    public static final String COLUMN_SPACE2 = "space2";
    public static final String COLUMN_SPACE3 = "space3";
    public static final String COLUMN_SPACE4 = "space4";
    public static final String COLUMN_SPACE5 = "space5";
    public static final String COLUMN_SPACE6 = "space6";
    public static final String COLUMN_SPACE_STUDIO = "spacestudio";
    public static final String COLUMN_DISCRICT = "discrit";
    public static final String COLUMN_ROOM1 = "room1";
    public static final String COLUMN_ROOM2 = "room2";
    public static final String COLUMN_ROOM3 = "room3";
    public static final String COLUMN_ROOM4 = "room4";
    public static final String COLUMN_ROOM5 = "room5";
    public static final String COLUMN_ROOM6 = "room6";
    public static final String COLUMN_ROOM_STUDIO = "roomstudio";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_FINISHING = "finishing";
    public static final String COLUMN_COMPANY = "company";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_FINISHED = "finished";
    public static final String COLUMN_COMPLEX = "complex";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_PLAYGROUND = "playground";
    public static final String COLUMN_SCHOOL = "school";
    public static final String COLUMN_KINDERGARTEN = "kindergarten";
    public static final String COLUMN_MATERIAL = "material";
    public static final String COLUMN_FLOORS1 = "floors1";
    public static final String COLUMN_FLOORS2 = "floors2";
    public static final String COLUMN_FAVOURITE = "favourite";
    public static final String COLUMN_IMAGES_HOUSES = "images_houses";
    public static final String COLUMN_IMAGES_ROOM1 = "imagesroom1";
    public static final String COLUMN_IMAGES_ROOM2 = "imagesroom2";
    public static final String COLUMN_IMAGES_ROOM3 = "imagesroom3";
    public static final String COLUMN_IMAGES_ROOM4 = "imagesroom4";
    public static final String COLUMN_IMAGES_ROOM5 = "imagesroom5";
    public static final String COLUMN_IMAGES_ROOM6 = "imagesroom6";
    public static final String COLUMN_IMAGES_ROOM_STUDIO = "imagesroomstudio";
    public static final String COLUMN_X = "x";
    public static final String COLUMN_Y = "y";
    public static final String COLUMN_AFTER_DEDLINE = "afterdedline";

    private static final String DB_CREATE =
            "CREATE TABLE `houses` (\n" +
                    "\t`" + COLUMN_ID  + "`\tINTEGER,\n" +
                    "\t`" + COLUMN_ADRESS  + "`\tTEXT,\n" +
                    "\t`" + COLUMN_SPACE1   + "`\t TEXT,\n" +
                    "\t`" + COLUMN_SPACE2   + "`\t TEXT,\n" +
                    "\t`" + COLUMN_SPACE3   + "`\t TEXT,\n" +
                    "\t`" + COLUMN_SPACE4   + "`\t TEXT,\n" +
                    "\t`" + COLUMN_SPACE5   + "`\t TEXT,\n" +
                    "\t`" + COLUMN_SPACE6   + "`\t TEXT,\n" +
                    "\t`" + COLUMN_SPACE_STUDIO   + "`\t TEXT,\n" +
                    "\t`" + COLUMN_DISCRICT  + "`\t TEXT,\n" +
                    "\t`" + COLUMN_ROOM1  + "`\t INTEGER NOT NULL DEFAULT 0,\n" +
                    "\t`" + COLUMN_ROOM2  + "`\t INTEGER NOT NULL DEFAULT 0,\n" +
                    "\t`" + COLUMN_ROOM3  + "`\t INTEGER NOT NULL DEFAULT 0,\n" +
                    "\t`" + COLUMN_ROOM4  + "`\t INTEGER NOT NULL DEFAULT 0,\n" +
                    "\t`" + COLUMN_ROOM5  + "`\t INTEGER NOT NULL DEFAULT 0,\n" +
                    "\t`" + COLUMN_ROOM6  + "`\t INTEGER NOT NULL DEFAULT 0,\n" +
                    "\t`" + COLUMN_ROOM_STUDIO  + "`\t INTEGER NOT NULL DEFAULT 0,\n" +
                    "\t`" + COLUMN_PRICE  + "`\t INTEGER NOT NULL DEFAULT 0,\n" +
                    "\t`" + COLUMN_FINISHING  + "`\t INTEGER NOT NULL DEFAULT 0,\n" +
                    "\t`" + COLUMN_COMPANY  + "`\t TEXT,\n" +
                    "\t`" + COLUMN_COMPLEX  + "`\t TEXT,\n" +
                    "\t`" + COLUMN_DATE  + "`\t INTEGER NOT NULL DEFAULT 0,\n" +
                    "\t`" + COLUMN_FINISHED  + "`\t INTEGER NOT NULL DEFAULT 0,\n" +
                    "\t`" + COLUMN_TYPE  + "`\t TEXT,\n" +
                    "\t`" + COLUMN_PLAYGROUND  + "`\t INTEGER NOT NULL DEFAULT 0,\n" +
                    "\t`" + COLUMN_SCHOOL  + "`\t INTEGER NOT NULL DEFAULT 0,\n" +
                    "\t`" + COLUMN_KINDERGARTEN  + "`\t INTEGER NOT NULL DEFAULT 0,\n" +
                    "\t`" + COLUMN_MATERIAL  + "`\t TEXT,\n" +
                    "\t`" + COLUMN_FLOORS1  + "`\t INTEGER NOT NULL DEFAULT 0,\n" +
                    "\t`" + COLUMN_FLOORS2  + "`\t INTEGER NOT NULL DEFAULT 0,\n" +
                    "\t`" + COLUMN_FAVOURITE  + "`\t INTEGER NOT NULL DEFAULT 0,\n" +
                    "\t`" + COLUMN_X  + "`\t INTEGER NOT NULL DEFAULT 0,\n" +
                    "\t`" + COLUMN_Y  + "`\t INTEGER NOT NULL DEFAULT 0,\n" +
                    "\t`" + COLUMN_AFTER_DEDLINE  + "`\t INTEGER NOT NULL DEFAULT 0,\n" +
                    "\t`" + COLUMN_IMAGES_HOUSES  + "`\t TEXT,\n" +
                    "\t`" + COLUMN_IMAGES_ROOM1  + "`\t TEXT,\n" +
                    "\t`" + COLUMN_IMAGES_ROOM2  + "`\t TEXT,\n" +
                    "\t`" + COLUMN_IMAGES_ROOM3  + "`\t TEXT,\n" +
                    "\t`" + COLUMN_IMAGES_ROOM4  + "`\t TEXT,\n" +
                    "\t`" + COLUMN_IMAGES_ROOM5  + "`\t TEXT,\n" +
                    "\t`" + COLUMN_IMAGES_ROOM6  + "`\t TEXT,\n" +
                    "\t`" + COLUMN_IMAGES_ROOM_STUDIO  + "`\t TEXT,\n" +
                    "\tPRIMARY KEY(`_id`)\n" +
                    ");";

    private final Context mCtx;
    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;
    String[][] house;

    public DBHouses(Context ctx) {
        mCtx = ctx;
    }

    // открыть подключение
    public void open() {
        mDBHelper = new DBHelper(mCtx, DB_NAME, null, DB_VERSION);
        mDB = mDBHelper.getWritableDatabase();
    }

    public void updateLike(int id, boolean like) {
        open();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_FAVOURITE, like ? 1 : 0);
        mDB.update(DB_TABLE, cv, COLUMN_ID + " = ?", new String[]{id + ""});
        close();
    }

    // открыть подключение
    public void open(String[][] house) {
        this.house = house;
        mDBHelper = new DBHelper(mCtx, DB_NAME, null, DB_VERSION);
        mDB = mDBHelper.getWritableDatabase();
    }

    public Cursor getHouses(boolean flagFavourite) {

        open();
        Cursor cursor;
        cursor = mDB.query(DB_TABLE, null, (flagFavourite)?COLUMN_FAVOURITE+" = ?":null, (flagFavourite)?new String[]{"1"}:null, null, null, null);
        return cursor;
    }
    public House getHouse(int id) {

        open();
        Cursor cursor;
        cursor = mDB.query(DB_TABLE, null, COLUMN_ID+" = ?", new String[]{id+""}, null, null, null);
        cursor.moveToFirst();
        House house=House.getFromCursor(cursor);
        close();
        return house;
    }

    public Cursor getHouses(String selection,boolean flagFavourite) {

        open();
        Cursor cursor;
        cursor = mDB.query(DB_TABLE, null, selection+((flagFavourite)?COLUMN_FAVOURITE+" = ?":""), (flagFavourite)?new String[]{"1"}:null, null, null, null);
        return cursor;
    }


    // закрыть подключение
    public void close() {
        if (mDBHelper != null) mDBHelper.close();
    }

    public House getFullInfo(House house) {

        open();
        Cursor cursor;
        cursor = mDB.query(DB_TABLE, null, COLUMN_ID+" = ?", new String[]{house.id+""}, null, null, null);
        cursor.moveToFirst();
        House houseFullInfo=House.getFullInfo(cursor);
        close();
        return houseFullInfo;
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
            for (int i = 0; i < house[0].length; i++) {
                cv.put("adress", house[0][i]);
                cv.put("space1", house[1][i]);
                cv.put("space2", house[2][i]);
                cv.put("space3", house[3][i]);
                cv.put("space4", house[4][i]);
                cv.put("space5", house[5][i]);
                cv.put("space6", house[6][i]);
                cv.put("spacestudio", house[7][i]);
                cv.put("discrit", house[8][i]);
                cv.put("room1", house[9][i]);
                cv.put("room2", house[10][i]);
                cv.put("room3", house[11][i]);
                cv.put("room4", house[12][i]);
                cv.put("room5", house[13][i]);
                cv.put("room6", house[14][i]);
                cv.put("roomstudio", house[15][i]);
                cv.put("price", house[16][i]);
                cv.put("finishing", house[17][i]);
                cv.put("company", house[18][i]);
                cv.put("date", house[19][i]);
                cv.put("finished", house[20][i]);
                cv.put("complex", house[21][i]);
                cv.put("type", house[22][i]);
                cv.put("playground", house[23][i]);
                cv.put("school", house[24][i]);
                cv.put("kindergarten", house[25][i]);
                cv.put("material", house[26][i]);
                cv.put("floors1", house[27][i]);
                cv.put("floors2", house[28][i]);
                cv.put("favourite", house[29][i]);
                cv.put("images_houses", house[30][i]);
                cv.put("imagesroom1", house[31][i]);
                cv.put("imagesroom2", house[32][i]);
                cv.put("imagesroom3", house[33][i]);
                cv.put("imagesroom4", house[34][i]);
                cv.put("imagesroom5", house[35][i]);
                cv.put("imagesroom6", house[36][i]);
                cv.put("imagesroomstudio", house[37][i]);
                cv.put("x", house[38][i]);
                cv.put("y", house[39][i]);
                cv.put("afterdedline", house[40][i]);
                db.insert(DB_TABLE, null, cv);
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.delete(DB_TABLE, null, null);
            ContentValues cv = new ContentValues();
            for (int i = 0; i < house[0].length; i++) {
                cv.put("adress", house[0][i]);
                cv.put("space1", house[1][i]);
                cv.put("space2", house[2][i]);
                cv.put("space3", house[3][i]);
                cv.put("space4", house[4][i]);
                cv.put("space5", house[5][i]);
                cv.put("space6", house[6][i]);
                cv.put("spacestudio", house[7][i]);
                cv.put("discrit", house[8][i]);
                cv.put("room1", house[9][i]);
                cv.put("room2", house[10][i]);
                cv.put("room3", house[11][i]);
                cv.put("room4", house[12][i]);
                cv.put("room5", house[13][i]);
                cv.put("room6", house[14][i]);
                cv.put("roomstudio", house[15][i]);
                cv.put("price", house[16][i]);
                cv.put("finishing", house[17][i]);
                cv.put("company", house[18][i]);
                cv.put("date", house[19][i]);
                cv.put("finished", house[20][i]);
                cv.put("complex", house[21][i]);
                cv.put("type", house[22][i]);
                cv.put("playground", house[23][i]);
                cv.put("school", house[24][i]);
                cv.put("kindergarten", house[25][i]);
                cv.put("material", house[26][i]);
                cv.put("floors1", house[27][i]);
                cv.put("floors2", house[28][i]);
                cv.put("favourite", house[29][i]);
                cv.put("images_houses", house[30][i]);
                cv.put("imagesroom1", house[31][i]);
                cv.put("imagesroom2", house[32][i]);
                cv.put("imagesroom3", house[33][i]);
                cv.put("imagesroom4", house[34][i]);
                cv.put("imagesroom5", house[35][i]);
                cv.put("imagesroom6", house[36][i]);
                cv.put("imagesroomstudio", house[37][i]);
                cv.put("x", house[38][i]);
                cv.put("y", house[39][i]);
                cv.put("afterdedline", house[40][i]);
                db.insert(DB_TABLE, null, cv);
            }
        }

    }


}


