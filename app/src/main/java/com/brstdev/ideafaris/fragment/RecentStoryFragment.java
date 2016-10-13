package com.brstdev.ideafaris.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brstdev.ideafaris.MainAcivity;
import com.brstdev.ideafaris.R;
import com.brstdev.ideafaris.activity.TabIconText;
import com.brstdev.ideafaris.adapter.RecentStoryAdapter;
import com.brstdev.ideafaris.model.RecentStory;
import com.brstdev.ideafaris.model.TopRated;
import com.brstdev.ideafaris.utils.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brst-pc16 on 9/14/16.
 */
public class RecentStoryFragment extends Fragment {
    View rootView;

    private List<RecentStory> recentStories = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecentStoryAdapter mAdapter;
    Toolbar mToolbar;
    TextView toolbar_title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.recent_story_activity, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recentlist);
        toolbar_title = (TextView) rootView.findViewById(R.id.toolbar_title);
        mAdapter = new RecentStoryAdapter(recentStories);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);


        mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar);

        initToolbar();
        prepareMovieData();
        return rootView;
    }

    void initToolbar() {

        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayUseLogoEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar_title.setText("Recent Story");
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


        RecentStory recentStory = new RecentStory("Mad Max: Fury Road", "Title lorem title ipsum title", "John Framer", R.drawable.u1);
        recentStories.add(recentStory);

        recentStory = new RecentStory("Inside Out", "Title lorem title ipsum title", "Ted Ballard", R.drawable.u2);
        recentStories.add(recentStory);

        recentStory = new RecentStory("Star Wars: Episode VII - The Force Awakens", "Title lorem title ipsum title", "Kendra", R.drawable.u3);
        recentStories.add(recentStory);

        recentStory = new RecentStory("Shaun the Sheep", "Title lorem title ipsum title", "Cecil", R.drawable.u4);
        recentStories.add(recentStory);

        recentStory = new RecentStory("The Martian", "Title lorem title ipsum title", "Girrifth", R.drawable.u5);
        recentStories.add(recentStory);

        recentStory = new RecentStory("Mission: Impossible Rogue Nation", "Title lorem title ipsum title", "Diana Walen", R.drawable.u1);
        recentStories.add(recentStory);

        recentStory = new RecentStory("Up", "Title lorem title ipsum title", "John Mesh", R.drawable.u2);
        recentStories.add(recentStory);

        recentStory = new RecentStory("Star Trek", "Title lorem title ipsum title", "Ted jackson", R.drawable.u3);
        recentStories.add(recentStory);

        recentStory = new RecentStory("The LEGO Movie", "Title lorem title ipsum title", "Ammy", R.drawable.u4);
        recentStories.add(recentStory);

        recentStory = new RecentStory("Iron Man", "Title lorem title ipsum title", "John", R.drawable.u5);
        recentStories.add(recentStory);

        recentStory = new RecentStory("Aliens", "Title lorem title ipsum title", "Smith", R.drawable.u1);
        recentStories.add(recentStory);

        recentStory = new RecentStory("Chicken Run", "Title lorem title ipsum title", "Maxwell", R.drawable.u2);
        recentStories.add(recentStory);

        recentStory = new RecentStory("Back to the Future", "Title lorem title ipsum title", "Mcgrath", R.drawable.u3);
        recentStories.add(recentStory);

        recentStory = new RecentStory("Raiders of the Lost Ark", "Title lorem title ipsum title", "Donald", R.drawable.u4);
        recentStories.add(recentStory);

        recentStory = new RecentStory("Goldfinger", "Title lorem title ipsum title", "Gayle", R.drawable.u5);
        recentStories.add(recentStory);

        recentStory = new RecentStory("Guardians of the Galaxy", "Title lorem title ipsum title", "Starc", R.drawable.u1);
        recentStories.add(recentStory);

        mAdapter.notifyDataSetChanged();
    }

}
