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
    public static String CATEGORY_TABLE_NAME = "categories";
    public static String WEBSITE_TABLE_NAME = "websites";
    public static String NEWS_TABLE_NAME = "news";
    public static String SAVED_NEWS_TABLE_NAME = "savedNews";
    public static String USER_TABLE_NAME = "users";
    public static String THEME_TABLE_NAME = "theme";

    public static DatabaseManager getInstance(Context context) {
        if (SINGLE_INSTANCE == null) {
            synchronized (DatabaseManager.class) {
                SINGLE_INSTANCE = new DatabaseManager(context);
            }
        }
        return SINGLE_INSTANCE;
    }

    private DatabaseManager(Context context) {
        super(context, DB_NAME, null, 2);
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("create table if not exists " + CATEGORY_TABLE_NAME + " (id integer primary key autoincrement, title varchar(50), isSelected int);");
        db.execSQL("create table if not exists " + WEBSITE_TABLE_NAME + " (id integer primary key autoincrement, title varchar(50), categoryID int, url varchar(100), isSelected int);");
        db.execSQL("create table if not exists " + NEWS_TABLE_NAME + " (id integer primary key, title varchar(100), date varchar(50), link varchar(100), websiteID int, imageAddress varchar(100), body varchar(500));");
        db.execSQL("create table if not exists " + SAVED_NEWS_TABLE_NAME + " (id integer primary key autoincrement, title varchar(100), date varchar(10), link varchar(100), websiteID int, imageAddress varchar(100), body varchar(500));");
        db.execSQL("create table if not exists " + USER_TABLE_NAME + " (id integer primary key autoincrement, username varachar(100), password int, emailAddress varchar(100), isInUse int);");
        db.execSQL("create table if not exists " + THEME_TABLE_NAME + " (id integer primary key);");

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

    public void insertUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", user.getUserName());
        contentValues.put("password", user.getPassword());
        contentValues.put("emailAddress", user.getEmailAddress());
        db.insert(USER_TABLE_NAME, null, contentValues);
        contentValues.clear();
    }

    public void deleteTheme() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + THEME_TABLE_NAME + ";");
    }

    public void insertTheme(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        db.insert(THEME_TABLE_NAME, null, contentValues);
        contentValues.clear();
    }

    public int getTheme() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("select * from " + THEME_TABLE_NAME + ";", null);
        int theme;
        if(result.getCount() == 0) {
            insertTheme(1);
        }
        result = db.rawQuery("select * from " + THEME_TABLE_NAME + ";", null);
        result.moveToFirst();
        theme = result.getInt(0);

        return theme;
    }

    public void reinsertUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        deleteUser(user);
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", user.getId());
        contentValues.put("username", user.getUserName());
        contentValues.put("password", user.getPassword());
        contentValues.put("emailAddress", user.getEmailAddress());
        db.insert(USER_TABLE_NAME, null, contentValues);
        contentValues.clear();
    }

    public UserState checkPassword(String name, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("select * from " + USER_TABLE_NAME + " where username == " + name + " or emailAddress == " + name + ";", null);
        if (result.getCount() == 0) {
            return UserState.DOES_NOT_EXIST;
        }
        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
            int pass = result.getInt(1);
            if (password.hashCode() == pass) {
                return UserState.VALID;
            }
        }
        return UserState.DOES_NOT_EXIST;

    }

    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + USER_TABLE_NAME + " where username == " + "\"" + user.getUserName() + "\"" + ";");
    }

    public void changeInUseUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + USER_TABLE_NAME + " where isInUse == 1;", null);
        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
            User resUser = new User();
            resUser.setId(result.getInt(0));
            resUser.setUserName(result.getString(1));
            resUser.setPassword(result.getInt(2));
            resUser.setEmailAddress(result.getString(3));
            resUser.setIsInUse(0);
            reinsertUser(resUser);

        }
        result = db.rawQuery("select * from " + USER_TABLE_NAME + " where username == " + "\"" + user.getUserName() + "\";", null);
        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
            User resUser = new User();
            resUser.setId(result.getInt(0));
            resUser.setUserName(result.getString(1));
            resUser.setPassword(result.getInt(2));
            resUser.setEmailAddress(result.getString(3));
            resUser.setIsInUse(1);
            reinsertUser(resUser);
        }

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

    public void changeWebsiteStatus(String websiteName) {
        ArrayList<Website> websites = getWebsites(websiteName);
        deleteWebsites(websites);
        for (Website w:
             websites) {
            w.setIsSelected(w.getIsSelected() == 0? 1 : 0);
        }
        insertWebsites(websites);


    }


    public void deleteCategories() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + CATEGORY_TABLE_NAME + ";");
    }

    public ArrayList<Category> getCategories() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("select * from " + CATEGORY_TABLE_NAME + " where isSelected == 1;", null);
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

    public ArrayList<Category> getAllCategories() {
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
        db.execSQL("delete from " + WEBSITE_TABLE_NAME + " where id == " + website.getId() + ";");
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

    public void deleteWebsites(ArrayList<Website> websites) {
        for (Website w:
             websites) {
            deleteWebsite(w);
        }
    }

    public ArrayList<Website> getAllWebsites() {
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

    public ArrayList<Website> getAllWebsites(int categoryID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("select * from " + WEBSITE_TABLE_NAME + " where categoryID == " + categoryID + ";", null);
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

    public ArrayList<Website> getWebsites(Category category) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("select * from " + WEBSITE_TABLE_NAME + " where categoryID == " + category.getId() + " and isSelected == 1;", null);
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

    public ArrayList<Website> getWebsites(String websiteName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("select * from " + WEBSITE_TABLE_NAME + " where title == " + "\"" + websiteName + "\"" + ";", null);
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

    public void saveNews(News news) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", news.getId());
        contentValues.put("title", news.getTitle());
        contentValues.put("date", news.getDate());
        contentValues.put("link", news.getLink());
        contentValues.put("websiteID", news.getWebsiteID());
        contentValues.put("imageAddress", news.getImageAddress());
        contentValues.put("body", news.getBody());
        db.insert(SAVED_NEWS_TABLE_NAME, null, contentValues);

        contentValues.clear();
    }

    public void fillNewsSaved(ArrayList<News> news) {
        SQLiteDatabase db = this.getReadableDatabase();
        for (News n:
                news) {
            Cursor savedRes = db.rawQuery("select * from " + SAVED_NEWS_TABLE_NAME + " where title == " + "\"" + n.getTitle() + "\";", null);
            n.setSaved(savedRes.getCount() > 0);
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
        fillNewsSaved(newss);
        return newss;
    }

    public ArrayList<News> getSavedNews() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("select * from " + SAVED_NEWS_TABLE_NAME + ";", null);
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
        Cursor result = db.rawQuery("select id from " + WEBSITE_TABLE_NAME + " where categoryID == " + category.getId() + " and isSelected == 1;", null);
        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
            news.addAll(getNews(result.getInt(0)));
        }
        return news;
    }

    public ArrayList<News> getNews(String categoryName) {
        Category category = new Category();
        category.setTitle(categoryName);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("select id from " + CATEGORY_TABLE_NAME + " where title == " + category.getTitle() + " and isSelected == 1;", null);
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
