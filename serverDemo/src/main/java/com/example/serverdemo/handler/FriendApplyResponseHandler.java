package com.example.serverdemo.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.serverdemo.ChannelContainer;
import com.example.serverdemo.MessageProtobuf;
import com.example.serverdemo.StatusContainer;
import com.example.serverdemo.database.IMSqlSessionFactory;
import com.example.serverdemo.entity.Friendship;
import com.example.serverdemo.entity.User;
import com.example.serverdemo.utils.FriendshipCard;
import com.example.serverdemo.utils.RandomID;

import org.apache.ibatis.session.SqlSession;

import io.netty.channel.ChannelHandlerContext;

public class FriendApplyResponseHandler {
    public static void handleMessage(MessageProtobuf.Msg message, ChannelHandlerContext ctx) {
        //todo:通知对方
        int userId = Integer.parseInt(message.getFromId());
        int friendId = Integer.parseInt(message.getToId());
        JSONObject jsonObject = JSON.parseObject(message.getExtend());
        boolean accept = jsonObject.getBooleanValue("accept");

        if(!accept) return;

        //查找用户
        String statement = "com.example.serverdemo.mapper.UserMapper.selectById";
        User user = null;
        User friend = null;
        try (SqlSession sqlSession = IMSqlSessionFactory.getSession()) {
            user = sqlSession.selectOne(statement,userId);
            friend = sqlSession.selectOne(statement,friendId);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        assert friend != null;
        Friendship friendship0 = new Friendship(userId,friendId,userId,friend.getDisplayName(),friend.getAvatar());
        Friendship friendship1 = new Friendship(friendId,userId,friendId,user.getDisplayName(),user.getAvatar());

        String statement1 = "com.example.serverdemo.mapper.FriendMapper.insertFriendship";
        try (SqlSession sqlSession = IMSqlSessionFactory.getSession()) {
            sqlSession.insert(statement1,userId);
            sqlSession.insert(statement1,friendId);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }


        String statement2 = "com.example.serverdemo.mapper.FriendshipMapper.selectFriendship";
        FriendshipCard friendshipCard = new FriendshipCard(userId,friendId);
        Friendship friendship = null;
        try (SqlSession sqlSession = IMSqlSessionFactory.getSession()) {
            friendship = sqlSession.selectOne(statement,friendshipCard);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //发送好友信息
        boolean status = StatusContainer.getINSTANCE().isOnline(friendId);

        JSONObject responseJson = new JSONObject();
        MessageProtobuf.Msg.Builder builder = MessageProtobuf.Msg.newBuilder();
        builder.setMsgId(RandomID.getStringID());
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
