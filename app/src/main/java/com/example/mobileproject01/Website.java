package com.example.mobileproject01;

public class Website {
    private int id;
    private String URL;
    private int categoryID;

    public Website(int id, String URL, int category) {
        this.id = id;
        this.categoryID = categoryID;
        this.URL = URL;
    }

    public String getURL() {
        return URL;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public int getId() {
        return id;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public void setCategory(int categoryID) {
        this.categoryID = categoryID;
    }

    public void setId(int id) {
        this.id = id;
    }
}
