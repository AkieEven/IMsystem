package com.example.example.connection.handler;

import com.example.example.connection.Config;
import com.example.example.connection.tcp.NettyTcpClient;
import com.example.example.bean.MessageProtobuf;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.internal.StringUtil;

public class TCPReadHandler extends ChannelInboundHandlerAdapter {
    private NettyTcpClient imClient;

    public TCPReadHandler(NettyTcpClient client) {
        this.imClient = client;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        System.err.println("TCPReadHandler:channelInactive()");
        Channel channel = ctx.channel();
        if(channel != null) {
            channel.close();
            ctx.close();
        }
        imClient.resetConnect(false);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx,cause);
        System.err.println("TCPReadHandler:exceptionCaught()");
        Channel channel = ctx.channel();
        if(channel != null) {
            channel.close();
            ctx.close();
        }
        imClient.resetConnect(false);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{
        MessageProtobuf.Msg message = (MessageProtobuf.Msg) msg;
        if(message == null) return;

        int msgType = message.getMsgType();
        //如果是服务端发送的消息发送状态报告
        if (msgType == imClient.getServerSentReportMsgType()) {
            int status = message.getStatus();
            if (status == Config.DEFAULT_REPORT_SERVER_SEND_MSG_SUCCESSFUL) {
                imClient.getMsgTimeoutTimerManager().remove(message.getMsgId());
                System.out.println("消息发送成功，ID:" + message.getMsgId() + "，从超时管理器中移除。");
            }
        }

        //如果服务端无需确认客户端对于消息的接收
//        } else {
//            MessageProtobuf.Msg receivedReportMsg = buildReceivedReportMsg(message.getMsgId());
//            if(receivedReportMsg != null) {
//                imClient.sendMsg(receivedReportMsg);
//            }
//        }
        imClient.getMsgDispatcher().receiveMessage(message);
    }

//    private MessageProtobuf.Msg buildReceivedReportMsg(String msgId) {
//        if(StringUtil.isNullOrEmpty(msgId)) return null;
//        MessageProtobuf.Msg.Builder builder = MessageProtobuf.Msg.newBuilder();
//        builder.setMsgId(msgId);
//        builder.setMsgType(imClient.getClientReceivedReportMsgType());
//        builder.setTimestamp(System.currentTimeMillis());
//        return builder.build();
//    }
}
