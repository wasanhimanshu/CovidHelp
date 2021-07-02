package com.example.covidhelp.model;

import com.google.gson.annotations.SerializedName;

public class News {
    @SerializedName("title")
    private String mTitle;
    @SerializedName("description")
    private String mDesc;
    @SerializedName("urlToImage")
    private String ImageUrl;
    @SerializedName("publishedAt")
    private String mDate;
    @SerializedName("url")
    private String Url;

    public News(String title,String desc,String imageurl,String date,String url){
        this.mTitle=title;
        this.mDesc=desc;
        this.ImageUrl=imageurl;
        this.mDate=date;
        this.Url=url;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmDate() {
        return mDate;
    }

    public String getmDesc() {
        return mDesc;
    }

    public String getUrl() {
        return Url;
    }
}
