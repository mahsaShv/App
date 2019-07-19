package com.example.mobileproject01;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.apache.http.ParseException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

import static android.content.Context.LOCATION_SERVICE;

public class ConnectionManager {
    RSSParser rssParser = new RSSParser();
    private static String TAG_TITLE = "title";
    private static String TAG_LINK = "link";
    private static String TAG_PUB_DATE = "pubDate";
    String city;
    Context context;
    private FusedLocationProviderClient fusedLocationClient;


    ConnectionManager(Context context) {
        this.context = context;
    }

    Worker cloud = new Worker("ConnectionManager Thread");


    void funcOnAnotherThread() {
        final CountDownLatch countDownLatch = new CountDownLatch(1);


        Runnable task = new Runnable() {
            @Override
            public void run() {
                city = getLocationCity();

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

    void getNews(ArrayList<Website> websites, List<News> rssItems, ArrayList<HashMap<String, String>> rssItemList) {

        funcOnAnotherThread();


        if (websites.size() == 0)
            return;


        int categoryID = websites.get(0).getCategoryID();
        List<News> temp;


        if (categoryID == 5) {
            for (int i = 0; i < websites.size(); i++) {
                String rss_url = websites.get(i).getURL().replace("text", city);

                System.out.println(rss_url);
                temp = rssParser.getRSSFeedItems(rss_url);

                for (int j = 0; j < temp.size(); j++) {
                    temp.get(j).setWebsiteID(websites.get(i).getId());
                }
                rssItems.addAll(temp);
            }
        } else {
            for (int i = 0; i < websites.size(); i++) {
                String rss_url = websites.get(i).getURL();
                temp = rssParser.getRSSFeedItems(rss_url);

                for (int j = 0; j < temp.size(); j++) {
                    temp.get(j).setWebsiteID(websites.get(i).getId());
                }
                rssItems.addAll(temp);
            }
        }


        for (int i = 0; i < rssItems.size(); i++) {
            rssItems.get(i).setId(categoryID * 10000 + i);
        }


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


    private String getLocationCity() {


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show();

        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {

                            double lat = location.getLatitude();
                            double lng = location.getLongitude();
                            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                            try {


                                List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);

                                city = addresses.get(0).getAddressLine(0);
                                city = city.split(" ")[0];
                            } catch (IOException e) {

                                System.out.println(e.getStackTrace());
                            }


                        }
                    }
                });


        return city;

    }


}


