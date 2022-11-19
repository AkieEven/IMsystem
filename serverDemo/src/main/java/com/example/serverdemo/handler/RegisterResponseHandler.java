package com.example.serverdemo.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.serverdemo.MessageProtobuf;
import com.example.serverdemo.database.IMSqlSessionFactory;
import com.example.serverdemo.entity.Group;
import com.example.serverdemo.entity.User;

import org.apache.ibatis.session.SqlSession;

import io.netty.channel.ChannelHandlerContext;
import jdk.internal.org.jline.utils.Log;

public class RegisterResponseHandler {
    public static void handleMessage(MessageProtobuf.Msg message, ChannelHandlerContext ctx) {
        int status = 0;


        String statement2 = "com.example.serverdemo.mapper.UserMapper.insertUser";
        JSONObject jsonObject = JSON.parseObject(message.getExtend());

        //返回消息构建
        JSONObject responseJson = new JSONObject();
        MessageProtobuf.Msg.Builder builder = MessageProtobuf.Msg.newBuilder();
        builder.setMsgId(message.getMsgId());
        builder.setMsgType(4001);
        builder.setTimestamp(System.currentTimeMillis());

        //检查是否已经注册
        User result = null;
        String statement1 = "com.example.serverdemo.mapper.UserMapper.selectByEmail";
        try (SqlSession sqlSession = IMSqlSessionFactory.getSession();) {
            result = sqlSession.selectOne(statement1, jsonObject.getString("email"));
            System.out.println(result);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (result != null) {
            responseJson.put("status", 0);
            responseJson.put("error", "注册失败:用户已注册");
            builder.setExtend(responseJson.toString());

            ctx.channel().writeAndFlush(builder.build());
            return;
        }

        User user = new User(jsonObject.getString("password"), jsonObject.getString("salt"),
                jsonObject.getString("email"), jsonObject.getString("displayName"), Long.toString(System.currentTimeMillis()));

        try (SqlSession sqlSession = IMSqlSessionFactory.getSession();) {
            status = sqlSession.insert(statement2, user);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        User newUser = null;
        try (SqlSession sqlSession = IMSqlSessionFactory.getSession();) {
            newUser = sqlSession.selectOne(statement1, jsonObject.getString("email"));
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String statement4 = "com.example.serverdemo.mapper.GroupMapper.insertUserGroup";
        assert newUser != null;
        Group group = new Group((long)newUser.getUserId(),newUser.getUserId(),"默认分组");
        try (SqlSession sqlSession = IMSqlSessionFactory.getSession();) {
            sqlSession.insert(statement4, group);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (status == 1) {
            responseJson.put("status", 1);
            responseJson.put("info", "注册成功");
        } else {
            responseJson.put("status", 0);
            responseJson.put("error", "注册失败");
        }
        builder.setExtend(responseJson.toString());

        ctx.channel().writeAndFlush(builder.build());
    }
}
