package com.example.mobileproject01;

import android.content.Context;
import android.content.res.Configuration;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Observer {

    NotificationCenter notificationCenter = new NotificationCenter();
    private RecyclerView topicsRecyclerView;
    private RecyclerView.Adapter topicsMAdapter;
    private RecyclerView.LayoutManager topicsLayoutManager;

    private RecyclerView newsRecyclerView;
    private RecyclerView.Adapter newsMAdapter;
    private RecyclerView.LayoutManager newsLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ArrayList<Topic> topics = new ArrayList<Topic>();
        ArrayList<News> news = new ArrayList<News>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        topicsRecyclerView = (RecyclerView) findViewById(R.id.topics);
        topicsRecyclerView.setHasFixedSize(false);

        topicsLayoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) topicsLayoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
        topicsRecyclerView.setLayoutManager(topicsLayoutManager);

        topicsMAdapter = new TopicsAdapter(topics);
        topicsRecyclerView.setAdapter(topicsMAdapter);


        newsRecyclerView = (RecyclerView) findViewById(R.id.news);
        newsRecyclerView.setHasFixedSize(false);

        newsLayoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) newsLayoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        newsRecyclerView.setLayoutManager(newsLayoutManager);

        newsMAdapter = new MyAdapter(news);
        newsRecyclerView.setAdapter(newsMAdapter);


//        AppBarConfiguration appBarConfiguration =
    }


    @Override
    public void update() {


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
