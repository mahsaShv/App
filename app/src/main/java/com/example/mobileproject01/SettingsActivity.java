package com.example.mobileproject01;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {
    private Switch themeSwitcher;
    private static boolean isThemeDark = true;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isThemeDark)
            this.setTheme(R.style.AppTheme);
        else
            setTheme(R.style.AppThemeLight);
        setContentView(R.layout.activity_settings);
        themeSwitcher = findViewById(R.id.theme_switcher);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        themeSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isThemeDark = !isThemeDark;
                editor.putInt("Theme", isThemeDark ? 1 : 0);
                recreate();
            }
        });
    }
}
