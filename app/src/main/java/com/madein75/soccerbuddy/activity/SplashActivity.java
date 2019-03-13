package com.madein75.soccerbuddy.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.madein75.soccerbuddy.R;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;
    private boolean isBackButtonPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                finish();

                if (!isBackButtonPressed) {
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                }

            }
        }, SPLASH_TIME_OUT);
    }

    @Override
    public void onBackPressed() {
        isBackButtonPressed = true;
        super.onBackPressed();
    }
}
