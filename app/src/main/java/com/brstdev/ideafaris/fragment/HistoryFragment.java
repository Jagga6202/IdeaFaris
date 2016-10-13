package com.brstdev.ideafaris.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brstdev.ideafaris.MainAcivity;
import com.brstdev.ideafaris.R;
import com.brstdev.ideafaris.activity.TabIconText;
import com.brstdev.ideafaris.adapter.HistoryAdapter;
import com.brstdev.ideafaris.model.History;
import com.brstdev.ideafaris.model.RecentStory;
import com.brstdev.ideafaris.utils.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brst-pc16 on 9/14/16.
 */
public class HistoryFragment extends Fragment {
    private List<History> histories = new ArrayList<>();
    private RecyclerView recyclerView;
    private HistoryAdapter mAdapter;
    View rootView;
    Toolbar mToolbar;
    TextView toolbar_title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.history_activity, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.historylist);
        mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        toolbar_title = (TextView) rootView.findViewById(R.id.toolbar_title);
        mAdapter = new HistoryAdapter(histories);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
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
        toolbar_title.setText("History");


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


        History recentStory = new History("Mad Max: Fury Road", "John Framer", "Title lorem title ipsum title", R.drawable.u1);
        histories.add(recentStory);

        recentStory = new History("Inside Out", "Ted Ballard", "Title lorem title ipsum title", R.drawable.u2);
        histories.add(recentStory);

        recentStory = new History("Star Wars: Episode VII - The Force Awakens", "Kendra", "Title lorem title ipsum title", R.drawable.u3);
        histories.add(recentStory);

        recentStory = new History("Shaun the Sheep", "Cecil", "Title lorem title ipsum title", R.drawable.u4);
        histories.add(recentStory);

        recentStory = new History("The Martian", "Girrifth", "Title lorem title ipsum title", R.drawable.u5);
        histories.add(recentStory);

        recentStory = new History("Mission: Impossible Rogue Nation", "Diana Walen", "Title lorem title ipsum title", R.drawable.u1);
        histories.add(recentStory);

        recentStory = new History("Up", "John Mesh", "Title lorem title ipsum title", R.drawable.u2);
        histories.add(recentStory);

        recentStory = new History("Star Trek", "Ted jackson", "Title lorem title ipsum title", R.drawable.u3);
        histories.add(recentStory);

        recentStory = new History("The LEGO Movie", "Ammy", "Title lorem title ipsum title", R.drawable.u4);
        histories.add(recentStory);

        recentStory = new History("Iron Man", "John", "Title lorem title ipsum title", R.drawable.u5);
        histories.add(recentStory);

        recentStory = new History("Aliens", "Smith", "Title lorem title ipsum title", R.drawable.u1);
        histories.add(recentStory);

        recentStory = new History("Chicken Run", "Maxwell", "Title lorem title ipsum title", R.drawable.u2);
        histories.add(recentStory);

        recentStory = new History("Back to the Future", "Mcgrath", "Title lorem title ipsum title", R.drawable.u3);
        histories.add(recentStory);

        recentStory = new History("Raiders of the Lost Ark", "Donald", "Title lorem title ipsum title", R.drawable.u4);
        histories.add(recentStory);

        recentStory = new History("Goldfinger", "Gayle", "Title lorem title ipsum title", R.drawable.u5);
        histories.add(recentStory);

        recentStory = new History("Guardians of the Galaxy", "Starc", "Title lorem title ipsum title", R.drawable.u1);
        histories.add(recentStory);

        mAdapter.notifyDataSetChanged();
    }
}
