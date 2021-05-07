package com.example.hw9;

public class reviewData {
    private String rating;
    private String username;
    private String content;
    private String creation;

    public reviewData(String rating, String username, String content, String creation){
        this.rating = rating;
        this.username = username;
        this.content = content;
        this.creation = creation;
    }

    public String getRating(){
        return rating;
    }

    public String getUsername(){
        return username;
    }

    public String getContent(){
        return content;
    }

    public String getCreation(){
        return creation;
    }


}
