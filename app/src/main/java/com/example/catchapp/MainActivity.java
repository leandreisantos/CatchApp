package com.example.catchapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNav);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new DashboardFragment()).commit();


    }
    private BottomNavigationView.OnNavigationItemSelectedListener onNav = item -> {
        Fragment selected = null;
        switch(item.getItemId()){
            case R.id.dashboard_bottom:
                selected = new DashboardFragment();
                break;
            case R.id.Profile_bottom:
                selected = new CalendarFragment();
                break;
            case R.id.settings_bottom:
                selected = new ProfileFragment();
                break;
            case R.id.notification_bottom:
                selected = new NotificationFragment();
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,selected).commit();
        return true;
    };
}