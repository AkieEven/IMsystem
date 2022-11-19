package com.example.example.messagehandler;

import android.util.Log;

import com.example.example.activity.RegisterActivity;
import com.example.example.bean.MessageProtobuf;
import com.example.example.event.ClientEventCenter;
import com.example.example.event.Event;

public class RegisterMsgHandler implements IMessageHandler{
    private static final String TAG = RegisterMsgHandler.class.getSimpleName();

    @Override
    public void handle(MessageProtobuf.Msg msg) {
        if(msg == null) {
            Log.e(TAG,"未获得消息");
        }
        else {
            ClientEventCenter.dispatchEvent(Event.REGISTER_EVENT,0,0,msg);
        }

    }
}
