package com.example.mobileproject01;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import org.apache.http.ParseException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class RSSFeedActivity extends ListActivity {
    private ProgressBar pDialog;
    ArrayList<HashMap<String, String>> rssItemList = new ArrayList<>();
    RSSParser rssParser = new RSSParser();
    List<News> rssItems = new ArrayList<>();
    private static String TAG_TITLE = "title";
    private static String TAG_LINK = "link";
    private static String TAG_PUB_DATE = "pubDate";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rssfeed);


        String rss_link = getIntent().getStringExtra("rssLink");
        new LoadRSSFeedItems().execute(rss_link);
        ListView lv = getListView();


    }


    public class LoadRSSFeedItems extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressBar(RSSFeedActivity.this, null, android.R.attr.progressBarStyleLarge);
            RelativeLayout relativeLayout = findViewById(R.id.relativeLayout);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );

            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            pDialog.setLayoutParams(layoutParams);
            pDialog.setVisibility(View.VISIBLE);
            relativeLayout.addView(pDialog);
        }

        @Override
        protected String doInBackground(String... args) {
            // rss link url
            String rss_url = args[0];
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

            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    ListAdapter adapter = new SimpleAdapter(
                            RSSFeedActivity.this,
                            rssItemList, R.layout.rss_item_list_row,
                            new String[]{TAG_LINK, TAG_TITLE, TAG_PUB_DATE},
                            new int[]{R.id.page_url, R.id.title, R.id.pub_date});

                    // updating listview
                    setListAdapter(adapter);
                }
            });
            return null;
        }

        protected void onPostExecute(String args) {
            pDialog.setVisibility(View.GONE);
        }
    }
}
