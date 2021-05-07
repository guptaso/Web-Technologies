package com.example.hw9;

public class movieData {
    private String id;
    private String img;
    private String name;

    public movieData(String id, String img, String name){
        this.id = id;
        this.img = img;
        this.name = name;
    }

    public String getId(){
        return id;
    }

    public String getImg(){
        return img;
    }

    public String getName(){
        return name;
    }


}
