package com.example.example.connection;

import com.example.example.connection.Interface.ImClientInterface;
import com.example.example.bean.MessageProtobuf;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.util.internal.StringUtil;

public class MsgTimeoutTimerManager {
    private Map<String, MsgTimeoutTimer> mMsgTimeoutMap = new ConcurrentHashMap<>();
    private ImClientInterface imClient;

    public MsgTimeoutTimerManager(ImClientInterface client) {
        this.imClient = client;
    }

    public void add(MessageProtobuf.Msg msg) {
        if (msg == null) return;

        int handshakeMsgType = imClient.getHandshakeMsgType();
        int heartbeatMsgType = imClient.getHeartbeatMsgType();
        int clientReceivedReportMsgType = imClient.getClientReceivedReportMsgType();

        int msgType = msg.getMsgType();
        if (msgType == handshakeMsgType || msgType == heartbeatMsgType || msgType == clientReceivedReportMsgType)
            return;

        String msgId = msg.getMsgId();
        if (!mMsgTimeoutMap.containsKey(msgId)) {
            MsgTimeoutTimer timer = new MsgTimeoutTimer(imClient, msg);
            mMsgTimeoutMap.put(msgId, timer);
        }
        System.out.println("添加消息至超时管理器，消息ID:" + msgId + "，当前管理器消息数：" + mMsgTimeoutMap.size());
    }

    public void remove(String msgId) {
        if (StringUtil.isNullOrEmpty(msgId)) return;

        MsgTimeoutTimer timer = mMsgTimeoutMap.remove(msgId);
        MessageProtobuf.Msg msg = null;
        if (timer != null) {
            msg = timer.getMsg();
            timer.cancel();
            timer = null;
        }
        System.out.println("从超时消息管理器中移除消息" + msg);
    }

    public synchronized void onResetConnected() {
        for (Iterator<Map.Entry<String, MsgTimeoutTimer>> it = mMsgTimeoutMap.entrySet().iterator(); it.hasNext(); ) {
            it.next().getValue().sendMsg();
        }
    }
}
