package com.brstdev.ideafaris.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.brstdev.ideafaris.R;
import com.brstdev.ideafaris.model.TopRated;


import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by brst-pc16 on 9/12/16.
 */
public class TopRatedAdapter extends RecyclerView.Adapter<TopRatedAdapter.MyViewHolder> {

    private List<TopRated> moviesList;
    public int isClicked = 0;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView profilepic;
        public TextView title, year, genre, timer, dislike, like, share;
        public ImageView likethump, sharebtn;

        public MyViewHolder(View view) {
            super(view);
            profilepic = (CircleImageView) view.findViewById(R.id.title);
            genre = (TextView) view.findViewById(R.id.genre);
            year = (TextView) view.findViewById(R.id.year);
            timer = (TextView) view.findViewById(R.id.timer);
            dislike = (TextView) view.findViewById(R.id.dislike);
            like = (TextView) view.findViewById(R.id.like);
            share = (TextView) view.findViewById(R.id.share);
            sharebtn = (ImageView) view.findViewById(R.id.sharebtn);
            likethump = (ImageView) view.findViewById(R.id.likethump);

        }
    }


    public TopRatedAdapter(List<TopRated> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.top_rated_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {


        TopRated toprated = moviesList.get(position);
        holder.setIsRecyclable(false);
        //holder.title.setText(globalStory.getTitle());
        holder.genre.setText(toprated.getGenre());
        holder.year.setText(toprated.getYear());
        holder.profilepic.setImageResource(toprated.getImageUrl());
       /* holder.likethump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=holder.getAdapterPosition();
                Log.d("data", "position = " + pos);
                if (isClicked == 0) {
                    holder.likethump.setImageResource(R.drawable.thumbsupempty);
                    String likecount=holder.like.getText().toString();
                    int likecount1=Integer.parseInt(likecount)-1;
                    holder.like.setText(String.valueOf(likecount1));
                    isClicked = 1;
                } else {
                    holder.likethump.setImageResource(R.drawable.thumbsup);
                    String likecount=holder.like.getText().toString();
                    int likecount1=Integer.parseInt(likecount)+1;
                    holder.like.setText(String.valueOf(likecount1));
                    isClicked = 0;
                }

            }
        });*/
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
