package com.example.mobileproject01;

import android.app.ListActivity;
import android.content.ContentValues;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NavUtils;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.apache.http.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

//import java.text.ParseException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class RSSFeedActivity extends AppCompatActivity implements Observer, NavigationView.OnNavigationItemSelectedListener {

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.edit) {
            // Handle the camera action
        } else if (id == R.id.saved) {

        } else if (id == R.id.navCategories) {
            Intent i = new Intent(this, CategroiesActivity.class);
            startActivity(i);
        } else if (id == R.id.share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private ProgressBar pDialog;
    ArrayList<HashMap<String, String>> rssItemList = new ArrayList<>();
    NotificationCenter notificationCenter = new NotificationCenter();

    List<News> rssItems = new ArrayList<>();
    private static String TAG_TITLE = "title";
    private static String TAG_LINK = "link";
    private static String TAG_PUB_DATE = "pubDate";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private DividerItemDecoration dividerItem;
    private Category shown_category = new Category();
    private LocationManager locationManager;
    private String provider;
    SwipeRefreshLayout swipeRefreshLayout;

    private ListView lv;

    MessageController messageController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rssfeed);


        lv = (ListView) findViewById(android.R.id.list);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);


        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
//                        countDownLatch = new CountDownLatch(1);
                        doYourUpdate();
                    }
                }
        );


//        System.out.println("city:      " + getLocationCity());


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Menu menu = navigationView.getMenu();
        MenuItem others = menu.findItem(R.id.other);
        SpannableString s = new SpannableString(others.getTitle());
        s.setSpan(new TextAppearanceSpan(this, R.style.TextAppearance), 0, s.length(), 0);
        others.setTitle(s);
        navigationView.setNavigationItemSelectedListener(this);

        messageController = MessageController.getInstance(this);
        notificationCenter.register(this);

        if (messageController.storageManager.categoryTableIsEmpty()) {
            readCategoriesFromFile(this);
        }
        if (messageController.storageManager.websiteTableIsEmpty()) {
            readWebsitesFromFile(this);
        }

        shown_category.setId(8);
        shown_category.setTitle("Cinema");
        shown_category.setIsSelected(1);


        ArrayList<Category> categories = new ArrayList<>();
        Icon.createWithFilePath("drawable-v24/rss.png");
        categories = messageController.storageManager.getValidCategories();

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        recyclerView.setHasFixedSize(false);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new CategoryAdapter(getApplicationContext(), categories, shown_category, notificationCenter);
        recyclerView.setAdapter(mAdapter);
        dividerItem = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.HORIZONTAL);
        recyclerView.addItemDecoration(dividerItem);


        new LoadRSSFeedItems().execute("");
        ListView lv = (ListView) findViewById(android.R.id.list);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent in = new Intent(getApplicationContext(), WebActivity.class);
                String page_url = ((TextView) view.findViewById(R.id.page_url)).getText().toString().trim();
                in.putExtra("url", page_url);
                startActivity(in);
            }
        });

        registerForContextMenu(lv);

//        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                registerForContextMenu(lv);
//                return false;
//            }
//        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        if (v.getId() == android.R.id.list) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            String[] menuItems = getResources().getStringArray(R.array.menu);
            for (int i = 0; i < menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }
        }

