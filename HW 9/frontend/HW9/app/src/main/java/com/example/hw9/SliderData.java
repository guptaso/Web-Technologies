package com.example.hw9;

public class SliderData {
    private String imgURL;
    private String type;
    private String id;

    public SliderData(String imgURL, String id, String type){

        this.imgURL = imgURL;
        this.id = id;
        this.type = type;
    }

    public String getImgURL(){
        return imgURL;
    }
    public String getId(){
        return id;
    }
    public String getType(){
        return type;
    }

    public void setImgURL(String imgURL){
        this.imgURL = imgURL;
    }


}
