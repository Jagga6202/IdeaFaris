package com.brstdev.ideafaris.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.brstdev.ideafaris.R;
import com.brstdev.ideafaris.model.GlobalStory;
import com.brstdev.ideafaris.model.WriteStoryFirebase;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by brst-pc16 on 9/13/16.
 */
public class WriteStoryActivity extends AppCompatActivity {
    EditText writest;
    ImageView clock;
    Toolbar mToolbar;
    int time = 20;
    Timer t;
    TextView timer, savestory;
    TimerTask task;
    AlertDialog.Builder builder1;
    private FirebaseDatabase mFirebaseDatabase;
    CountDownTimer mCountDownTimer;
    public static final String PREFS_NAME = "login";
    SharedPreferences settings;
    String idfire, name, logged, loggedtwitter, title, id;

    private DatabaseReference myRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_story_activity);
        writest = (EditText) findViewById(R.id.writest);
        timer = (TextView) findViewById(R.id.timer);
        savestory = (TextView) findViewById(R.id.savestory);
        clock = (ImageView) findViewById(R.id.clock);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference("StoryLine");

        timer.setText("3m 00s");
        settings = getSharedPreferences(PREFS_NAME, 0);
        initToolbar();
        final Dialog dialog = new Dialog(WriteStoryActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.write_story_popup);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
        ImageView dailogButton = (ImageView) dialog.findViewById(R.id.sweet);
        dailogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                startTimer();

            }
        });
        Log.e("editext", "" + timer.getText().toString());

        writest.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (writest.getText().toString().length() == 0 || timer.getText().toString().equalsIgnoreCase("done!"))
                    savestory.setEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                savestory.setEnabled(true);
                savestory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String story = writest.getText().toString();

                        Toast.makeText(WriteStoryActivity.this, story, Toast.LENGTH_LONG).show();
                        mCountDownTimer.cancel();
                        timer.setText("done!");
                        clock.setVisibility(View.GONE);
                        writest.setKeyListener(null);
                        savestory.setKeyListener(null);
                        idfire = settings.getString("idfire", "");

                        logged = settings.getString("logged", "");
                        loggedtwitter = settings.getString("loggedtwitter", "");
                        if (logged.equalsIgnoreCase("logged")) {
                            id = settings.getString("idtest", "");
                            name = settings.getString("name", "");
                        } else if (loggedtwitter.equalsIgnoreCase("loggedtwitter")) {
                            name = settings.getString("twittername", "");
                            id = settings.getString("twitterprofileimg", "");
                        }
                        myRef.child("Write").child(idfire).setValue(new WriteStoryFirebase(name, story, id));
                        finish();
                    }


                });

            }
        });


    }

    void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportActionBar().setDisplayUseLogoEnabled(true);

        mToolbar.setNavigationIcon(R.drawable.arrow);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void startTimer() {

        mCountDownTimer = new CountDownTimer(180000, 1000) { // adjust the milli seconds here

            public void onTick(long millisUntilFinished) {
                timer.setText("" + String.format("%dm:%02ds",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                timer.setText("done!");
                clock.setVisibility(View.GONE);
                writest.setKeyListener(null);
            }
        }.start();
    }

}


