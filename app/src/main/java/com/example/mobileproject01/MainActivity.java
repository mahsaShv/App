package com.example.mobileproject01;

import android.content.Context;
import android.content.res.Configuration;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    NotificationCenter notificationCenter=new NotificationCenter();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

    }


    @Override
    public void update() {


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
