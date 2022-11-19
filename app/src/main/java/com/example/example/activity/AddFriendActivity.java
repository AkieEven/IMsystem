package com.example.example.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.example.adapter.UserItemAdapter;
import com.example.example.bean.Message;
import com.example.example.bean.User;
import com.example.example.connection.IMClientBootstrap;
import com.example.example.databinding.ActivityAddFriendBinding;
import com.example.example.databinding.ActivityLoginBinding;
import com.example.example.event.ClientEventListener;
import com.example.example.event.Event;
import com.example.example.thread.ClientThreadPoolExecutor;
import com.example.example.utils.MessageUtil;

import java.util.ArrayList;

public class AddFriendActivity extends AppCompatActivity implements ClientEventListener {
    private ActivityAddFriendBinding binding;
    private ArrayList<User> friends;
    private UserItemAdapter userItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddFriendBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        friends = new ArrayList<>();
        userItemAdapter = new UserItemAdapter(friends);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.addFriendList.setAdapter(userItemAdapter);
        binding.addFriendList.setLayoutManager(layoutManager);
        binding.addFriendList.setItemAnimator(new DefaultItemAnimator());
    }

    public void searchFriend(View view) {
        String friendEmail = binding.addFriendInput.getText().toString();
        //todo:加入超时重传
        IMClientBootstrap.getInstance().sendMsg(MessageUtil.buildFriendSearchMsg(friendEmail),false);
    }

    @Override
    public void onEvent(String topic, int msgCode, int resultCode, Object obj) {
        User user = (User)obj;
        if (topic.equals(Event.FRIEND_SEARCH_RESPONSE)) {
            ClientThreadPoolExecutor.runOnMainThread(new Runnable() {
                @Override
                public void run() {
                    userItemAdapter.getData().add(user);
                    userItemAdapter.notifyItemChanged(0);
                }
            });
        }
    }
}