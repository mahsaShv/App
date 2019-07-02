package com.example.mobileproject01;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.util.concurrent.CountDownLatch;

public class Worker extends Thread {


    private volatile Handler handler = null;
    private CountDownLatch syncLatch = new CountDownLatch(1);

    public Worker(final String threadName) {
        setName(threadName);
        start();
    }

    public void sendMessage(Message msg, int delay) {
        try {
            syncLatch.await();
            if (delay <= 0) {
                handler.sendMessage(msg);
            } else {
                handler.sendMessageDelayed(msg, delay);
            }
        } catch (Exception e) {
            Log.i("dispatch thread", e.toString());
        }
    }

    public void cancelRunnable(Runnable runnable) {
        try {
            syncLatch.await();
            handler.removeCallbacks(runnable);
        } catch (Exception e) {
            Log.i("dispatch thread", e.toString());
        }
    }

    public void postRunnable(Runnable runnable) {
        postRunnable(runnable, 0);
    }

    public void postRunnable(Runnable runnable, long delay) {
        try {
            syncLatch.await();
            if (delay <= 0) {
                handler.post(runnable);
            } else {
                handler.postDelayed(runnable, delay);
            }
        } catch (Exception e) {
            Log.i("dispatch thread", e.toString());
        }
    }

    public void cleanupQueue() {
        try {
            syncLatch.await();
            handler.removeCallbacksAndMessages(null);
        } catch (Exception e) {
            Log.i("dispatch thread", e.toString());
        }
    }

    public void handleMessage(Message inputMessage) {
        System.out.println("msg handled");
    }


    @Override
    public void run() {
        Looper.prepare();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Worker.this.handleMessage(msg);
            }
        };
        syncLatch.countDown();
        Looper.loop();
    }


}
