package com.example.evitarapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT=5000;
    ImageView logo, girl, wave;
    Animation bigtosmall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

        Button skip=(Button) findViewById(R.id.skip);

        bigtosmall= AnimationUtils.loadAnimation(this, R.anim.bigtosmall);

        logo=(ImageView) findViewById(R.id.imageView2);

        logo.startAnimation(bigtosmall);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setContentView(R.layout.walkthrough);
                logo=(ImageView) findViewById(R.id.imageView5);
                girl=(ImageView) findViewById(R.id.imageView3);
                wave=(ImageView) findViewById(R.id.imageView4);

                girl.startAnimation(bigtosmall);
                wave.startAnimation(bigtosmall);
                logo.startAnimation(bigtosmall);
            }
        },SPLASH_TIME_OUT);
/*
        skip.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

            }
        });*/
    }
}
