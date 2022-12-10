package com.example.example.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.example.adapter.UserItemAdapter;
import com.example.example.bean.MessageProtobuf;
import com.example.example.bean.User;
import com.example.example.connection.IMClientBootstrap;
import com.example.example.databinding.ActivityAddFriendBinding;
import com.example.example.messagehandler.event.ClientEventCenter;
import com.example.example.messagehandler.event.ClientEventListener;
import com.example.example.messagehandler.event.Event;
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

        ClientEventCenter.registerEventListener(this, Event.FRIEND_SEARCH_RESPONSE);
    }

    public void searchFriend(View view) {
        String friendEmail = binding.addFriendInput.getText().toString();
        //todo:加入应用层去重的机制
        IMClientBootstrap.getInstance().sendMsg(MessageUtil.buildFriendSearchMsg(friendEmail),false);
    }

    @Override
    public void onEvent(String topic, int msgCode, int resultCode, Object obj) {
        MessageProtobuf.Msg msg = (MessageProtobuf.Msg) obj;
        JSONObject jsonObject = JSON.parseObject(msg.getExtend());
        if (topic.equals(Event.FRIEND_SEARCH_RESPONSE)) {
            ClientThreadPoolExecutor.runOnMainThread(new Runnable() {
                @Override
                public void run() {
                    if(msg.getStatus() == 1) {
                        User user = new User(jsonObject.getString("userId"), jsonObject.getString("displayName"));
                        friends.clear();
                        friends.add(user);
                        userItemAdapter.notifyItemChanged(0);
                    } else {
                        String info = jsonObject.getString("error");
                        Toast toast = Toast.makeText(AddFriendActivity.this, info, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            });
        }
    }
}