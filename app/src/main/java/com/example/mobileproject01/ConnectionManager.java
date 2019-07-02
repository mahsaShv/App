package com.example.mobileproject01;

import android.os.SystemClock;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class ConnectionManager {

    ConnectionManager() {

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


