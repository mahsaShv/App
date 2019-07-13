package com.example.mobileproject01;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseManager extends SQLiteOpenHelper {

    private static DatabaseManager SINGLE_INSTANCE = null;

    public static String DB_NAME = "database.db";
    public static String CATEGORY_TABLE_NAME = "Categories";
    public static String WEBSITE_TABLE_NAME = "Websites";
    public static String NEWS_TABLE_NAME = "News";

    public static DatabaseManager getInstance(Context context) {
        if (SINGLE_INSTANCE == null) {
            synchronized (DatabaseManager.class) {
                SINGLE_INSTANCE = new DatabaseManager(context);
            }
        }
        return SINGLE_INSTANCE;
    }

    private DatabaseManager(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists " + CATEGORY_TABLE_NAME + " (id integer primary key autoincrement, title varchar(50), isSelected int);");
        db.execSQL("create table if not exists " + WEBSITE_TABLE_NAME + " (id integer primary key autoincrement, title varchar(50), categoryID int, url varchar(100), isSelected int);");
        db.execSQL("create table if not exists " + NEWS_TABLE_NAME + " (id integer primary key, title varchar(100), date varchar(10), link varchar(100), websiteID int, imageAddress varchar(100), body varchar(500));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", category.getId());
        contentValues.put("title", category.getTitle());
        contentValues.put("isSelected", category.getIsSelected());
        db.insert(CATEGORY_TABLE_NAME, null, contentValues);
        contentValues.clear();
    }

    public void deleteCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + CATEGORY_TABLE_NAME + " where title == " + category.getTitle() + ";");
    }

    public void insertCategories(ArrayList<Category> categories) {
        SQLiteDatabase db = this.getWritableDatabase();
        for (Category c:
             categories) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("id", c.getId());
            contentValues.put("title", c.getTitle());
            contentValues.put("isSelected", c.getIsSelected());
            db.insert(CATEGORY_TABLE_NAME, null, contentValues);
            contentValues.clear();
        }
    }

    public void deleteCategories() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + CATEGORY_TABLE_NAME + ";");
    }

    public ArrayList<Category> getCategories() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("select * from " + CATEGORY_TABLE_NAME + ";", null);
        ArrayList<Category> categories = new ArrayList<Category>();
        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
            Category category = new Category();
            category.setId(result.getInt(0));
            category.setTitle(result.getString(1));
            category.setIsSelected(result.getInt(2));
            categories.add(category);
        }
        return categories;

    }

    public void insertWebsite(Website website) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", website.getId());
        contentValues.put("title", website.getTitle());
        contentValues.put("categoryID", website.getCategoryID());
        contentValues.put("url", website.getURL());
        contentValues.put("isSelected", website.getIsSelected());
        db.insert(WEBSITE_TABLE_NAME, null, contentValues);
        contentValues.clear();
    }

    public void deleteWebsite(Website website) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + WEBSITE_TABLE_NAME + " where url == " + website.getURL() + ";");
    }

    public void insertWebsites(ArrayList<Website> websites) {
        SQLiteDatabase db = this.getWritableDatabase();
        for (Website w:
                websites) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("id", w.getId());
            contentValues.put("title", w.getTitle());
            contentValues.put("categoryID", w.getCategoryID());
            contentValues.put("url", w.getURL());
            contentValues.put("isSelected", w.getIsSelected());
            db.insert(WEBSITE_TABLE_NAME, null, contentValues);
            contentValues.clear();
        }
    }

    public void deleteWebsites() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + WEBSITE_TABLE_NAME + ";");
    }

    public ArrayList<Website> getWebsites() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("select * from " + WEBSITE_TABLE_NAME + ";", null);
        ArrayList<Website> websites = new ArrayList<Website>();
        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
            Website website = new Website();
            website.setId(result.getInt(0));
            website.setTitle(result.getString(1));
            website.setCategoryID(result.getInt(2));
            website.setURL(result.getString(3));
            website.setIsSelected(result.getInt(4));
            websites.add(website);
        }
        return websites;
    }

    public void insertNews(ArrayList<News> news) {
        SQLiteDatabase db = this.getWritableDatabase();
        for (News n:
                news) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("id", n.getId());
            contentValues.put("title", n.getTitle());
            contentValues.put("date", n.getDate());
            contentValues.put("link", n.getLink());
            contentValues.put("websiteID", n.getWebsiteID());
            contentValues.put("imageAddress", n.getImageAddress());
            contentValues.put("body", n.getBody());
            db.insert(NEWS_TABLE_NAME, null, contentValues);
            contentValues.clear();
        }
    }

    public ArrayList<News> getNews(int webID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("select * from " + NEWS_TABLE_NAME + " where websiteID == " + webID + ";", null);
        ArrayList<News> newss = new ArrayList<>();
        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
            News news = new News();
            news.setId(result.getInt(0));
            news.setTitle(result.getString(1));
            news.setDate(result.getString(2));
            news.setLink(result.getString(3));
            news.setWebsiteID(result.getInt(4));
            news.setImageAddress(result.getString(5));
            news.setBody(result.getString(6));
            newss.add(news);
        }
        return newss;
    }

    public ArrayList<News> getNews(Category category) {
        ArrayList<News> news = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("select id from " + WEBSITE_TABLE_NAME + " where categoryID == " + category.getId() + ";", null);
        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
            news.addAll(getNews(result.getInt(0)));
        }
        return news;
    }

    public ArrayList<News> getNews(String categoryName) {
        Category category = new Category();
        category.setTitle(categoryName);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("select id from " + CATEGORY_TABLE_NAME + " where title == " + category.getTitle() + ";", null);
        result.moveToFirst();
        category.setId(result.getInt(0));
        return getNews(category);
    }

    public void deleteNews(int webID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + NEWS_TABLE_NAME + " where websiteID == " + webID + ";");
    }

    public void deleteNews(Category category) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("select id from " + WEBSITE_TABLE_NAME + " where categoryID == " + category.getId() + ";", null);
        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
            deleteNews(result.getInt(0));
        }
    }

    public void deleteNews(String categoryName) {
        Category category = new Category();
        category.setTitle(categoryName);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("select id from " + CATEGORY_TABLE_NAME + " where title == " + category.getTitle() + ";", null);
        result.moveToFirst();
        category.setId(result.getInt(0));
        deleteNews(category);
    }


}
