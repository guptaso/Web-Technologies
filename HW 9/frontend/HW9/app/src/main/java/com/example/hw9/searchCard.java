package com.example.hw9;

public class searchCard {
    String img;
    String type;
    String title;
    String rating;
    String year;
    String id;

    public searchCard(String img, String type, String title, String rating, String year ,String id){
        this.img = img;
        this.type = type;
        this.title = title;
        this.rating = rating;
        this.year = year;
        this.id = id;
    }

    public String getImg(){ return img;}

    public String getType(){ return type;}

    public String getTitle(){ return title;}

    public String getRating(){ return rating;}

    public String getYear(){ return year;}

    public String getId(){ return id;}
}
