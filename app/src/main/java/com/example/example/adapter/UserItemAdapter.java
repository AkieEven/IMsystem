package com.example.example.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.example.R;
import com.example.example.bean.User;
import com.example.example.connection.IMClientBootstrap;
import com.example.example.utils.MessageUtil;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class UserItemAdapter extends RecyclerView.Adapter<UserItemAdapter.UserViewHolder> {
    private ArrayList<User> mData;

    public UserItemAdapter(ArrayList<User> friends) {this.mData = friends;}

    public ArrayList<User> getData() {return mData;}

    @NotNull
    @NonNull
    @Override
    public UserItemAdapter.UserViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_for_add_new_friend,parent,false);
        return new UserItemAdapter.UserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull UserItemAdapter.UserViewHolder holder, int position) {
        User user = mData.get(position);
        holder.friendName.setText(user.getDisplayName());
        holder.avatar.setImageResource(R.drawable.background);
        /*holder.addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IMClientBootstrap.getInstance().sendMsg(MessageUtil.buildFriendApply(user.getId()),false);
            }
        });
         */
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView friendName;
        private ImageView avatar;
        private Button addFriend;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            friendName = itemView.findViewById(R.id.name_for_add_new_friend_item);
            avatar = itemView.findViewById(R.id.image_for_add_new_friend_item);
            addFriend = itemView.findViewById(R.id.add_friend);
        }
    }
}
