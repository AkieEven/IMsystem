package com.example.example.connection.listener;

import com.example.example.bean.MessageProtobuf;

public interface OnEventListener {
    //分发处理消息
    void dispatchMessage(MessageProtobuf.Msg msg);
    //获取网络可用状态
    boolean isNetworkAvailable();

    MessageProtobuf.Msg getHeartbeatMsg();
    int getHeartbeatMsgType();

    int getHandshakeMsgType();
    int getReconnectInterval();
    int getConnectTimeout();
    int getForegroundHeartbeatInterval();
    int getBackgroundHeartbeatInterval();
    int getServerSentReportMsgType();
    int getClientReceivedReportMsgType();
    int getResendCount();
    int getResendInterval();
}
