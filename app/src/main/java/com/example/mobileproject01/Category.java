package com.example.mobileproject01;

public class Category {
    private int id;
    private String title;
    private int isSelected;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getIsSelected() {
        return isSelected;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIsSelected(int isSelected) {
        this.isSelected = isSelected;
    }

    public void setId(int id) {
        this.id = id;
    }
}
