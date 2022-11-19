package com.example.example.connection.handler;

import com.example.example.connection.tcp.NettyTcpClient;
import com.example.example.bean.MessageProtobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class HeartbeatHandler extends ChannelInboundHandlerAdapter {
    private NettyTcpClient imClient;

    public HeartbeatHandler(NettyTcpClient client) {
        this.imClient = client;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx,evt);

        if(evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            switch (state) {
                //服务端3次心跳都未回应，此时重连
                case READER_IDLE: {
                    imClient.resetConnect(false);
                    break;
                }
                //一段时间没有新消息发送，发送心跳消息
                case WRITER_IDLE: {
                    if(heartbeatTask == null) {
                        heartbeatTask = new HeartbeatTask(ctx);
                    }
                    imClient.getLoopGroup().executeWorkTask(heartbeatTask);
                    break;
                }
            }
        }
    }

    private HeartbeatTask heartbeatTask;
    private class HeartbeatTask implements Runnable {
        private ChannelHandlerContext ctx;

        public HeartbeatTask(ChannelHandlerContext ctx) {
            this.ctx = ctx;
        }

        @Override
        public void run() {
            if(ctx.channel().isActive()) {
                MessageProtobuf.Msg heartbeatMsg = imClient.getHeartbeatMsg();
                if(heartbeatMsg != null) {
                    imClient.sendMsg(heartbeatMsg,false);
                    System.out.printf("发送心跳，当前心跳间隔为%dms",imClient.getHeartbeatInterval());
                }
            }
        }
    }
}
