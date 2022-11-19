package com.example.example.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.example.R;
import com.example.example.bean.User;
import com.example.example.connection.IMClientBootstrap;
import com.example.example.utils.MessageUtil;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class NewFriendAdapter extends RecyclerView.Adapter<NewFriendAdapter.FriendViewHolder> {
    private ArrayList<User> mData;

    public NewFriendAdapter(ArrayList<User> friends) {this.mData = friends;}

    public ArrayList<User> getData() {return mData;}

    @NotNull
    @NonNull
    @Override
    public NewFriendAdapter.FriendViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_for_new_friend,parent,false);
        return new NewFriendAdapter.FriendViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NewFriendAdapter.FriendViewHolder holder, int position) {
        User user = mData.get(position);
        holder.friendName.setText(user.getDisplayName());
        holder.avatar.setImageResource(R.drawable.background);
        holder.acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IMClientBootstrap.getInstance().sendMsg(MessageUtil.buildFriendApplyResponse(user.getId(),true),false);
            }
        });
        holder.rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IMClientBootstrap.getInstance().sendMsg(MessageUtil.buildFriendApplyResponse(user.getId(),false),false);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class FriendViewHolder extends RecyclerView.ViewHolder {
        private TextView friendName;
        private ImageView avatar;
        private Button acceptButton;
        private Button rejectButton;

        public FriendViewHolder(@NonNull View itemView) {
            super(itemView);
            friendName = itemView.findViewById(R.id.name_for_new_friend_item);
            avatar = itemView.findViewById(R.id.image_for_new_friend_item);
            acceptButton = itemView.findViewById(R.id.button_accept);
            rejectButton = itemView.findViewById(R.id.button_reject);
        }
    }
}