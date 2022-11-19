package com.example.serverdemo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.serverdemo.handler.FriendApplyHandler;
import com.example.serverdemo.handler.FriendApplyResponseHandler;
import com.example.serverdemo.handler.FriendInfoResponseHandler;
import com.example.serverdemo.handler.FriendSearchRequestHandler;
import com.example.serverdemo.handler.FriendStatusResponseHandler;
import com.example.serverdemo.handler.GroupCreateHandler;
import com.example.serverdemo.handler.LoginResponseHandler;
import com.example.serverdemo.handler.OfflineMessageResponseHandler;
import com.example.serverdemo.handler.RegisterResponseHandler;
import com.example.serverdemo.handler.SingleMessageHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    private static final String TAG = com.example.serverdemo.ServerHandler.class.getSimpleName();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        System.out.println("ServerHandler channelActive()" + ctx.channel().remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        System.out.println("ServerHandler channelInactive()");
        // 用户断开连接后，移除channel
        ChannelContainer.getInstance().removeChannelIfConnectNoActive(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        System.out.println("ServerHandler exceptionCaught()");
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        System.out.println("ServerHandler userEventTriggered()");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MessageProtobuf.Msg message = (MessageProtobuf.Msg) msg;
        System.out.println("收到来自客户端的消息：" + message);
        int msgType = message.getMsgType();

        switch (msgType) {
            // 握手消息
            case 1001: {
                LoginResponseHandler.handleMessage(message,ctx);
                break;
            }

            // 心跳消息
            case 1002: {
                // 收到心跳消息，原样返回
                String fromId = message.getFromId();
                ChannelContainer.getInstance().getActiveChannelByUserId(fromId).getChannel().writeAndFlush(message);
                break;
            }

            case 2001: {
                SingleMessageHandler.handleSingleMessage(message);
                break;
            }

            case 3001: {
                // todo 群聊，自己实现吧，toId可以是群id，根据群id查找所有在线用户的id，循环遍历channel发送即可。
                break;
            }

            case 4001: {
                RegisterResponseHandler.handleMessage(message,ctx);
                break;
            }
            case 5001: {
                FriendStatusResponseHandler.handleMessage(message,ctx);
                break;
            }
            case 5003: {
                FriendInfoResponseHandler.handleMessage(message,ctx);
                break;
            }
            case 5005: {
                OfflineMessageResponseHandler.handleMessage(message,ctx);
                break;
            }
            case 6001: {
                FriendSearchRequestHandler.handleMessage(message,ctx);
                break;
            }
            case 6003: {
                FriendApplyHandler.handleMessage(message);
                break;
            }
            case 6004: {
                FriendApplyResponseHandler.handleMessage(message,ctx);
            }
            case 6005: {
                GroupCreateHandler.handleMessage(message,ctx);
            }
            default:
                break;
        }
    }
}
