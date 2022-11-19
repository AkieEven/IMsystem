package com.example.serverdemo.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.serverdemo.MessageProtobuf;
import com.example.serverdemo.database.IMSqlSessionFactory;
import com.example.serverdemo.entity.User;
import com.example.serverdemo.utils.RandomID;

import org.apache.ibatis.session.SqlSession;

import io.netty.channel.ChannelHandlerContext;

public class FriendSearchRequestHandler {
    public static void handleMessage(MessageProtobuf.Msg message, ChannelHandlerContext ctx) {
        JSONObject jsonObject = JSON.parseObject(message.getExtend());
        String friendEmail = jsonObject.getString("email");


        String statement = "com.example.serverdemo.mapper.UserMapper.selectByEmail";
        User user = null;
        try (SqlSession sqlSession = IMSqlSessionFactory.getSession()) {
            user = sqlSession.selectOne(statement,friendEmail);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        MessageProtobuf.Msg.Builder builder = MessageProtobuf.Msg.newBuilder();

        builder.setMsgId(RandomID.getStringID());
        builder.setMsgType(6002);
        builder.setTimestamp(System.currentTimeMillis());

        JSONObject responseObject = new JSONObject();

        if(user == null){
            responseObject.put("error","该用户未注册");
            builder.setStatus(0);
        } else {
            responseObject.put("userId",user.getUserId());
            responseObject.put("displayName",user.getDisplayName());
            responseObject.put("avatar",user.getAvatar());
            builder.setStatus(1);
        }
        builder.setExtend(responseObject.toString());

        ctx.writeAndFlush(builder.build());
    }
}
