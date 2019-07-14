package com.example.mobileproject01;

import android.graphics.drawable.Icon;

public class Category {
    private int id;
    private String title;
    private int isSelected;
    private Icon icon;

    public Category(int id, String title, int isSelected) {
        this.setId(id);
        this.setTitle(title);
        this.setIsSelected(isSelected);
    }

    public Category() {
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public Icon getIcon() {
        return icon;
    }

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
