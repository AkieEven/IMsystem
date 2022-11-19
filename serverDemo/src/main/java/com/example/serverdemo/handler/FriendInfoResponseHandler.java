package com.example.serverdemo.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.serverdemo.MessageProtobuf;
import com.example.serverdemo.StatusContainer;
import com.example.serverdemo.database.IMSqlSessionFactory;
import com.example.serverdemo.entity.Friendship;
import com.example.serverdemo.utils.FriendshipCard;

import org.apache.ibatis.session.SqlSession;

import io.netty.channel.ChannelHandlerContext;

public class FriendInfoResponseHandler {
    public static void handleMessage(MessageProtobuf.Msg message, ChannelHandlerContext ctx) {
        //解析数据
        int userId = Integer.parseInt(message.getFromId());
        JSONObject jsonObject = JSON.parseObject(message.getExtend());
        int friendId = Integer.parseInt(jsonObject.getString("friendId"));

        //查询好友
        String statement = "com.example.serverdemo.mapper.FriendshipMapper.selectFriendship";
        FriendshipCard friendshipCard = new FriendshipCard(userId,friendId);
        Friendship friendship = null;
        try (SqlSession sqlSession = IMSqlSessionFactory.getSession()) {
            friendship = sqlSession.selectOne(statement,friendshipCard);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //查询在线状态
        boolean status = StatusContainer.getINSTANCE().isOnline(userId);

        //构建返回消息
        JSONObject responseJson = new JSONObject();
        MessageProtobuf.Msg.Builder builder = MessageProtobuf.Msg.newBuilder();
        builder.setMsgId(message.getMsgId());
        builder.setMsgType(5004);
        builder.setToId(message.getFromId());
        builder.setTimestamp(System.currentTimeMillis());
        if (friendship!=null) {
            responseJson.put("friendshipId", friendship.getFriendshipId());
            responseJson.put("userId", friendship.getUserId());
            responseJson.put("friendId",friendship.getFriendId());
            responseJson.put("groupId",friendship.getGroupId());
            responseJson.put("friendName",friendship.getFriendName());
            responseJson.put("friendAvatar",friendship.getFriendAvatar());
            responseJson.put("status",status);
        }
        builder.setExtend(responseJson.toString());
        ctx.writeAndFlush(builder.build());
    }
}
