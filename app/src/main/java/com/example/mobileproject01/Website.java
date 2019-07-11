package com.example.mobileproject01;

public class Website {
    private int id;
    private String title;
    private String URL;
    private int categoryID;
    private int isSelected;

    public String getURL() {
        return URL;
    }

    public String getTitle() {
        return title;
    }

    public int getIsSelected() {
        return isSelected;
    }

    public int getId() {
        return id;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setIsSelected(int isSelected) {
        this.isSelected = isSelected;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }
}
