package com.brstdev.ideafaris.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.brstdev.ideafaris.MainAcivity;
import com.brstdev.ideafaris.R;
import com.brstdev.ideafaris.activity.FriendListActivity;
import com.brstdev.ideafaris.activity.WriteStoryActivity;
import com.brstdev.ideafaris.adapter.GlobalStoryAdapter;
import com.brstdev.ideafaris.adapter.PrivateStoryAdapter;
import com.brstdev.ideafaris.model.GlobalStory;
import com.brstdev.ideafaris.model.PrivateStory;
import com.brstdev.ideafaris.utils.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brst-pc16 on 9/14/16.
 */
public class GlobalStoryFragmentPrivate extends Fragment {

    private List<PrivateStory> globalStoryList = new ArrayList<>();
    Button people, currentstory, toprated, recentstory, history, writestory;
    RecyclerView recyclerView;
    private PrivateStoryAdapter mAdapter;
    FloatingActionButton fab;
    TextView toolbar_title;
    Toolbar mToolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.private_story, container,
                false);
        mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.privatecontact);
        writestory = (Button) rootView.findViewById(R.id.writestory);
        toolbar_title = (TextView) rootView.findViewById(R.id.toolbar_title);
        mAdapter = new PrivateStoryAdapter(globalStoryList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        initToolbar();
        prepareMovieData();
        writestory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WriteStoryActivity.class);
                startActivity(intent);
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FriendListActivity.class);
                startActivity(intent);
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
            }
        });
    }

    private void prepareMovieData() {


        PrivateStory movie = new PrivateStory("Mad Max: Fury Road", "John Framer", "lorem ipsum dolor ament", R.drawable.u1);
        globalStoryList.add(movie);

        movie = new PrivateStory("Inside Out", "Ted Ballard", "lorem ipsum dolor ament", R.drawable.u2);
        globalStoryList.add(movie);

        movie = new PrivateStory("Star Wars: Episode VII - The Force Awakens", "Kendra", "lorem ipsum dolor ament", R.drawable.u3);
        globalStoryList.add(movie);

        movie = new PrivateStory("Shaun the Sheep", "Cecil", "lorem ipsum dolor ament", R.drawable.u4);
        globalStoryList.add(movie);

        movie = new PrivateStory("The Martian", "Girrifth", "lorem ipsum dolor ament", R.drawable.u5);
        globalStoryList.add(movie);

        movie = new PrivateStory("Mission: Impossible Rogue Nation", "Diana Walen", "lorem ipsum dolor ament", R.drawable.u1);
        globalStoryList.add(movie);

        movie = new PrivateStory("Up", "John Mesh", "lorem ipsum dolor ament", R.drawable.u2);
        globalStoryList.add(movie);

        movie = new PrivateStory("Star Trek", "Ted jackson", "lorem ipsum dolor ament", R.drawable.u3);
        globalStoryList.add(movie);

        movie = new PrivateStory("The LEGO Movie", "Ammy", "lorem ipsum dolor ament", R.drawable.u4);
        globalStoryList.add(movie);

        movie = new PrivateStory("Iron Man", "John", "lorem ipsum dolor ament", R.drawable.u5);
        globalStoryList.add(movie);

        movie = new PrivateStory("Aliens", "Smith", "lorem ipsum dolor ament", R.drawable.u1);
        globalStoryList.add(movie);

        movie = new PrivateStory("Chicken Run", "Maxwell", "lorem ipsum dolor ament", R.drawable.u2);
        globalStoryList.add(movie);

        movie = new PrivateStory("Back to the Future", "Mcgrath", "lorem ipsum dolor ament", R.drawable.u3);
        globalStoryList.add(movie);

        movie = new PrivateStory("Raiders of the Lost Ark", "Donald", "lorem ipsum dolor ament", R.drawable.u4);
        globalStoryList.add(movie);

        movie = new PrivateStory("Goldfinger", "Gayle", "lorem ipsum dolor ament", R.drawable.u5);
        globalStoryList.add(movie);

        movie = new PrivateStory("Guardians of the Galaxy", "Starc", "lorem ipsum dolor ament", R.drawable.u1);
        globalStoryList.add(movie);

        mAdapter.notifyDataSetChanged();
    }


}


