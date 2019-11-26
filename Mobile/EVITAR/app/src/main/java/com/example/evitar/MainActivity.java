package com.example.evitar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT=3000;
    ImageView logo, girl, wave;
    TextView wtext;
    Button skip;
    Animation descer,rotation, walktext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);


        descer= AnimationUtils.loadAnimation(this, R.anim.descer);
        rotation= AnimationUtils.loadAnimation(this, R.anim.rotation);
        walktext= AnimationUtils.loadAnimation(this, R.anim.walktext);

        logo=(ImageView) findViewById(R.id.imageView);

        logo.startAnimation(rotation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setContentView(R.layout.walkthrough);
                logo=(ImageView) findViewById(R.id.imageView5);
                girl=(ImageView) findViewById(R.id.imageView3);
                wave=(ImageView) findViewById(R.id.imageView4);
                wtext=(TextView) findViewById(R.id.textView);
                skip=(Button) findViewById(R.id.skip);

                girl.startAnimation(descer);
                wave.startAnimation(descer);
                logo.startAnimation(descer);
                wtext.startAnimation(walktext);
                skip.startAnimation(walktext);

                skip.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        setContentView(R.layout.login);
                    }
                });
            }
        },SPLASH_TIME_OUT);

    }
}
