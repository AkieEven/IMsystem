package com.example.example.messagehandler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.example.bean.MessageProtobuf;
import com.example.example.bean.User;
import com.example.example.event.ClientEventCenter;
import com.example.example.event.Event;

public class FriendSearchResponseHandler implements IMessageHandler{
    @Override
    public void handle(MessageProtobuf.Msg msg) {
        JSONObject jsonObject = JSON.parseObject(msg.getExtend());
        User user = new User(jsonObject.getString("userId"),jsonObject.getString("displayName"),jsonObject.getString("avatar"));

        ClientEventCenter.dispatchEvent(Event.FRIEND_SEARCH_RESPONSE,0,0,user);
    }
}