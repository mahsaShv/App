package com.example.mobileproject01;

import android.os.SystemClock;
import android.widget.LinearLayout;


import org.apache.http.ParseException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;

public class ConnectionManager {
    RSSParser rssParser = new RSSParser();
    private static String TAG_TITLE = "title";
    private static String TAG_LINK = "link";
    private static String TAG_PUB_DATE = "pubDate";


    ConnectionManager() {
    }

//    Worker cloud = new Worker("ConnectionManager Thread");
//
//
//    void funcOnAnotherThread() {
//        final CountDownLatch countDownLatch = new CountDownLatch(1);
//
//
//        Runnable task = new Runnable() {
//            @Override
//            public void run() {
//
//
//
//                //TODO
//                countDownLatch.countDown();
//
//
//            }
//
//        };
//
//        cloud.postRunnable(task);
//        try {
//            countDownLatch.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//
//    }

    void getNews(ArrayList<Website> websites, List<News> rssItems, ArrayList<HashMap<String, String>> rssItemList) {
        // rss link url
        //TODO hame site a?
        String rss_url = websites.get(0).getURL();
        // list of rss items
        rssItems = rssParser.getRSSFeedItems(rss_url);
        // looping through each item
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
    }

}


