package com.example.mobileproject01;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class News {
    private int id;
    private String title;
    private String date;
    private String link;
    private int websiteID;
    private String imageAddress;
    private String body;

    public News() {

    }

    public News(String title, String date, String link) {
        this.title = title;
        this.date = date;
        this.link = link;
    }

    public int getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public String getDate() {
        return date;
    }

    public int getWebsiteID() {
        return websiteID;
    }

    public String getLink() {
        return link;
    }

    public String getImageAddress() {
        return imageAddress;
    }


    public String getTitle() {
        return title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setImageAddress(String imageAddress) {
        this.imageAddress = imageAddress;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setWebsiteID(int websiteID) {
        this.websiteID = websiteID;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
