package com.brstdev.ideafaris.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.brstdev.ideafaris.R;
import com.brstdev.ideafaris.model.History;
import com.brstdev.ideafaris.model.RecentStory;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by brst-pc16 on 9/13/16.
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    private List<History> histories;
    public int isClicked = 0;
    public int isClicked1 = 0;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView imageView;
        public TextView title, year, genre, timer, dislike, like;
        public ImageView likethump, unlikethump;

        public MyViewHolder(View view) {
            super(view);
            imageView = (CircleImageView) view.findViewById(R.id.title);
            genre = (TextView) view.findViewById(R.id.genre);
            year = (TextView) view.findViewById(R.id.year);
            timer = (TextView) view.findViewById(R.id.timer);
            dislike = (TextView) view.findViewById(R.id.dislike);
            like = (TextView) view.findViewById(R.id.like);
            likethump = (ImageView) view.findViewById(R.id.likethump);
            unlikethump = (ImageView) view.findViewById(R.id.unlikethump);
        }
    }


    public HistoryAdapter(List<History> histories) {
        this.histories = histories;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {


        History history = histories.get(position);
        holder.setIsRecyclable(false);

        holder.genre.setText(history.getGenre());
        holder.year.setText(history.getYear());
        holder.imageView.setImageResource(history.getImageUrl());
       /* holder.likethump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("data", "position = " + holder.getAdapterPosition());
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
        });
        holder.unlikethump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("data", "position = " + holder.getAdapterPosition());
                if (isClicked1 == 0) {
                    holder.unlikethump.setImageResource(R.drawable.thumbsdown);
                    String likecount=holder.dislike.getText().toString();
                    int likecount1=Integer.parseInt(likecount)+1;
                    holder.dislike.setText(String.valueOf(likecount1));
                    isClicked1 = 1;
                } else {
                    holder.unlikethump.setImageResource(R.drawable.thumbsdownempty);
                    String likecount=holder.dislike.getText().toString();
                    int likecount1=Integer.parseInt(likecount)-1;
                    holder.dislike.setText(String.valueOf(likecount1));
                    isClicked1 = 0;
                }

            }
        });*/
    }

    @Override
    public int getItemCount() {
        return histories.size();
    }
}
