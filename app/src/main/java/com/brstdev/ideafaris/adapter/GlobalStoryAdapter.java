package com.brstdev.ideafaris.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.brstdev.ideafaris.R;
import com.brstdev.ideafaris.activity.TabIconText;
import com.brstdev.ideafaris.model.GlobalStory;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by brst-pc16 on 9/12/16.
 */
public class GlobalStoryAdapter extends RecyclerView.Adapter<GlobalStoryAdapter.MyViewHolder> {
    private List<GlobalStory> moviesList;

    Bitmap bmp;
    GlobalStory globalStory;
    String currentDateTimeString;
    private static String today;

    public GlobalStoryAdapter(List<GlobalStory> moviesList, Context mContext) {
        this.moviesList = moviesList;
        this.mContext = mContext;
    }

    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView profilepic;
        public TextView title, year, genre, timer, dislike, like;
        public ImageView unlikethump, likethump;

        public MyViewHolder(View view) {
            super(view);
            profilepic = (CircleImageView) view.findViewById(R.id.title);
            genre = (TextView) view.findViewById(R.id.genre);
            year = (TextView) view.findViewById(R.id.year);
            timer = (TextView) view.findViewById(R.id.timer);


        }
    }


    public GlobalStoryAdapter(List<GlobalStory> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.global_story_items, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {


        globalStory = moviesList.get(position);
        //holder.setIsRecyclable(false);
        //holder.title.setText(globalStory.getTitle());
        holder.genre.setText(globalStory.getGenre());
        holder.year.setText(globalStory.getYear());
        holder.timer.setText(globalStory.getTime());
        //holder.profilepic.setImageResource(globalStory.getImageUrl());
        if (!globalStory.getProfileUrl().contains("http")) {
            try {
                byte[] encodeByte = Base64.decode(globalStory.getProfileUrl(), Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                holder.profilepic.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Picasso.with(mContext).load(globalStory.getProfileUrl()).into(holder.profilepic);
        }


    }


    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}