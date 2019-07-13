package com.example.mobileproject01;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;
import android.widget.LinearLayout;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MessageController {

    NotificationCenter notificationCenter;

    private static MessageController SINGLE_INSTANCE = null;

    public static MessageController getInstance(Context context) {
        if (SINGLE_INSTANCE == null) {
            synchronized (MessageController.class) {
                SINGLE_INSTANCE = new MessageController(context);
            }
        }
        return SINGLE_INSTANCE;
    }


    Context context;
    ConnectionManager connectionManager;
    StorageManager storageManager;

    private MessageController(Context context) {
        this.context = context;
        storageManager = StorageManager.getInstance(context);
        connectionManager = new ConnectionManager();

    }

    public ArrayList<News> getNews(Category category) {
        ArrayList<Website> websites  = storageManager.getWebsites(category);


        return storageManager.getNews(category);
        //todo: get news from server in another thread and return news from database until the news are ready
    }

    //TODO from storage or connection?


}