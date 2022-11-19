package com.example.serverdemo.handler;

import com.alibaba.fastjson.JSONObject;
import com.example.serverdemo.ChannelContainer;
import com.example.serverdemo.MessageProtobuf;
import com.example.serverdemo.entity.Message;
import com.example.serverdemo.utils.MessageType;
import com.example.serverdemo.utils.RandomID;

public class SingleMessageHandler {
    public static void sendMessage(Message message) {
        MessageProtobuf.Msg.Builder builder = MessageProtobuf.Msg.newBuilder();

        builder.setMsgId(RandomID.getStringID());
        builder.setToId(Integer.toString(message.getToId()));
        builder.setFromId(Integer.toString(message.getFromId()));
        builder.setMsgType(2001);
        builder.setMsgContentType(message.getMessageType());
        builder.setTimestamp(Long.valueOf(message.getTimestamp()));

        JSONObject responseObject = new JSONObject();
        responseObject.put("text",message.getText());
        responseObject.put("mediaFilePath",message.getMediaFilePath());
        responseObject.put("duration",message.getDuration());
        builder.setExtend(responseObject.toString());

        ChannelContainer.getInstance().getActiveChannelByUserId(Integer.toString(message.getToId())).getChannel().writeAndFlush(builder.build());
    }

    public static void handleSingleMessage(MessageProtobuf.Msg msg) {
        ChannelContainer.getInstance().getActiveChannelByUserId(msg.getToId()).getChannel().writeAndFlush(msg);
    }
}
