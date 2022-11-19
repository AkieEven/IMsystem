package com.example.example.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.example.adapter.NewFriendAdapter;
import com.example.example.adapter.UserItemAdapter;
import com.example.example.bean.User;
import com.example.example.databinding.ActivityNewFriendBinding;
import com.example.example.event.ClientEventListener;
import com.example.example.event.Event;
import com.example.example.thread.ClientThreadPoolExecutor;

import java.util.ArrayList;

public class NewFriendActivity extends AppCompatActivity implements ClientEventListener {
    ActivityNewFriendBinding binding;
    private ArrayList<User> friends;
    private NewFriendAdapter newFriendAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewFriendBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        friends = new ArrayList<>();
        newFriendAdapter = new NewFriendAdapter(friends);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.friendList.setAdapter(newFriendAdapter);
        binding.friendList.setLayoutManager(layoutManager);
        binding.friendList.setItemAnimator(new DefaultItemAnimator());
    }

    public void toAddFriendActivity(View view) {
        Intent intent = new Intent(NewFriendActivity.this,AddFriendActivity.class);
        startActivity(intent);
    }
    @Override
    public void onEvent(String topic, int msgCode, int resultCode, Object obj) {
        User user = (User)obj;
        if (topic.equals(Event.FRIEND_APPLY)) {
            ClientThreadPoolExecutor.runOnMainThread(new Runnable() {
                @Override
                public void run() {
                    newFriendAdapter.getData().add(user);
                    newFriendAdapter.notifyDataSetChanged();
                }
            });
        }
    }
}