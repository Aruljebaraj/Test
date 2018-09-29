package com.example.ab0034.firebasechat;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ab0034.firebasechat.Fragment.Login;
import com.example.ab0034.firebasechat.Fragment.Register;

public class MainActivity extends AppCompatActivity {
    private Login fragmentOne;
    private Register fragmentTwo;
    boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout Linear = (LinearLayout) findViewById(R.id.Linear);
        final TabLayout TabLayout = (TabLayout) findViewById(R.id.TabLayout);
        TabLayout.addTab(TabLayout.newTab().setText("SIGN UP"));
        TabLayout.addTab(TabLayout.newTab().setText("SIGN IN"));
//        TabLayout.addTab(TabLayout.newTab().setIcon(R.drawable.add_live));
//        TabLayout.addTab(TabLayout.newTab().setIcon(R.drawable.calendar_live));

        replaceFragment(new Register());

        //handling tab click event
        TabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    replaceFragment(new Register());
                } else {
                    replaceFragment(new Login());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        isNetworkOnline();
    }

    public boolean isNetworkOnline() {
        boolean status = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo netInfo = cm.getNetworkInfo(0);
            if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                status = true;
                if (status == false) {
                    Toast.makeText(this, "No net", Toast.LENGTH_SHORT).show();
                }
            } else {
                netInfo = cm.getNetworkInfo(1);
                if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED)
                    status = true;
                if (status == false) {
                    Toast.makeText(this, "No net", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return status;


    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.Linear, fragment);
        transaction.commit();
    }
}




