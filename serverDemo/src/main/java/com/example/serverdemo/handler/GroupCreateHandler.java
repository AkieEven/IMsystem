package com.example.serverdemo.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.serverdemo.MessageProtobuf;
import com.example.serverdemo.database.IMSqlSessionFactory;
import com.example.serverdemo.entity.Group;
import com.example.serverdemo.entity.User;

import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.netty.channel.ChannelHandlerContext;

public class GroupCreateHandler {
    public static void handleMessage(MessageProtobuf.Msg message, ChannelHandlerContext ctx) {
        int userId = Integer.parseInt(message.getFromId());
        JSONObject jsonObject = JSON.parseObject(message.getExtend());
        String groupName = jsonObject.getString("groupName");

        String statement = "com.example.serverdemo.mapper.GroupMapper.insertGroup";
        Group group = new Group(userId,groupName);
        try (SqlSession sqlSession = IMSqlSessionFactory.getSession()) {
            sqlSession.insert(statement,group);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String statement2 = "com.example.serverdemo.mapper.GroupMapper.selectByUserId";
        List<Group> groupList = new ArrayList<>();
        try (SqlSession sqlSession = IMSqlSessionFactory.getSession()) {
            groupList = sqlSession.selectList(statement2, userId);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject responseJson = new JSONObject();
        MessageProtobuf.Msg.Builder builder = MessageProtobuf.Msg.newBuilder();
        builder.setMsgId(message.getMsgId());
        builder.setMsgType(6006);
        builder.setToId(message.getFromId());
        builder.setTimestamp(System.currentTimeMillis());
        StringBuilder sb = new StringBuilder();
        Iterator<Group> iterator = groupList.iterator();
        if(iterator.hasNext()) {
            sb.append(iterator.next().toString());
        }
        while (iterator.hasNext()) {
            sb.append(';');
            sb.append(iterator.next().toString());
        }
        responseJson.put("groups",sb.toString());
        builder.setExtend(responseJson.toString());

        ctx.writeAndFlush(builder.build());
    }
}
