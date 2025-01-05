package com.example.finalapp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EasySplashScreen splashScreen = new EasySplashScreen(SplashScreen.this)
                .withFullScreen().withTargetActivity(MainActivity.class).withSplashTimeOut(4000)
                .withBackgroundColor(Color.parseColor("#0000FF")).withBeforeLogoText("The Searcher").withAfterLogoText("By Ved").withLogo(R.drawable.searcher);

        splashScreen.getBeforeLogoTextView().setTextSize(40);
        splashScreen.getBeforeLogoTextView().setTextColor(Color.MAGENTA);
        splashScreen.getAfterLogoTextView().setTextSize(40);
        splashScreen.getAfterLogoTextView().setTextColor(Color.CYAN);

        View splash = splashScreen.create();
        setContentView(splash);
    }
}