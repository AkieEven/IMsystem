package com.example.serverdemo.handler;

import com.example.serverdemo.ChannelContainer;
import com.example.serverdemo.MessageProtobuf;

import io.netty.channel.ChannelHandlerContext;

public class FriendApplyHandler {
    public static void handleMessage(MessageProtobuf.Msg message) {
        ChannelContainer.getInstance().getActiveChannelByUserId(message.getToId()).getChannel().writeAndFlush(message);
    }
}
