package com.example.mobileproject01;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.SystemClock;
import android.util.Log;
import android.widget.LinearLayout;

import org.apache.http.ParseException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MessageController {

    private static String TAG_TITLE = "title";
    private static String TAG_LINK = "link";
    private static String TAG_PUB_DATE = "pubDate";

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

    public void getNews(Category category , List<News> rssItems, ArrayList<HashMap<String, String>> rssItemList ) {
        ArrayList<Website> websites  = storageManager.getWebsites(category);
        rssItemList.clear();
        rssItems.clear();

        if (isConnectedToNetwork(context)){
            connectionManager.getNews(websites, rssItems, rssItemList);

            ArrayList<News> news = new ArrayList<>();
            news.addAll(rssItems);
            storageManager.updateNews(news, category);
            return;

        }

        else {
            rssItems.addAll(storageManager.getNews(category));

            for (final News item : rssItems) {
                // creating new HashMap
                if (item.getLink().toString().equals(""))
                    break;
                HashMap<String, String> map = new HashMap<String, String>();

                // adding each child node to HashMap key => value
                String givenDateString = item.getDate().trim();
                SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
                try {
                    Date mDate = sdf.parse(givenDateString);
                    SimpleDateFormat sdf2 = new SimpleDateFormat("EEEE, dd MMMM yyyy - hh:mm a", Locale.US);
                    item.setDate(sdf2.format(mDate));

                } catch (ParseException | java.text.ParseException e) {
                    e.printStackTrace();

                }

                map.put(TAG_TITLE, item.getTitle());
                map.put(TAG_LINK, item.getLink());
                map.put(TAG_PUB_DATE, item.getDate());
                // adding HashList to ArrayList
                rssItemList.add(map);
            }
            return;
        }




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