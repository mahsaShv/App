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


}
