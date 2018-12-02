package com.jifisher.infohouses.SupportClasses;

import java.util.ArrayList;

public class Room {

    public String name;
    public ArrayList<String> image;

    public Room(String name, String image){
        this.name=name;
        this.image=getList(image);
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