//        getMenuInflater().inflate(R.menu.news_floating_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        String[] menuItems = getResources().getStringArray(R.array.menu);
        String menuItemName = menuItems[menuItemIndex];
        News listItemNews = rssItems.get(info.position);


        switch (item.getItemId()) {
            case R.id.share_news:
                Toast.makeText(this, "Shared" + listItemNews.getLink(), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.save_news:
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void doYourUpdate() {
        update();

        pDialog.setVisibility(View.GONE);

    }

    private void readWebsitesFromFile(Context context) {
        final Resources resources = context.getResources();
        messageController.storageManager.deleteWebsites();
        InputStream inputStream = resources.openRawResource(R.raw.websites);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String line = bufferedReader.readLine();
            while (line != null) {
                messageController.storageManager.addWebsiteToDatabase(line);
                line = bufferedReader.readLine();
            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        } finally {
            try {
                bufferedReader.close();
            } catch (Exception e) {
                System.out.println(e.getStackTrace());
            }

        }
    }

//    private String getLocationCity() {
//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        Criteria criteria = new Criteria();
//        provider = locationManager.getBestProvider(criteria, false);
//        Location location = null;
//        try {
//            location = locationManager.getLastKnownLocation(provider);
//        } catch (SecurityException e) {
//            e.getStackTrace();
//        }
//        String city = "";
//        if (location != null) {
//            double lat = location.getLatitude();
//            double lng = location.getLongitude();
//            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
//            try {
//                List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
//                city = addresses.get(0).getAddressLine(0);
//            } catch (IOException e) {
//                e.getStackTrace();
//            }
//
//        }
//
//        return city;
//
//    }

    private void readCategoriesFromFile(Context context) {
        final Resources resources = context.getResources();
        messageController.storageManager.deleteCategories();
        InputStream inputStream = resources.openRawResource(R.raw.categories);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String line = bufferedReader.readLine();
            while (line != null) {
                messageController.storageManager.addCategoryToDatabase(line);
                line = bufferedReader.readLine();
            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        } finally {
            try {
                bufferedReader.close();
            } catch (Exception e) {
                System.out.println(e.getStackTrace());
            }

        }
    }

    @Override
    public void update() {
        new LoadRSSFeedItems().execute("");
        lv = (ListView) findViewById(android.R.id.list);

        registerForContextMenu(lv);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent in = new Intent(getApplicationContext(), WebActivity.class);
                String page_url = ((TextView) view.findViewById(R.id.page_url)).getText().toString().trim();
                in.putExtra("url", page_url);
                startActivity(in);
            }
        });

    }


    public class LoadRSSFeedItems extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressBar(RSSFeedActivity.this, null, android.R.attr.progressBarStyleLarge);
            pDialog.getIndeterminateDrawable().setColorFilter(0xFFd65a31, android.graphics.PorterDuff.Mode.MULTIPLY);


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

            messageController.getNews(shown_category, rssItems, rssItemList);
            System.out.println("category :      " + shown_category.getTitle());


            // updating UI from Background Thread


            // rss link url
//            String rss_url = args[0];
//            // list of rss items
//            RSSParser rssParser = new RSSParser();
//            rssItems = rssParser.getRSSFeedItems(rss_url);
//            // looping through each item
//            for (final News item : rssItems) {
//                // creating new HashMap
//                if (item.getLink().toString().equals(""))
//                    break;
//                HashMap<String, String> map = new HashMap<String, String>();
//
//                // adding each child node to HashMap key => value
//                String givenDateString = item.getDate().trim();
//                SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
//                try {
//                    Date mDate = sdf.parse(givenDateString);
//                    SimpleDateFormat sdf2 = new SimpleDateFormat("EEEE, dd MMMM yyyy - hh:mm a", Locale.US);
//                    item.setDate(sdf2.format(mDate));
//
//                } catch (ParseException | java.text.ParseException e) {
//                    e.printStackTrace();
//
//                }
//
//                map.put(TAG_TITLE, item.getTitle());
//                map.put(TAG_LINK, item.getLink());
//                map.put(TAG_PUB_DATE, item.getDate());
//                // adding HashList to ArrayList
//                rssItemList.add(map);
//            }

            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    ListAdapter adapter = new SimpleAdapter(
                            RSSFeedActivity.this,
                            rssItemList, R.layout.rss_item_list_row,
                            new String[]{TAG_LINK, TAG_TITLE, TAG_PUB_DATE},
                            new int[]{R.id.page_url, R.id.title, R.id.pub_date});

                    // updating listview
//                    setListAdapter(adapter);
                    lv.setAdapter(adapter);
                }
            });

//            countDownLatch.countDown();
            swipeRefreshLayout.setRefreshing(false);


            return null;
        }

        protected void onPostExecute(String args) {
            pDialog.setVisibility(View.GONE);
        }


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        messageController.storageManager.deleteCategories();
//        messageController.storageManager.deleteWebsites();
    }
}
