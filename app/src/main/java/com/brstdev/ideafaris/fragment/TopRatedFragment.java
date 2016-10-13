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
import com.brstdev.ideafaris.adapter.TopRatedAdapter;
import com.brstdev.ideafaris.model.PrivateStory;
import com.brstdev.ideafaris.model.TopRated;
import com.brstdev.ideafaris.utils.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brst-pc16 on 9/14/16.
 */
public class TopRatedFragment extends Fragment {
    View rootView;

    private List<TopRated> globalStoryList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TopRatedAdapter mAdapter;
    Toolbar mToolbar;
    TextView toolbar_title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.top_rated_activity, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.toprated);
        toolbar_title = (TextView) rootView.findViewById(R.id.toolbar_title);
        mAdapter = new TopRatedAdapter(globalStoryList);
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

        toolbar_title.setText("Top Rated");
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

        TopRated movie = new TopRated("Mad Max: Fury Road", "John Framer", "Title lorem title ipsum title", R.drawable.u1);
        globalStoryList.add(movie);

        movie = new TopRated("Inside Out", "Ted Ballard", "Title lorem title ipsum title", R.drawable.u2);
        globalStoryList.add(movie);

        movie = new TopRated("Star Wars: Episode VII - The Force Awakens", "Kendra", "Title lorem title ipsum title", R.drawable.u3);
        globalStoryList.add(movie);

        movie = new TopRated("Shaun the Sheep", "Cecil", "Title lorem title ipsum title", R.drawable.u4);
        globalStoryList.add(movie);

        movie = new TopRated("The Martian", "Girrifth", "Title lorem title ipsum title", R.drawable.u5);
        globalStoryList.add(movie);

        movie = new TopRated("Mission: Impossible Rogue Nation", "Diana Walen", "Title lorem title ipsum title", R.drawable.u1);
        globalStoryList.add(movie);

        movie = new TopRated("Up", "John Mesh", "Title lorem title ipsum title", R.drawable.u2);
        globalStoryList.add(movie);

        movie = new TopRated("Star Trek", "Ted jackson", "Title lorem title ipsum title", R.drawable.u3);
        globalStoryList.add(movie);

        movie = new TopRated("The LEGO Movie", "Ammy", "Title lorem title ipsum title", R.drawable.u4);
        globalStoryList.add(movie);

        movie = new TopRated("Iron Man", "John", "Title lorem title ipsum title", R.drawable.u5);
        globalStoryList.add(movie);

        movie = new TopRated("Aliens", "Smith", "Title lorem title ipsum title", R.drawable.u1);
        globalStoryList.add(movie);

        movie = new TopRated("Chicken Run", "Maxwell", "Title lorem title ipsum title", R.drawable.u2);
        globalStoryList.add(movie);

        movie = new TopRated("Back to the Future", "Mcgrath", "Title lorem title ipsum title", R.drawable.u3);
        globalStoryList.add(movie);

        movie = new TopRated("Raiders of the Lost Ark", "Donald", "Title lorem title ipsum title", R.drawable.u4);
        globalStoryList.add(movie);

        movie = new TopRated("Goldfinger", "Gayle", "Title lorem title ipsum title", R.drawable.u5);
        globalStoryList.add(movie);

        movie = new TopRated("Guardians of the Galaxy", "Starc", "Title lorem title ipsum title", R.drawable.u1);
        globalStoryList.add(movie);

        mAdapter.notifyDataSetChanged();
    }

}
