package com.brstdev.ideafaris.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.brstdev.ideafaris.R;
import com.brstdev.ideafaris.model.FriendList;
import com.brstdev.ideafaris.model.GlobalStory;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by brst-pc16 on 9/13/16.
 */
public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.MyViewHolder> {

    private List<FriendList> friendList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView imageView;
        public TextView friendname, friendemail;

        public MyViewHolder(View view) {
            super(view);
            imageView = (CircleImageView) view.findViewById(R.id.title);
            friendname = (TextView) view.findViewById(R.id.friendname);
            friendemail = (TextView) view.findViewById(R.id.friendemail);

        }
    }

    public FriendListAdapter(List<FriendList> friendList) {
        this.friendList = friendList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.friend_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


        FriendList friendLists = friendList.get(position);
        holder.setIsRecyclable(false);
        //holder.title.setText(globalStory.getTitle());
        holder.friendname.setText(friendLists.getFreindname());
        holder.friendemail.setText(friendLists.getFreindemail());
        holder.imageView.setImageResource(friendLists.getImageUrl());
    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }
}

