package com.jifisher.infohouses.SupportClasses;

import android.database.Cursor;

import com.jifisher.infohouses.Databases.DBHouses;

import java.util.ArrayList;

public class House {

    public int id;
    public String nameComplex;
    public String discrit;
    public String space1;
    public String space2;
    public String space3;
    public String space4;
    public String space5;
    public String space6;
    public String spaceStudio;
    public String price;
    public boolean finishing;
    public String date;
    public boolean finished;
    public boolean allFinishing;
    public String type;
    public boolean playground;
    public boolean school;
    public boolean kindergarten;
    public String material;
    public String floorMin;
    public String floorMax;
    public String adress;
    public String company;
    public String x;
    public String y;
    public ArrayList<String> image;
    public ArrayList<Room> rooms;
    public boolean flagLike=false;
    public boolean flagEndBuild=false;

    public House(int id,String nameComplex, String adress, String image, boolean flagLike, boolean flagEndBuild) {
        this.id = id;
        this.nameComplex = nameComplex;
        this.adress = adress;
        this.image= getList(image);
        this.flagLike=flagLike;
        this.flagEndBuild=flagEndBuild;
    }
    public House(int id,String nameComplex, String adress, String company, String discrit, String image, boolean flagLike, boolean flagEndBuild, String room1,String room2,String room3,String room4,String room5,String room6,String roomstudio) {
        this.id = id;
        this.nameComplex = nameComplex;
        this.adress = adress;
        this.company = company;
        this.image= getList(image);
        this.flagLike=flagLike;
        this.flagEndBuild=flagEndBuild;
        this.discrit=discrit;
        rooms=new ArrayList<>();
        if(!room1.equals("null")&&room1.length()>0)
            rooms.add(getRoom("Однокомнотная",room1));
        if(!room2.equals("null")&&room2.length()>0)
            rooms.add(getRoom("Двухкомнотная",room2));
        if(!room3.equals("null")&&room3.length()>0)
            rooms.add(getRoom("Трехкомнотная",room3));
        if(!room4.equals("null")&&room4.length()>0)
            rooms.add(getRoom("Четырехкомнотная",room4));
        if(!room5.equals("null")&&room5.length()>0)
            rooms.add(getRoom("Пятикомнотная",room5));
        if(!room6.equals("null")&&room6.length()>0)
            rooms.add(getRoom("Шестикомнотная",room6));
        if(!roomstudio.equals("null")&&roomstudio.length()>0)
            rooms.add(getRoom("Квартира-студия",roomstudio));
    }
    public House(int id,String nameComplex, String adress, String company, String discrit, String image, boolean flagLike, boolean flagEndBuild, String room1,String room2,String room3,String room4,String room5,String room6,String roomstudio,
                 String space1,String space2,String space3,String space4,String space5,String space6,String spaceStudio, String price,boolean finishing, String date,boolean finished,String type, boolean playground, boolean school,boolean kindergarten, String material, String floorMin, String floorMax, boolean allFinishing, String x, String y) {
        this.id = id;
        this.nameComplex = nameComplex;
        this.adress = adress;
        this.company = company;
        this.image= getList(image);
        this.flagLike=flagLike;
        this.flagEndBuild=flagEndBuild;
        this.discrit=discrit;
        rooms=new ArrayList<>();
        if(!room1.equals("null")&&room1.length()>0)
            rooms.add(getRoom("Однокомнотная",room1));
        if(!room2.equals("null")&&room2.length()>0)
            rooms.add(getRoom("Двухкомнотная",room2));
        if(!room3.equals("null")&&room3.length()>0)
            rooms.add(getRoom("Трехкомнотная",room3));
        if(!room4.equals("null")&&room4.length()>0)
            rooms.add(getRoom("Четырехкомнотная",room4));
        if(!room5.equals("null")&&room5.length()>0)
            rooms.add(getRoom("Пятикомнотная",room5));
        if(!room6.equals("null")&&room6.length()>0)
            rooms.add(getRoom("Шестикомнотная",room6));
        if(!roomstudio.equals("null")&&roomstudio.length()>0)
            rooms.add(getRoom("Квартира-студия",roomstudio));
        this.space1=space1;
        this.space2=space2;
        this.space3=space3;
        this.space4=space4;
        this.space5=space5;
        this.space6=space6;
        this.spaceStudio=spaceStudio;
        this.price=price;
        this.finishing=finishing;
        this.date=date;
        this.finished=finished;
        this.type=type;
        this.playground=playground;
        this.school=school;
        this.kindergarten=kindergarten;
        this.material=material;
        this.floorMin=floorMin;
        this.floorMax=floorMax;
        this.allFinishing=allFinishing;
        this.x=x;
        this.y=y;
    }

    public static House fromCursor(Cursor cursor) {

        return new House(cursor.getInt(cursor.getColumnIndex(DBHouses.COLUMN_ID)),cursor.getString(cursor.getColumnIndex(DBHouses.COLUMN_COMPLEX)),cursor.getString(cursor.getColumnIndex(DBHouses.COLUMN_ADRESS)),cursor.getString(cursor.getColumnIndex(DBHouses.COLUMN_IMAGES_HOUSES)),1==cursor.getInt(cursor.getColumnIndex(DBHouses.COLUMN_FAVOURITE)),1==cursor.getInt(cursor.getColumnIndex(DBHouses.COLUMN_FINISHED)));
    }

