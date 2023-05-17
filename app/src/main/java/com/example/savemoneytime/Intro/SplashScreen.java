package com.example.savemoneytime.Intro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.savemoneytime.Intro.IntroViewPager.IntroViewpager;
import com.example.savemoneytime.R;

public class SplashScreen extends AppCompatActivity {

    TextView appname;
    LottieAnimationView lottie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        appname= findViewById(R.id.appname);
        lottie=findViewById(R.id.IntroAni);

        appname.animate().translationY(-1400).setDuration(3000).setStartDelay(0);
        lottie.animate().translationX(2000).setDuration(2500).setStartDelay(1000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(getApplicationContext(), IntroViewpager.class);
                startActivity(i);
            }
        }, 3000);
    }
}