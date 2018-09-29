package com.example.ab0034.anim;

import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements ListFragment.ChessPieceListener {

    TextView TxtDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TxtDescription = (TextView) findViewById(R.id.TxtDescription);


        if (findViewById(R.id.LayoutDefault) != null) {
            FragmentManager manager = this.getSupportFragmentManager();
            manager.beginTransaction()
                    .hide(manager.findFragmentById(R.id.fragment2))
                    .show(manager.findFragmentById(R.id.fragment))
                    .commit();
        }
        if (findViewById(R.id.LayoutLand) != null) {
            FragmentManager manager = this.getSupportFragmentManager();
            manager.beginTransaction().show(manager.findFragmentById(R.id.fragment2)).show(manager.findFragmentById(R.id.fragment)).commit();
        }
    }

    @Override
    public void onchessSelected(int index) {

        if (findViewById(R.id.LayoutDefault) != null) {
            FragmentManager manager = this.getSupportFragmentManager();
            manager.beginTransaction()
                    .hide(manager.findFragmentById(R.id.fragment))
                    .show(manager.findFragmentById(R.id.fragment2))
                    .commit();
        }

        String desc[] = getResources().getStringArray(R.array.Description);
        TxtDescription.setText(desc[index]);
    }

}
