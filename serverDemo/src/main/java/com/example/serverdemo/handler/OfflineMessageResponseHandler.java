package com.example.serverdemo.handler;

import com.example.serverdemo.MessageProtobuf;
import com.example.serverdemo.database.IMSqlSessionFactory;
import com.example.serverdemo.entity.Message;

import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;

import io.netty.channel.ChannelHandlerContext;

public class OfflineMessageResponseHandler {
    public static void handleMessage(MessageProtobuf.Msg message, ChannelHandlerContext ctx) {
        //解析数据
        int userId = Integer.parseInt(message.getFromId());

        //查询离线消息
        String statement = "com.example.serverdemo.mapper.MessageMapper.selectByToId";
        List<Message> offlineMessageList = new ArrayList<>();
        try(SqlSession sqlSession = IMSqlSessionFactory.getSession()) {
            offlineMessageList = sqlSession.selectList(statement,userId);
            sqlSession.commit();
        }

        //发送离线消息
        //TODO:暂时性不考虑离线消息丢失处理，需完善重发机制,暂时先处理单聊
        for (Message msg: offlineMessageList) {
            if(msg.getMessageType()==2001) {
                SingleMessageHandler.sendMessage(msg);
            }
        }
    }
}