    public static House getFromCursor(Cursor cursor) {

        return new House(cursor.getInt(cursor.getColumnIndex(DBHouses.COLUMN_ID)),cursor.getString(cursor.getColumnIndex(DBHouses.COLUMN_COMPLEX)),cursor.getString(cursor.getColumnIndex(DBHouses.COLUMN_ADRESS)),cursor.getString(cursor.getColumnIndex(DBHouses.COLUMN_COMPANY)),cursor.getString(cursor.getColumnIndex(DBHouses.COLUMN_DISCRICT)),cursor.getString(cursor.getColumnIndex(DBHouses.COLUMN_IMAGES_HOUSES)),1==cursor.getInt(cursor.getColumnIndex(DBHouses.COLUMN_FAVOURITE)),1==cursor.getInt(cursor.getColumnIndex(DBHouses.COLUMN_FINISHED)),
                cursor.getString(cursor.getColumnIndex(DBHouses.COLUMN_IMAGES_ROOM1)),cursor.getString(cursor.getColumnIndex(DBHouses.COLUMN_IMAGES_ROOM2)),cursor.getString(cursor.getColumnIndex(DBHouses.COLUMN_IMAGES_ROOM3)),cursor.getString(cursor.getColumnIndex(DBHouses.COLUMN_IMAGES_ROOM4)),cursor.getString(cursor.getColumnIndex(DBHouses.COLUMN_IMAGES_ROOM5)),cursor.getString(cursor.getColumnIndex(DBHouses.COLUMN_IMAGES_ROOM6)),cursor.getString(cursor.getColumnIndex(DBHouses.COLUMN_IMAGES_ROOM_STUDIO)));
    }
    public static House getFullInfo(Cursor cursor) {
        return new House(cursor.getInt(cursor.getColumnIndex(DBHouses.COLUMN_ID)),cursor.getString(cursor.getColumnIndex(DBHouses.COLUMN_COMPLEX)),cursor.getString(cursor.getColumnIndex(DBHouses.COLUMN_ADRESS)),cursor.getString(cursor.getColumnIndex(DBHouses.COLUMN_COMPANY)),cursor.getString(cursor.getColumnIndex(DBHouses.COLUMN_DISCRICT)),cursor.getString(cursor.getColumnIndex(DBHouses.COLUMN_IMAGES_HOUSES)),1==cursor.getInt(cursor.getColumnIndex(DBHouses.COLUMN_FAVOURITE)),1==cursor.getInt(cursor.getColumnIndex(DBHouses.COLUMN_FINISHED)),
                cursor.getString(cursor.getColumnIndex(DBHouses.COLUMN_IMAGES_ROOM1)),cursor.getString(cursor.getColumnIndex(DBHouses.COLUMN_IMAGES_ROOM2)),cursor.getString(cursor.getColumnIndex(DBHouses.COLUMN_IMAGES_ROOM3)),cursor.getString(cursor.getColumnIndex(DBHouses.COLUMN_IMAGES_ROOM4)),cursor.getString(cursor.getColumnIndex(DBHouses.COLUMN_IMAGES_ROOM5)),cursor.getString(cursor.getColumnIndex(DBHouses.COLUMN_IMAGES_ROOM6)),cursor.getString(cursor.getColumnIndex(DBHouses.COLUMN_IMAGES_ROOM_STUDIO)),
                cursor.getString(cursor.getColumnIndex(DBHouses.COLUMN_SPACE1)),cursor.getString(cursor.getColumnIndex(DBHouses.COLUMN_SPACE2)),cursor.getString(cursor.getColumnIndex(DBHouses.COLUMN_SPACE3)),cursor.getString(cursor.getColumnIndex(DBHouses.COLUMN_SPACE4)),cursor.getString(cursor.getColumnIndex(DBHouses.COLUMN_SPACE5)),cursor.getString(cursor.getColumnIndex(DBHouses.COLUMN_SPACE6)),cursor.getString(cursor.getColumnIndex(DBHouses.COLUMN_SPACE_STUDIO)),
                cursor.getString(cursor.getColumnIndex(DBHouses.COLUMN_PRICE)),0!=cursor.getInt(cursor.getColumnIndex(DBHouses.COLUMN_FINISHING)),cursor.getString(cursor.getColumnIndex(DBHouses.COLUMN_DATE)),0!=cursor.getInt(cursor.getColumnIndex(DBHouses.COLUMN_FINISHING)),cursor.getString(cursor.getColumnIndex(DBHouses.COLUMN_TYPE)),0!=cursor.getInt(cursor.getColumnIndex(DBHouses.COLUMN_PLAYGROUND)),0!=cursor.getInt(cursor.getColumnIndex(DBHouses.COLUMN_SCHOOL)),
                0!=cursor.getInt(cursor.getColumnIndex(DBHouses.COLUMN_KINDERGARTEN)),cursor.getString(cursor.getColumnIndex(DBHouses.COLUMN_MATERIAL)),cursor.getString(cursor.getColumnIndex(DBHouses.COLUMN_FLOORS1)),cursor.getString(cursor.getColumnIndex(DBHouses.COLUMN_FLOORS2)),2==cursor.getInt(cursor.getColumnIndex(DBHouses.COLUMN_FLOORS2)),cursor.getString(cursor.getColumnIndex(DBHouses.COLUMN_X)),cursor.getString(cursor.getColumnIndex(DBHouses.COLUMN_Y)));

        }

    private Room getRoom(String name, String image){
        return new Room(name, image);
    }

    private ArrayList<String> getList(String str){
        ArrayList<String> result=new ArrayList<>();
        while (str.indexOf("jifisher//38//jifisher")!=-1){
            result.add(str.substring(0,str.indexOf("jifisher//38//jifisher")));
            str=str.substring(str.indexOf("jifisher//38//jifisher")+("jifisher//38//jifisher").length());
        }
        result.add(str);
        return result;
    }

}
