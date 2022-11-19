package com.example.example.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.alibaba.fastjson.JSONObject;
import com.example.example.IMApp;
import com.example.example.bean.Message;
import com.example.example.connection.listener.OnEventListener;
import com.example.example.bean.MessageProtobuf;
import com.example.example.messagehandler.MessageHandlerFactory;
import com.example.example.messagehandler.MessageProcessor;

public class IMClientListener implements OnEventListener {

    @Override
    public void dispatchMessage(MessageProtobuf.Msg msg) {
        MessageProcessor.getInstance().receiveMsg(msg);
    }

    @Override
    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) IMApp.sharedInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null) {
            return info.isConnected();
        }
        return false;
    }

    @Override
    public int getHandshakeMsgType() {
        return MessageProtobufType.HANDSHAKE.getMsgType();
    }

    @Override
    public MessageProtobuf.Msg getHeartbeatMsg() {
        MessageProtobuf.Msg.Builder builder = MessageProtobuf.Msg.newBuilder();

        builder.setMsgId(RandomID.getStringID());
        builder.setMsgType(MessageProtobufType.HEARTBEAT.getMsgType());
        builder.setFromId(IMApp.sharedInstance().getUserId());
        builder.setTimestamp(System.currentTimeMillis());
        return builder.build();
    }

    @Override
    public int getHeartbeatMsgType() {
        return MessageProtobufType.HEARTBEAT.getMsgType();
    }

    @Override
    public int getReconnectInterval() {
        return 0;
    }

    @Override
    public int getConnectTimeout() {
        return 0;
    }

    @Override
    public int getForegroundHeartbeatInterval() {
        return 0;
    }

    @Override
    public int getBackgroundHeartbeatInterval() {
        return 0;
    }

    @Override
    public int getServerSentReportMsgType() {
        return MessageProtobufType.SERVER_MSG_SENT_STATUS_REPORT.getMsgType();
    }

    @Override
    public int getClientReceivedReportMsgType() {
        return MessageProtobufType.CLIENT_MSG_RECEIVED_STATUS_REPORT.getMsgType();
    }

    @Override
    public int getResendCount() {
        return 0;
    }

    @Override
    public int getResendInterval() {
        return 0;
    }
}
