package com.brstdev.ideafaris.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.brstdev.ideafaris.MainAcivity;
import com.brstdev.ideafaris.R;

/**
 * Created by brst-pc16 on 9/13/16.
 */
public class EditProfilePrivateActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView profilepic;
    TextView profilename;
    EditText profileemail, profilephone, profilegender, profileaddress;
    Button friendlist;
    Toolbar mToolbar;
    FloatingActionButton fab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_private);
        profilepic = (ImageView) findViewById(R.id.profilepic);
        profilename = (TextView) findViewById(R.id.profilename);
        profileemail = (EditText) findViewById(R.id.profileemail);
        profilephone = (EditText) findViewById(R.id.profilephone);
        profilegender = (EditText) findViewById(R.id.profilegender);
        profileaddress = (EditText) findViewById(R.id.profileaddress);
        friendlist = (Button) findViewById(R.id.friendlist);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        initToolbar();
        friendlist.setOnClickListener(this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(EditProfilePrivateActivity.this, MainAcivity.class);
                startActivity(intent1);
            }
        });

    }

    void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        getSupportActionBar().setTitle("Profile");
        mToolbar.setNavigationIcon(R.drawable.arrow);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.friendlist:
                Intent intent = new Intent(EditProfilePrivateActivity.this, FriendListActivity.class);
                startActivity(intent);
                break;

        }
    }
}


