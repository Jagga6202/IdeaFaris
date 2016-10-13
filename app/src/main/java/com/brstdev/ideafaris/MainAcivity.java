package com.brstdev.ideafaris;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.brstdev.ideafaris.activity.TabIconText;
import com.brstdev.ideafaris.activity.TabIconTextPrivate;

/**
 * Created by brst-pc16 on 9/12/16.
 */
public class MainAcivity extends AppCompatActivity {

    RelativeLayout joinrelative, createrelative;
    Toolbar mToolbar;
    ImageView joinglobalimage, createfriendsimage;
    TextView toolbar_title, joinGlobal, createFriend, storylinetext;
    ;
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    public static final String PREFS_NAME = "login";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settings = getSharedPreferences(PREFS_NAME, 0);
        editor = settings.edit();
        if (settings.getString("editProfile", "").equals("true")) {
            Intent i = new Intent(this, TabIconText.class);
            startActivity(i);
            finish();
        }
        setContentView(R.layout.main_activity);
        storylinetext = (TextView) findViewById(R.id.storylinetext);
        joinGlobal = (TextView) findViewById(R.id.joinglobal);
        createFriend = (TextView) findViewById(R.id.createfriends);
        joinrelative = (RelativeLayout) findViewById(R.id.joinrelative);
        createrelative = (RelativeLayout) findViewById(R.id.createrelative);
        joinglobalimage = (ImageView) findViewById(R.id.joinglobalimage);
        createfriendsimage = (ImageView) findViewById(R.id.createfriendsimage);


        initToolbar();
        joinrelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainAcivity.this, TabIconText.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
        createrelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainAcivity.this, TabIconTextPrivate.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar_title.setText("Home");


    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.e("onfinish", "onfinish");
    }
}
