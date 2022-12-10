package com.example.example.messagehandler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.example.bean.MessageProtobuf;
import com.example.example.bean.User;
import com.example.example.messagehandler.event.ClientEventCenter;
import com.example.example.messagehandler.event.Event;

public class FriendSearchResponseHandler implements IMessageHandler{
    @Override
    public void handle(MessageProtobuf.Msg msg) {
        ClientEventCenter.dispatchEvent(Event.FRIEND_SEARCH_RESPONSE,0,0,msg);
    }
}
