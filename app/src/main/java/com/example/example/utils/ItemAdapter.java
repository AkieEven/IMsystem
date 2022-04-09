package com.example.example.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.example.R;
import com.example.example.model.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private ArrayList<User> mData;

    public ItemAdapter(ArrayList<User> friends) {
        this.mData = friends;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_for_messagelist, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, int position) {
        User user = mData.get(position);
        holder.nameTextView.setText(user.getDisplayName());
        /* todo 实现数据库内部消息查询，暂用固定数据代替*/
        holder.timeTextView.setText("fuck you");
        holder.messageTextView.setText("fuck you");
        holder.avaterImageView.setImageResource(R.drawable.background);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView messageTextView;
        private TextView timeTextView;
        private TextView nameTextView;
        private ImageView avaterImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.item_text);
            timeTextView = itemView.findViewById(R.id.item_time);
            nameTextView = itemView.findViewById(R.id.item_name);
            avaterImageView = itemView.findViewById(R.id.item_image);
        }
    }

}
