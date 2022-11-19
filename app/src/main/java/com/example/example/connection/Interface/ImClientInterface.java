package com.example.example.connection.Interface;

import com.example.example.connection.MsgDispatcher;
import com.example.example.connection.MsgTimeoutTimerManager;
import com.example.example.connection.listener.ConnectStatusCallback;
import com.example.example.connection.listener.OnEventListener;
import com.example.example.bean.MessageProtobuf;

import java.util.Vector;

public interface ImClientInterface {
    //初始化
    void init(Vector<String> serverList, OnEventListener listener, ConnectStatusCallback callback);
    //连接
    void resetConnect();
    //首次连接
    void resetConnect(boolean isFirst);
    //关闭连接
    void close();
    //判断是否关闭
    boolean isClosed();
    //发送消息
    void sendMsg(MessageProtobuf.Msg msg);
    void sendMsg(MessageProtobuf.Msg msg, boolean isJoinTimeoutManager);

    int getResendCount();
    int getClientReceivedReportMsgType();
    int getServerSentReportMsgType();
    int getResendInterval();
    int getHeartbeatMsgType();

    int getHandshakeMsgType();
    MessageProtobuf.Msg getHeartbeatMsg();

    MsgTimeoutTimerManager getMsgTimeoutTimerManager();
    MsgDispatcher getMsgDispatcher();

    void setAppStatus(boolean isBackground);
}
