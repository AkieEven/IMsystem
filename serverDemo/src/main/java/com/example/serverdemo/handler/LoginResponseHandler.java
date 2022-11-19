package com.example.serverdemo.handler;

import com.alibaba.fastjson.JSONObject;
import com.example.serverdemo.ChannelContainer;
import com.example.serverdemo.MessageProtobuf;
import com.example.serverdemo.NettyChannel;
import com.example.serverdemo.StatusContainer;
import com.example.serverdemo.database.IMSqlSessionFactory;
import com.example.serverdemo.entity.User;
import com.example.serverdemo.utils.MD5Util;

import org.apache.ibatis.session.SqlSession;

import io.netty.channel.ChannelHandlerContext;

public class LoginResponseHandler {
    public static void handleMessage(MessageProtobuf.Msg message, ChannelHandlerContext ctx) {
        JSONObject jsonObject = JSONObject.parseObject(message.getExtend());
        String email = jsonObject.getString("email");
        String password = jsonObject.getString("password");

        //从数据库中通过email查询得到User信息
        User user = null;
        String statement = "com.example.serverdemo.mapper.UserMapper.selectByEmail";
        try (SqlSession sqlSession = IMSqlSessionFactory.getSession()) {
            user = sqlSession.selectOne(statement, email);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject responseJson = new JSONObject();
        MessageProtobuf.Msg.Builder builder = MessageProtobuf.Msg.newBuilder();
        builder.setMsgId(message.getMsgId());
        builder.setMsgType(1001);
        builder.setTimestamp(System.currentTimeMillis());

        //查无此人
        if(user == null) {
            responseJson.put("status",0);
            responseJson.put("error","账户未注册");
            builder.setExtend(responseJson.toString());
            ctx.channel().writeAndFlush(builder.build());
            ChannelContainer.getInstance().removeChannelIfConnectNoActive(ctx.channel());
            return;
        }

        //密码验证失败
        if(!MD5Util.check(password,user.getSalt(),user.getPassword())) {
            responseJson.put("status",0);
            responseJson.put("error","密码不正确");
            builder.setExtend(responseJson.toString());
            ctx.channel().writeAndFlush(builder.build());
            ChannelContainer.getInstance().removeChannelIfConnectNoActive(ctx.channel());
            return;
        }

        //登录成功
        ChannelContainer.getInstance().saveChannel(new NettyChannel(Integer.toString(user.getUserId()),ctx.channel()));
        StatusContainer.getINSTANCE().userComeOnline(user.getUserId());
        responseJson.put("status",1);
        responseJson.put("userId",user.getUserId());
        responseJson.put("displayName",user.getDisplayName());
        responseJson.put("avatar",user.getAvatar());
        builder.setExtend(responseJson.toString());
        System.out.println(builder.build());
        ctx.channel().writeAndFlush(builder.build());
    }
}
