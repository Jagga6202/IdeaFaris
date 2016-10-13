package com.brstdev.ideafaris;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.brstdev.ideafaris.activity.NewTutorial;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

/**
 * Created by brst-pc16 on 9/12/16.
 */
public class SplashActivity extends AppCompatActivity {
    ImageView imageView;
    public static final String PREFS_NAME = "login";
    SharedPreferences settings;
    public String logged;
    public String loggedtwitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        settings = getSharedPreferences(PREFS_NAME, 0);
        logged = settings.getString("logged", "");
        loggedtwitter = settings.getString("loggedtwitter", "");
        imageView = (ImageView) findViewById(R.id.webview);
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageView);
        Glide.with(this).load(R.drawable.splash).into(imageViewTarget);
        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(7500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (logged.equalsIgnoreCase("logged") || loggedtwitter.equalsIgnoreCase("loggedtwitter")) {
                        Intent intent = new Intent(SplashActivity.this, MainAcivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(SplashActivity.this, NewTutorial.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }

                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }

}
