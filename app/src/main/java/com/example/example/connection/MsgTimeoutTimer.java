package com.example.example.connection;

import com.example.example.connection.Interface.ImClientInterface;
import com.example.example.bean.MessageProtobuf;

import java.util.Timer;
import java.util.TimerTask;

public class MsgTimeoutTimer extends Timer {
    private ImClientInterface imClient;
    private MessageProtobuf.Msg msg;
    private int currentResendCount = 0;
    private MsgTimeoutTask task;

    public MsgTimeoutTimer(ImClientInterface imClient, MessageProtobuf.Msg msg) {
        this.imClient = imClient;
        this.msg = msg;
        task = new MsgTimeoutTask();
        this.schedule(task, imClient.getResendInterval(), imClient.getResendInterval());
    }

    private class MsgTimeoutTask extends TimerTask {

        @Override
        public void run() {
            if (imClient.isClosed()) {
                if (imClient.getMsgTimeoutTimerManager() != null) {
                    imClient.getMsgTimeoutTimerManager().remove(msg.getMsgId());
                }
                return;
            }

            if (currentResendCount >= imClient.getResendCount()) {
                try {
                    MessageProtobuf.Msg.Builder builder = MessageProtobuf.Msg.newBuilder();
                    builder.setMsgId(msg.getMsgId());
                    builder.setMsgType(imClient.getServerSentReportMsgType());
                    builder.setStatus(Config.DEFAULT_REPORT_SERVER_SEND_MSG_FAILURE);
                    builder.setTimestamp(System.currentTimeMillis());
                    imClient.getMsgDispatcher().receiveMessage(builder.build());
                } finally {
                    imClient.getMsgTimeoutTimerManager().remove(msg.getMsgId());
                    imClient.resetConnect();
                    currentResendCount = 0;
                }
            } else {
                imClient.sendMsg(msg, false);
                System.out.println("正在重发消息" + msg);
                currentResendCount++;
            }
        }
    }

    @Override
    public void cancel() {
        if (task != null) {
            task.cancel();
            task = null;
        }
        super.cancel();
    }

    public MessageProtobuf.Msg getMsg() {
        return msg;
    }

    public void sendMsg() {
        imClient.sendMsg(msg, false);
        System.out.println("正在重发消息" + msg);
    }
}
