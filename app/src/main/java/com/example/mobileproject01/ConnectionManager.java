package com.example.mobileproject01;

import android.Manifest;
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
import android.support.v4.content.ContextCompat;
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

import static android.content.Context.LOCATION_SERVICE;

public class ConnectionManager implements LocationListener {
    RSSParser rssParser = new RSSParser();
    private static String TAG_TITLE = "title";
    private static String TAG_LINK = "link";
    private static String TAG_PUB_DATE = "pubDate";
    private LocationManager locationManager;
    private String provider;
    String city;
    Context context;


    ConnectionManager(Context context) {
        this.context = context;
    }

    Worker cloud = new Worker("ConnectionManager Thread");


    void funcOnAnotherThread() {
        final CountDownLatch countDownLatch = new CountDownLatch(1);


        Runnable task = new Runnable() {
            @Override
            public void run() {
//                System.out.println("city:   "+getLocationCity());




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

    void getNews(ArrayList<Website> websites, List<News> rssItems, ArrayList<HashMap<String, String>> rssItemList) {

        funcOnAnotherThread();


//        if (websites.size() == 0 )
//            return;

//        System.out.println(getLocationCity()+"mahsaaaaa");


        int categoryID = websites.get(0).getCategoryID();
        List<News> temp;


        if ( categoryID == 5 ){
            for (int i = 0; i <websites.size(); i++){
                String rss_url = websites.get(i).getURL().replace("text" , city);

                System.out.println(rss_url);
                temp = rssParser.getRSSFeedItems(rss_url);

                for (int j = 0; j < temp.size(); j++) {
                    temp.get(j).setWebsiteID(websites.get(i).getId());
                }
                rssItems.addAll(temp);
            }
        }

        else{
            for (int i = 0; i <websites.size(); i++){
                String rss_url = websites.get(i).getURL();
                temp = rssParser.getRSSFeedItems(rss_url);

                for (int j = 0; j < temp.size(); j++) {
                    temp.get(j).setWebsiteID(websites.get(i).getId());
                }
                rssItems.addAll(temp);
            }
        }







        for(int i = 0; i < rssItems.size(); i++) {
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

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }



    private String getLocationCity() {
        locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        Location location = null;
        try {

//            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE); //<-- NEW CODE
//            locationManager.requestLocationUpdates();






            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
//            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            location = getLastKnownLocation();

//            location = locationManager.getLastKnownLocation(provider);
        } catch (SecurityException e) {
            e.getStackTrace();
        }
        city = "";
        if (location != null) {



            double lat = location.getLatitude();
            double lng = location.getLongitude();
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
//            System.out.println("mahsaaaaaa" +addresses.size());
            try {


                List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
//                System.out.println("mahsaaaaaa" +addresses.size());


                city = addresses.get(0).getAddressLine(0);
            } catch (IOException e) {
//                System.out.println("mahsaaaaaa");
//                System.out.println(e.getStackTrace());
            }

        }

        return city;

    }

    private Location getLastKnownLocation() {
        Location l=null;
        LocationManager mLocationManager = (LocationManager)context.getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
                l = mLocationManager.getLastKnownLocation(provider);
            }
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = l;
            }
        }
        return bestLocation;
    }




}


