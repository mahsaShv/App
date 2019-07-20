package com.example.mobileproject01;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {
    private Button themeSwitcher;
    private static boolean isThemeDark;
    private Context con;
    private MessageController messageController;
//    SharedPreferences preferences;
//    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        con = this;
        messageController = MessageController.getInstance(con);

        isThemeDark = messageController.storageManager.getTheme() == 1;

        super.onCreate(savedInstanceState);
        if (isThemeDark)
            this.setTheme(R.style.AppTheme);
        else
            setTheme(R.style.AppThemeLight);
        setContentView(R.layout.activity_settings);
        themeSwitcher = findViewById(R.id.theme_switcher);
//        preferences = PreferenceManager.getDefaultSharedPreferences(this);
//        editor = preferences.edit();
        themeSwitcher.setBackgroundColor(isThemeDark ? getResources().getColor(R.color.colorPrimaryDark, this.getTheme()) : getResources().getColor(R.color.colorPrimaryDarkLight, this.getTheme()));
        themeSwitcher.setText(isThemeDark ? "Light theme" : "Dark theme");
        themeSwitcher.setTextColor(isThemeDark ? getResources().getColor(R.color.colorAccent, this.getTheme()) : getResources().getColor(R.color.colorAccentLight, this.getTheme()));
        themeSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isThemeDark = !isThemeDark;
                Constant.setChangeMain(1);
                messageController.storageManager.setTheme(isThemeDark? 1 : 0);
                Constant.setAppTheme(isThemeDark ? 1 : 0);
                Intent i = new Intent(con, RSSFeedActivity.class);
                startActivity(i);
//                recreate();
            }
        });
    }
}
