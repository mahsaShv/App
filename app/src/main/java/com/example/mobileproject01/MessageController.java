package com.example.mobileproject01;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

        if (isConnectedToNetwork(context)){

        }

        return storageManager.getNews(category);


    }


    public boolean isConnectedToNetwork(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean isConnected = false;
        if (connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            isConnected = (activeNetwork != null) && (activeNetwork.isConnectedOrConnecting());
        }

        return isConnected;
    }




}