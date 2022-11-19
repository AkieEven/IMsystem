package com.example.example.connection.handler;

import com.example.example.connection.tcp.NettyTcpClient;
import com.example.example.bean.MessageProtobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class HeartbeatRespHandler extends ChannelInboundHandlerAdapter {
    private NettyTcpClient imClient;

    public HeartbeatRespHandler(NettyTcpClient client) {
        this.imClient = client;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg) {
        MessageProtobuf.Msg heartbeatRespMsg = (MessageProtobuf.Msg) msg;
        if(heartbeatRespMsg == null) return;

        if(imClient.getHeartbeatMsgType() == heartbeatRespMsg.getMsgType()) {
            System.out.println("收到服务端心跳响应消息");
        }
        else {
            ctx.fireChannelRead(msg);
        }
    }
}
