package com.example.example.messagehandler;

import android.util.Log;

import com.example.example.bean.MessageProtobuf;
import com.example.example.connection.IMClientBootstrap;
import com.example.example.thread.ClientThreadPoolExecutor;
import com.example.example.utils.MessageProtobufType;

public class MessageProcessor {
    private static final String TAG = MessageProcessor.class.getSimpleName();
    private static final MessageProcessor INSTANCE = new MessageProcessor();

    private MessageProcessor() {

    }

    public static MessageProcessor getInstance() {
        return INSTANCE;
    }

    public void receiveMsg(MessageProtobuf.Msg msg) {
        ClientThreadPoolExecutor.runInBackground(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("接收消息"+msg);
                    IMessageHandler messageHandler = MessageHandlerFactory.getHandlerByMsgType(msg.getMsgType());
                    if (messageHandler != null) {
                        messageHandler.handle(msg);
                        Log.d(TAG,"接收消息，消息类型"+msg.getMsgType());
                    }
                    else Log.e(TAG, "未找到消息处理handler，消息类型：" + msg.getMsgType());
                } catch (Exception e) {
                    Log.e(TAG, "消息接收处理出错：" + e.getMessage());
                }
            }
        });
    }

    public void sendMsg(MessageProtobuf.Msg msg) {
        ClientThreadPoolExecutor.runInBackground(new Runnable() {
            @Override
            public void run() {
                boolean isActive = IMClientBootstrap.getInstance().isActive();
                if (isActive) {
                    IMClientBootstrap.getInstance().sendMsg(msg);
                } else {
                    Log.e(TAG,"发送消息失败！");
                }
            }
        });
    }

    public void sendMsg(MessageProtobuf.Msg msg,boolean isJoinTimeoutManager) {
        ClientThreadPoolExecutor.runInBackground(new Runnable() {
            @Override
            public void run() {
                boolean isActive = IMClientBootstrap.getInstance().isActive();
                if (isActive) {
                    IMClientBootstrap.getInstance().sendMsg(msg,isJoinTimeoutManager);
                } else {
                    Log.e(TAG,"发送消息失败！");
                }
            }
        });
    }
}
