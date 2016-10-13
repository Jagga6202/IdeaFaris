package com.brstdev.ideafaris.activity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;

import com.brstdev.ideafaris.R;
import com.brstdev.ideafaris.adapter.FriendListAdapter;
import com.brstdev.ideafaris.adapter.GlobalStoryAdapter;
import com.brstdev.ideafaris.model.FriendList;
import com.brstdev.ideafaris.model.GlobalStory;
import com.brstdev.ideafaris.utils.DividerItemDecoration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by brst-pc16 on 9/13/16.
 */
public class FriendListActivity extends AppCompatActivity {
    private List<FriendList> friendList = new ArrayList<>();
    private RecyclerView recyclerView;
    private FriendListAdapter mAdapter;
    Toolbar mToolbar;
    TextView profilename;
    CircleImageView image;
    TextView toolbar_title;
    public String logged;
    public String loggedtwitter;
    public static final String PREFS_NAME = "login";
    SharedPreferences settings;
    Bitmap bitmap;
    Handler handler;
    String name, email, gender, id, location;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.freind_list_activity);
        profilename = (TextView) findViewById(R.id.profilename);
        recyclerView = (RecyclerView) findViewById(R.id.friendlist);
        image = (CircleImageView) findViewById(R.id.profilepic);
        //image.setImageResource(R.drawable.u4);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        mAdapter = new FriendListAdapter(friendList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        initToolbar();
        frienddata();
        settings = getSharedPreferences(PREFS_NAME, 0);
        logged = settings.getString("logged", "");
        loggedtwitter = settings.getString("loggedtwitter", "");
        if (logged.equalsIgnoreCase("logged")) {
            name = settings.getString("name", "");
            profilename.setText(name);
            getprofilepicfb();
        } else if (loggedtwitter.equalsIgnoreCase("loggedtwitter")) {
            name = settings.getString("twittername", "");
            profilename.setText(name);
            getprofilepictwitter();
        }

    }

    void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar_title.setText("Friend List");

        mToolbar.setNavigationIcon(R.drawable.arrow);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    void frienddata() {
        FriendList friendList1 = new FriendList("Jack smith", "jack123@gmail.com", R.drawable.u1);
        friendList.add(friendList1);

        friendList1 = new FriendList("Jack smith", "jack123@gmail.com", R.drawable.u2);
        friendList.add(friendList1);

        friendList1 = new FriendList("Cecil", "celic@gmail.com", R.drawable.u3);
        friendList.add(friendList1);
        friendList1 = new FriendList("Ted Bernard", "ted_barnad@gmail.com", R.drawable.u4);
        friendList.add(friendList1);
        friendList1 = new FriendList("John Mash", "john_mesh@gmail.com", R.drawable.u5);
        friendList.add(friendList1);

        friendList1 = new FriendList("Jack smith", "jack_smith@gmail.com", R.drawable.u1);
        friendList.add(friendList1);

        friendList1 = new FriendList("John Mcgrah", "john_mcr@gmail.com", R.drawable.u2);
        friendList.add(friendList1);

        friendList1 = new FriendList("Ian smith", "Ian_smith@gmail.com", R.drawable.u3);
        friendList.add(friendList1);

        friendList1 = new FriendList("Ab devil", "ab_devil@gmail.com", R.drawable.u4);
        friendList.add(friendList1);
        friendList1 = new FriendList("Chris smith", "chris_smith@gmail.com", R.drawable.u5);
        friendList.add(friendList1);

        friendList1 = new FriendList("Michal Ted", "ted_michal@gmail.com", R.drawable.u1);
        friendList.add(friendList1);

        friendList1 = new FriendList("Den wan", "wan_den@gmail.com", R.drawable.u2);
        friendList.add(friendList1);

        friendList1 = new FriendList("Breet lee", "lee_brat@gmail.com", R.drawable.u3);
        friendList.add(friendList1);

        friendList1 = new FriendList("Johnson ", "johnson@gmail.com", R.drawable.u4);
        friendList.add(friendList1);

        friendList1 = new FriendList("Alica", "alica@gmail.com", R.drawable.u5);
        friendList.add(friendList1);

        friendList1 = new FriendList("Ammy", "ammy@gmail.com", R.drawable.u1);
        friendList.add(friendList1);

        mAdapter.notifyDataSetChanged();
    }

    public void getprofilepicfb() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //Your code goes here
                    id = settings.getString("idtest", "");
                    if (id.contains("https://")) {
                        try {
                            URL url = new URL(id);
                            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                            handler.sendEmptyMessage(0);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        byte[] encodeByte = Base64.decode(id, Base64.DEFAULT);
                        bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                        handler.sendEmptyMessage(0);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                image.setImageBitmap(bitmap);
                super.handleMessage(msg);
            }

        };
    }

    public void getprofilepictwitter() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //Your code goes here
                    id = settings.getString("twitterprofileimg", "");
                    if (id.contains("http://")) {
                        try {
                            URL url = new URL(id);
                            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                            handler.sendEmptyMessage(0);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        byte[] encodeByte = Base64.decode(id, Base64.DEFAULT);
                        bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                        handler.sendEmptyMessage(0);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                image.setImageBitmap(bitmap);
                super.handleMessage(msg);
            }

        };
    }
}
