package com.example.example.connection;

import com.example.example.connection.listener.OnEventListener;
import com.example.example.bean.MessageProtobuf;

public class MsgDispatcher {
    private OnEventListener mOnEventListener;
    public MsgDispatcher () {

    }

    public void setOnEventListener(OnEventListener listener) {
        this.mOnEventListener = listener;
    }

    public void receiveMessage(MessageProtobuf.Msg msg) {
        if(mOnEventListener == null) return;
        mOnEventListener.dispatchMessage(msg);
    }
}
