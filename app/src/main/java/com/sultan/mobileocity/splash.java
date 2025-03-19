package com.sultan.mobileocity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.airbnb.lottie.LottieAnimationView;

public class splash extends AppCompatActivity {

    Animation layoutAnimation;
    LottieAnimationView View;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        getSupportActionBar().hide();
        View=findViewById(R.id.lottieAnimationView);



        layoutAnimation= AnimationUtils.loadAnimation(splash.this,R.anim.fall_down);

        View.setVisibility(View.VISIBLE);
        View.startAnimation(layoutAnimation);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(splash.this,MainActivity.class);
                startActivity(intent);
            }
        },6000);
    }
}