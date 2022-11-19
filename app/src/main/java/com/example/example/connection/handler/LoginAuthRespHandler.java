package com.example.example.connection.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.example.IMApp;
import com.example.example.bean.User;
import com.example.example.connection.tcp.NettyTcpClient;
import com.example.example.bean.MessageProtobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class LoginAuthRespHandler extends ChannelInboundHandlerAdapter {

    private NettyTcpClient imClient;

    public LoginAuthRespHandler(NettyTcpClient client) {
        this.imClient = client;
    }

    //登录消息
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MessageProtobuf.Msg handshakeRespMsg = (MessageProtobuf.Msg) msg;

        if(handshakeRespMsg == null) return;

        int handshakeMsgType = imClient.getHandshakeMsgType();
        if(handshakeMsgType == handshakeRespMsg.getMsgType()) {
            System.out.println("客户端收到服务端握手消息");
            int status = -1;
            String userId = null;
            try {
                JSONObject jsonObj = JSON.parseObject(handshakeRespMsg.getExtend());
                status = jsonObj.getIntValue("status");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if(status == 1) {
                    JSONObject jsonObj = JSON.parseObject(handshakeRespMsg.getExtend());
                    userId = Integer.toString(jsonObj.getIntValue("userId"));
                    User user = new User(userId, jsonObj.getString("displayName"),"R.drawable.background");
                    IMApp.sharedInstance().setUser(user);

                    MessageProtobuf.Msg heartbeatMsg = imClient.getHeartbeatMsg();
                    if(heartbeatMsg == null) return;
                    //重发超时消息
                    imClient.getMsgTimeoutTimerManager().onResetConnected();
                    System.out.printf("发送心跳，当前心跳间隔为%dms",imClient.getHeartbeatInterval());
                    //疑问，如果未未完成handler添加便收到心跳回应怎么办
                    imClient.sendMsg(heartbeatMsg);
                    imClient.addHeartbeatHandler();
                } else if (status == -1){
                    imClient.resetConnect(false);
                }
            }
        }
        ctx.fireChannelRead(msg);
    }
}
