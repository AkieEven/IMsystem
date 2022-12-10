package com.example.example.messagehandler;

import com.example.example.bean.Message;
import com.example.example.bean.MessageProtobuf;
import com.example.example.messagehandler.event.ClientEventCenter;
import com.example.example.messagehandler.event.Event;
import com.example.example.utils.MessageUtil;

public class SingleMessageHandler implements IMessageHandler{
    @Override
    public void handle(MessageProtobuf.Msg msg) {
        Message message = MessageUtil.protobufToTextMessage(msg);
        //todo:存入数据库
        //todo:发送ACK消息

        ClientEventCenter.dispatchEvent(Event.SINGLE_MESSAGE_EVENT,0,0,message);
    }
}
