package com.example.serverdemo.handler;

import com.alibaba.fastjson.JSONObject;
import com.example.serverdemo.MessageProtobuf;
import com.example.serverdemo.StatusContainer;
import com.example.serverdemo.database.IMSqlSessionFactory;
import com.example.serverdemo.entity.Group;
import com.example.serverdemo.entity.User;
import com.sun.org.apache.xerces.internal.dom.PSVIElementNSImpl;

import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.netty.channel.ChannelHandlerContext;

public class FriendStatusResponseHandler {
    public static void handleMessage(MessageProtobuf.Msg message, ChannelHandlerContext ctx) {
        //提取数据
        int userId = Integer.parseInt(message.getFromId());

        //查询朋友
        List<Integer> friendList = new ArrayList<>();
        List<Group> groupList = new ArrayList<>();
        String statement1 = "com.example.serverdemo.mapper.FriendshipMapper.selectById";
        String statement2 = "com.example.serverdemo.mapper.GroupMapper.selectByUserId";
        try (SqlSession sqlSession = IMSqlSessionFactory.getSession()) {
            friendList = sqlSession.selectList(statement1, userId);
            groupList = sqlSession.selectList(statement2, userId);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }


        //查询在线状态
        boolean status = false;
        ArrayList<Integer> onlineFriendList = new ArrayList<>();
        ArrayList<Integer> offlineFriendList = new ArrayList<>();
        for (int id : friendList) {
            if (StatusContainer.getINSTANCE().isOnline(id)) {
                onlineFriendList.add(id);
            } else {
                offlineFriendList.add(id);
            }
        }
        String onlineFriends = listToString(onlineFriendList);
        String offlineFriends = listToString(offlineFriendList);

        //构造响应消息
        JSONObject responseJson = new JSONObject();
        MessageProtobuf.Msg.Builder builder = MessageProtobuf.Msg.newBuilder();
        builder.setMsgId(message.getMsgId());
        builder.setMsgType(5002);
        builder.setToId(message.getFromId());
        builder.setTimestamp(System.currentTimeMillis());
        responseJson.put("onlineFriends", onlineFriends);
        responseJson.put("offlineFriends", offlineFriends);

        //群组转字符串
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
        ctx.channel().writeAndFlush(builder.build());

    }

    private static String listToString(Iterable list) {
        final Iterator<?> it = list.iterator();
        if (!it.hasNext()) {
            return "";
        }
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(it.next());
        while (it.hasNext()) {
            stringBuilder.append(',');
            stringBuilder.append(it.next());
        }
        return stringBuilder.toString();
    }
}
