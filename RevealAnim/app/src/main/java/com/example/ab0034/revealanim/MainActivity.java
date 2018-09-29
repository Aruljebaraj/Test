package com.example.ab0034.revealanim;

import android.animation.Animator;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    ImageButton imageButton, closeButton;
    RelativeLayout revealView;
    LinearLayout layoutButtons;
    Animation alphaAppear, alphaDisappear;
    int x, y, width, height, hypotenuse;
    float pixelDensity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pixelDensity = getResources().getDisplayMetrics().density;

        imageView = (ImageView) findViewById(R.id.imageView);
        imageButton = (ImageButton) findViewById(R.id.launchTwitterAnimation);
        closeButton = (ImageButton) findViewById(R.id.closeButton);
        revealView = (RelativeLayout) findViewById(R.id.linearView);
        layoutButtons = (LinearLayout) findViewById(R.id.layoutButtons);

        alphaAppear = AnimationUtils.loadAnimation(this, R.anim.alpha_anim);
        alphaDisappear = AnimationUtils.loadAnimation(this, R.anim.alpha_disappear);
    }

    public void launchTwitter(View view) {

        width = imageView.getWidth();
        height = imageView.getHeight();

        x = width / 2;
        y = height / 2;
        hypotenuse = (int) Math.hypot(x, y);

        x = (int) (x - ((16 * pixelDensity) + (28 * pixelDensity)));

        FrameLayout.LayoutParams parameters = (FrameLayout.LayoutParams)
                revealView.getLayoutParams();
        parameters.height = imageView.getHeight();
        revealView.setLayoutParams(parameters);

        imageButton.animate()
                .translationX(-x)
                .translationY(-y)
                .setDuration(200);

//
        Animator anim = ViewAnimationUtils.createCircularReveal(revealView, width, height / 2, 28 * pixelDensity, hypotenuse);
        anim.setDuration(350);

        layoutButtons.setVisibility(View.VISIBLE);
        closeButton.setVisibility(View.VISIBLE);
        layoutButtons.startAnimation(alphaAppear);
        closeButton.startAnimation(alphaAppear);

        imageButton.setVisibility(View.GONE);
        revealView.setVisibility(View.VISIBLE);
        anim.start();

    }

    public void closeTwitter(View view) {

        Animator anim = ViewAnimationUtils.createCircularReveal(revealView, width / 2, height / 2, hypotenuse, 28 * pixelDensity);
        anim.setDuration(350);

        imageButton.animate()
                .translationX(-50)
                .translationY(50);


        imageButton.animate()
                .translationX(0f)
                .translationY(0f)
                .setDuration(200);
        revealView.setVisibility(View.GONE);

        anim.start();
//
        layoutButtons.startAnimation(alphaDisappear);
        layoutButtons.setVisibility(View.GONE);
        closeButton.startAnimation(alphaDisappear);
        closeButton.setVisibility(View.GONE);
        imageButton.setVisibility(View.VISIBLE);
    }
}
