package com.example.mobileproject01;

import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class StorageManager {

    private static StorageManager SINGLE_INSTANCE = null;
    public DatabaseManager databaseManager;


    public static StorageManager getInstance(Context context) {
        if (SINGLE_INSTANCE == null) {
            synchronized (StorageManager.class) {
                SINGLE_INSTANCE = new StorageManager(context);
            }
        }
        return SINGLE_INSTANCE;
    }

    Worker storage = new Worker("StorageManager Thread");
    Context context;

    private StorageManager(Context context) {
        this.context = context;
        databaseManager = DatabaseManager.getInstance(context);
    }


    void funcOnAnotherThread() {
        final CountDownLatch countDownLatch = new CountDownLatch(1);


        storage.postRunnable(new Runnable() {
            @Override
            public void run() {

                //TODO
                countDownLatch.countDown();

            }
        });


        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    ArrayList<Category> getValidCategories() {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final ArrayList<Category> categories = new ArrayList<>();

        storage.postRunnable(new Runnable() {
            @Override
            public void run() {

                categories.addAll(databaseManager.getCategories());
                countDownLatch.countDown();

            }
        });


        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return categories;
    }

    void updateNews(ArrayList<News> news, Category category) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);


        storage.postRunnable(new Runnable() {
            @Override
            public void run() {

                databaseManager.deleteNews(category);
                databaseManager.insertNews(news);
                countDownLatch.countDown();

            }
        });


        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    ArrayList<Website> getWebsites(Category category) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final ArrayList<Website> websites = new ArrayList<>();

        storage.postRunnable(new Runnable() {
            @Override
            public void run() {

                websites.addAll(databaseManager.getWebsites(category));

                countDownLatch.countDown();

            }
        });


        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return websites;

    }

    ArrayList<News> getNews(Category category) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final ArrayList<News> news = new ArrayList<>();

        storage.postRunnable(new Runnable() {
            @Override
            public void run() {


                news.addAll(databaseManager.getNews(category));

                countDownLatch.countDown();

            }
        });


        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return news;

    }

    String getCategoriesAsString() {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final ArrayList<Category> categories = new ArrayList<>();

        storage.postRunnable(new Runnable() {
            @Override
            public void run() {

                categories.addAll(databaseManager.getAllCategories());
                countDownLatch.countDown();

            }
        });


        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String all = "";
        for (Category c:
             categories) {
            all += c.getId() + " " + c.getTitle() + " " + c.getIsSelected() + "\n";
        }
        return all;
    }

    String getWebsitesAsString() {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final ArrayList<Website> websites = new ArrayList<>();

        storage.postRunnable(new Runnable() {
            @Override
            public void run() {

                websites.addAll(databaseManager.getAllWebsites());
                countDownLatch.countDown();

            }
        });


        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String all = "";
        for (Website w:
                websites) {
            all += w.getId() + " " + w.getTitle() + " " + w.getCategoryID() + " " + w.getURL() + " " + w.getIsSelected() + "\n";
        }
        return all;
    }

    void addCategoryToDatabase(String str) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        String[] props = str.split("\\s");
        final Category category = new Category();
        category.setId(Integer.parseInt(props[0]));
        category.setTitle(props[1]);
        category.setIsSelected(Integer.parseInt(props[2]));

        storage.postRunnable(new Runnable() {
            @Override
            public void run() {
                databaseManager.insertCategory(category);
                countDownLatch.countDown();

            }
        });


        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    void deleteWebsites() {
        final CountDownLatch countDownLatch = new CountDownLatch(1);


        storage.postRunnable(new Runnable() {
            @Override
            public void run() {

                databaseManager.deleteWebsites();
                countDownLatch.countDown();

            }
        });


        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void deleteCategories() {
        final CountDownLatch countDownLatch = new CountDownLatch(1);


        storage.postRunnable(new Runnable() {
            @Override
            public void run() {

                databaseManager.deleteCategories();
                countDownLatch.countDown();

            }
        });


        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void addWebsiteToDatabase(String str) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        String[] props = str.split("\\s");
        final Website website = new Website();
        website.setId(Integer.parseInt(props[0]));
        website.setTitle(props[1]);
        website.setCategoryID(Integer.parseInt(props[2]));
        website.setURL(props[3]);
        website.setIsSelected(Integer.parseInt(props[4]));

        storage.postRunnable(new Runnable() {
            @Override
            public void run() {
                databaseManager.insertWebsite(website);
                countDownLatch.countDown();

            }
        });


        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


}
