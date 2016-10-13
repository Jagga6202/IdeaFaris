package com.brstdev.ideafaris.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.brstdev.ideafaris.MainAcivity;
import com.brstdev.ideafaris.R;

import com.brstdev.ideafaris.activity.TabIconText;
import com.brstdev.ideafaris.activity.WriteStoryActivity;
import com.brstdev.ideafaris.adapter.GlobalStoryAdapter;
import com.brstdev.ideafaris.model.GlobalStory;
import com.brstdev.ideafaris.model.WriteStoryFirebase;
import com.brstdev.ideafaris.utils.DividerItemDecoration;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by brst-pc16 on 9/14/16.
 */
public class GlobalStroyFragment extends Fragment {
    private List<GlobalStory> globalStoryList = new ArrayList<>();
    private RecyclerView recyclerView;
    private GlobalStoryAdapter mAdapter;
    Toolbar mToolbar;
    Button writestory;
    TextView toolbar_title;
    private FirebaseAuth mAuth;

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseUser mFireBaseUser;
    private DatabaseReference myRef;
    private DatabaseReference myRef1;

    ProgressDialog pd;
    String writeName, writeTitle;
    String name, email, gender, id, location, idfire;
    public static final String PREFS_NAME = "login";
    SharedPreferences settings;
    public String logged;
    public String loggedtwitter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.global_story, container,
                false);
        pd = new ProgressDialog(getActivity());
        pd.setMessage("loading");
        pd.show();
        pd.setCanceledOnTouchOutside(false);
        settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
        logged = settings.getString("logged", "");
        loggedtwitter = settings.getString("loggedtwitter", "");
        idfire = settings.getString("idfire", "");
        if (logged.equalsIgnoreCase("logged")) {
            name = settings.getString("name", "");
        } else if (loggedtwitter.equalsIgnoreCase("loggedtwitter")) {
            name = settings.getString("twittername", "");
        }
        mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.globalcontact);
        writestory = (Button) rootView.findViewById(R.id.writestory);
        mAdapter = new GlobalStoryAdapter(globalStoryList, getActivity().getApplicationContext());
        //mAdapter=new GlobalStoryAdapter(globalStoryList);
        toolbar_title = (TextView) rootView.findViewById(R.id.toolbar_title);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference("StoryLine").child("Users");
        myRef1 = mFirebaseDatabase.getReference("StoryLine").child("Write");
        readData();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        initToolbar();
        prepareMovieData();
        writestory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (writeTitle != null && writeTitle != " ") {
                    Toast.makeText(getActivity(), "you have writeen a story", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(getActivity(), WriteStoryActivity.class);
                    startActivity(intent);
                }

            }
        });

        return rootView;
    }

    void initToolbar() {

        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayUseLogoEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar_title.setText("Current Story");
        mToolbar.setNavigationIcon(R.drawable.arrow);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getActivity(), MainAcivity.class);
                startActivity(intent);
                getActivity().finish();


            }
        });
    }

    public void readfire() {
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                GlobalStory post = dataSnapshot.getValue(GlobalStory.class);
                Log.d("post", post.toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("", "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
               /* Toast.makeText(getActivity(), "Failed to load post.",
                        Toast.LENGTH_SHORT).show();*/
                // [END_EXCLUDE]
            }
        };
        myRef.addValueEventListener(postListener);

        Query query = myRef.orderByChild("Users");
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                pd.dismiss();
                Log.e("Data", dataSnapshot.getValue().toString() + dataSnapshot.getChildrenCount());

                Map<String, Object> value = (Map<String, Object>) dataSnapshot.getValue();

                GlobalStory movie = new GlobalStory(value.get("username").toString(), value.get("email").toString()
                        , value.get("profileUrl").toString(), value.get("time").toString(), value.get("location").toString(), value.get("phoneNum").toString()
                );
                globalStoryList.add(movie);

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                pd.dismiss();
                Log.e("Childdata", dataSnapshot.getValue().toString());
/*
                Map<String,Object> value = (Map<String, Object>) dataSnapshot.getValue();
                GlobalStory movie = new GlobalStory( value.get("username").toString(), value.get("email").toString()
                        ,value.get("profileUrl").toString(),value.get("time").toString());
                globalStoryList.add(movie);
                mAdapter.notifyDataSetChanged();*/
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void prepareMovieData() {
        readfire();
       /* GlobalStory movie = new GlobalStory("Mad Max: Fury Road", "John Framer", "lorem ipsum dolor ament",R.drawable.u1);
        globalStoryList.add(movie);

        movie = new GlobalStory("Inside Out", "Ted Ballard", "lorem ipsum dolor ament",R.drawable.u2);
        globalStoryList.add(movie);

        movie = new GlobalStory("Star Wars: Episode VII - The Force Awakens", "Kendra", "lorem ipsum dolor ament",R.drawable.u3);
        globalStoryList.add(movie);

        movie = new GlobalStory("Shaun the Sheep", "Cecil", "lorem ipsum dolor ament",R.drawable.u4);
        globalStoryList.add(movie);

        movie = new GlobalStory("The Martian", "Girrifth", "lorem ipsum dolor ament",R.drawable.u5);
        globalStoryList.add(movie);

        movie = new GlobalStory("Mission: Impossible Rogue Nation", "Diana Walen", "lorem ipsum dolor ament",R.drawable.u1);
        globalStoryList.add(movie);

        movie = new GlobalStory("Up", "John Mesh", "lorem ipsum dolor ament",R.drawable.u2);
        globalStoryList.add(movie);

        movie = new GlobalStory("Star Trek", "Ted jackson", "lorem ipsum dolor ament",R.drawable.u3);
        globalStoryList.add(movie);

        movie = new GlobalStory("The LEGO Movie", "Ammy", "lorem ipsum dolor ament",R.drawable.u4);
        globalStoryList.add(movie);

        movie = new GlobalStory("Iron Man", "John", "lorem ipsum dolor ament",R.drawable.u5);
        globalStoryList.add(movie);

        movie = new GlobalStory("Aliens", "Smith", "lorem ipsum dolor ament",R.drawable.u1);
        globalStoryList.add(movie);

        movie = new GlobalStory("Chicken Run", "Maxwell", "lorem ipsum dolor ament",R.drawable.u2);
        globalStoryList.add(movie);

        movie = new GlobalStory("Back to the Future", "Mcgrath", "lorem ipsum dolor ament",R.drawable.u3);
        globalStoryList.add(movie);

        movie = new GlobalStory("Raiders of the Lost Ark", "Donald", "lorem ipsum dolor ament",R.drawable.u4);
        globalStoryList.add(movie);

        movie = new GlobalStory("Goldfinger", "Gayle", "lorem ipsum dolor ament",R.drawable.u5);
        globalStoryList.add(movie);

        movie = new GlobalStory("Guardians of the Galaxy", "Starc", "lorem ipsum dolor ament",R.drawable.u1);
        globalStoryList.add(movie);*/

    }

    public void readData() {
        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                WriteStoryFirebase value = dataSnapshot.getValue(WriteStoryFirebase.class);
                Log.d("", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("", "Failed to read value.", error.toException());
            }
        });
        Query query = myRef1.orderByKey().equalTo(idfire);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Log.e("Data", dataSnapshot.getValue().toString());
                Map<String, Object> value = (Map<String, Object>) dataSnapshot.getValue();
                writeName = value.get("name").toString();
                writeTitle = value.get("title").toString();


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
