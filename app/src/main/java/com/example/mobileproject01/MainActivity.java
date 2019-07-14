package com.example.mobileproject01;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.http.ParseException;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;

import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements Observer {

    NotificationCenter notificationCenter = new NotificationCenter();
    MessageController messageController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

//        messageController = MessageController.getInstance(this);
//        messageController.connectionManager.funcOnAnotherThread();
        ArrayList<String> rssLinks = new ArrayList<>();

//        rssLinks.add("https://www.aljazeera.com/xml/rss/all.xml");
//        rssLinks.add("https://www.yahoo.com/news/world/rss");
//        rssLinks.add("http://www.cinemablend.com/rss_review.php");
        rssLinks.add("https://www.theguardian.com/world/rss");
//        rssLinks.add("https://news.google.com/rss/topics/CAAqJggKIiBDQkFTRWdvSUwyMHZNRGx1YlY4U0FtVnVHZ0pWVXlnQVAB?hl=en-US&gl=US&ceid=US:en");
//        rssLinks.add("https://www.euronews.com/rss?level=theme&name=news");

        Intent intent = new Intent(getApplicationContext(), RSSFeedActivity.class);

        intent.putExtra("rssLink", rssLinks.get(0));
        startActivity(intent);
    }


    @Override
    public void update() {


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
