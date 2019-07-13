package com.example.mobileproject01;

import android.os.SystemClock;
import android.widget.LinearLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class ConnectionManager {

//    ArrayList<String> times = new ArrayList<>();
//    ArrayList<String> topics = new ArrayList<>();
//    ArrayList<String> titles = new ArrayList<>();


    ArrayList<String> rssLinks = new ArrayList<>();


    ConnectionManager() {

//        rssLinks.add("http://www.rediff.com/rss/moviesreviewsrss.xml");
//        rssLinks.add("http://www.cinemablend.com/rss_review.php");

    }

    Worker cloud = new Worker("ConnectionManager Thread");


    void funcOnAnotherThread() {
        final CountDownLatch countDownLatch = new CountDownLatch(1);


        Runnable task = new Runnable() {
            @Override
            public void run() {



                //TODO
                countDownLatch.countDown();


            }

        };
        cloud.postRunnable(task);
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}


