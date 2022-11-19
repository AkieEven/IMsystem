package com.example.example.connection.handler;

import com.example.example.connection.tcp.NettyTcpClient;
import com.example.example.bean.MessageProtobuf;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

public class TCPChannelInitializerHandler extends ChannelInitializer<Channel> {
    private NettyTcpClient imClient;
    public TCPChannelInitializerHandler(NettyTcpClient client) {
        this.imClient = client;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        //TCP拆包沾包问题
        pipeline.addLast("frameEncoder", new LengthFieldPrepender(2));
        pipeline.addLast("frameDecoder",new LengthFieldBasedFrameDecoder(65535,0,2,0,2));

        pipeline.addLast(new ProtobufEncoder());
        pipeline.addLast(new ProtobufDecoder(MessageProtobuf.Msg.getDefaultInstance()));

        pipeline.addLast(LoginAuthRespHandler.class.getSimpleName(), new LoginAuthRespHandler(imClient));
        pipeline.addLast(HeartbeatRespHandler.class.getSimpleName(), new HeartbeatRespHandler(imClient));
        pipeline.addLast(TCPReadHandler.class.getSimpleName(), new TCPReadHandler(imClient));
    }
}
