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
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
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


    HashMap<String, List<String>> getAllCategoriesAndWebsites() {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final HashMap<String, List<String>> result = new HashMap<>();

        storage.postRunnable(new Runnable() {
            @Override
            public void run() {
                ArrayList<Category> categories = databaseManager.getAllCategories();
                for (Category c :
                        categories) {
                    result.put(c.getTitle(), new ArrayList<>());
                    ArrayList<Website> websites = databaseManager.getAllWebsites(c.getId());
                    for (Website w:
                         websites) {
                        result.get(c.getTitle()).add(w.getTitle()+w.getIsSelected());
                    }
                }
                countDownLatch.countDown();

            }
        });


        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return result;
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

    void unsaveNews(News news) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);


        storage.postRunnable(new Runnable() {
            @Override
            public void run() {

                databaseManager.unsaveNews(news);
                countDownLatch.countDown();

            }
        });


        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    void setTheme(int theme) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);


        storage.postRunnable(new Runnable() {
            @Override
            public void run() {

                databaseManager.deleteTheme();
                databaseManager.insertTheme(theme);
                countDownLatch.countDown();

            }
        });


        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    int getTheme() {
        final CountDownLatch countDownLatch = new CountDownLatch(1);

        return databaseManager.getTheme();

    }



    ArrayList<News> getSavedNews() {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final ArrayList<News> news = new ArrayList<>();

        storage.postRunnable(new Runnable() {
            @Override
            public void run() {

                news.addAll(databaseManager.getSavedNews());
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

    ArrayList<Website> getAllWebsites() {
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
        return websites;

    }



    boolean categoryTableIsEmpty() {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        ArrayList<Category> categories = new ArrayList<>();

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
        return categories.size() == 0;

    }

    boolean websiteTableIsEmpty() {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        ArrayList<Website> websites = new ArrayList<>();

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
        return websites.size() == 0;

    }

    void fillNewsIsSaved(ArrayList<News> news) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);


        storage.postRunnable(new Runnable() {
            @Override
            public void run() {

                databaseManager.fillNewsSaved(news);
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
        for (Category c :
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
        for (Website w :
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


    void changeWebsiteStatus(String name) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);


        storage.postRunnable(new Runnable() {
            @Override
            public void run() {

                databaseManager.changeWebsiteStatus(name);
                countDownLatch.countDown();

            }
        });


        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void saveNews(News news) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);


        storage.postRunnable(new Runnable() {
            @Override
            public void run() {

                databaseManager.saveNews(news);
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
