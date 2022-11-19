package com.example.example.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.example.IMApp;
import com.example.example.bean.Message;
import com.example.example.bean.MessageProtobuf;
import com.example.example.database.AppDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import cn.jiguang.imui.commons.models.IMessage;

public class MessageUtil {
    //转为普通文字消息
    public static Message protobufToTextMessage(MessageProtobuf.Msg msg) {
        Message message = new Message();

        message.setMsgId(msg.getMsgId());

        try {
            JSONObject jsonObj = JSON.parseObject(msg.getExtend());
            message.setText(jsonObj.getString("text"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //todo:从数据库获取IUSER
        message.setFromUser(AppDatabase.getInstance().UserDao().selectByUserId(msg.getFromId()));
        //message.setFromUser();


        message.setTimeString(timestampLongToString(msg.getTimestamp()));

        message.setType(msg.getMsgType());

        message.setStatus(IMessage.MessageStatus.RECEIVE_SUCCEED);

        return message;
    }

    public static MessageProtobuf.Msg buildSingleTextMessage(String text,String friendId) {
        MessageProtobuf.Msg.Builder builder = MessageProtobuf.Msg.newBuilder();
        builder.setMsgId(RandomID.getStringID());
        builder.setFromId(IMApp.sharedInstance().getUserId());
        builder.setToId(friendId);
        builder.setTimestamp(System.currentTimeMillis());
        builder.setMsgType(MessageProtobufType.SINGLE_CHAT.getMsgType());
        builder.setMsgContentType(IMessage.MessageType.SEND_TEXT.ordinal());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("text",text);
        builder.setExtend(jsonObject.toString());

        return builder.build();
    }

    //构造注册消息
    public static MessageProtobuf.Msg buildRegisterMessage(String displayName,String email,String password,String salt) {
        MessageProtobuf.Msg.Builder builder = MessageProtobuf.Msg.newBuilder();

        builder.setMsgId(RandomID.getStringID());
        builder.setMsgType(MessageProtobufType.REGISTER.getMsgType());
        builder.setTimestamp(System.currentTimeMillis());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("displayName",displayName);
        jsonObject.put("email",email);
        jsonObject.put("password",password);
        jsonObject.put("salt",salt);
        builder.setExtend(jsonObject.toString());

        return builder.build();
    }

    //构建握手消息
    public static MessageProtobuf.Msg buildHandshakeMsg(String email,String password) {
        MessageProtobuf.Msg.Builder builder = MessageProtobuf.Msg.newBuilder();

        builder.setMsgId(RandomID.getStringID());
        builder.setMsgType(MessageProtobufType.HANDSHAKE.getMsgType());
        builder.setTimestamp(System.currentTimeMillis());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("password",password);
        jsonObject.put("email",email);
        builder.setExtend(jsonObject.toString());

        return builder.build();
    }

    //构建拉取用户在线状态消息
    public static MessageProtobuf.Msg buildFriendStatusRequestMessage() {
        MessageProtobuf.Msg.Builder builder = MessageProtobuf.Msg.newBuilder();

        builder.setMsgId(RandomID.getStringID());
        builder.setFromId(IMApp.sharedInstance().getUserId());
        builder.setMsgType(MessageProtobufType.FRIEND_STATUS_REQUEST.getMsgType());
        builder.setTimestamp(System.currentTimeMillis());

        return builder.build();
    }

    //构建好友信息拉取请求
    public static MessageProtobuf.Msg buildFriendInfoRequestMessage(String friendId) {
        MessageProtobuf.Msg.Builder builder = MessageProtobuf.Msg.newBuilder();

        builder.setMsgId(RandomID.getStringID());
        builder.setFromId(IMApp.sharedInstance().getUserId());
        builder.setMsgType(MessageProtobufType.FRIEND_INFO_REQUEST.getMsgType());
        builder.setTimestamp(System.currentTimeMillis());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("friendId",friendId);
        builder.setExtend(jsonObject.toString());

        return builder.build();
    }

    //离线消息拉取请求
    public static MessageProtobuf.Msg buildOfflineMessageRequestMessage() {
        MessageProtobuf.Msg.Builder builder = MessageProtobuf.Msg.newBuilder();

        builder.setMsgId(RandomID.getStringID());
        builder.setFromId(IMApp.sharedInstance().getUserId());
        builder.setMsgType(MessageProtobufType.OFFLINE_MESSAGE_REQUEST.getMsgType());
        builder.setTimestamp(System.currentTimeMillis());

        return builder.build();
    }

    //查找好友
    public static MessageProtobuf.Msg buildFriendSearchMsg(String email) {
        MessageProtobuf.Msg.Builder builder = MessageProtobuf.Msg.newBuilder();

        builder.setMsgId(RandomID.getStringID());
        builder.setMsgType(MessageProtobufType.FRIEND_SEARCH_REQUEST.getMsgType());
        builder.setTimestamp(System.currentTimeMillis());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email",email);
        builder.setExtend(jsonObject.toString());

        return builder.build();
    }

    //好友申请
    public static MessageProtobuf.Msg buildFriendApply(String friendId) {
        MessageProtobuf.Msg.Builder builder = MessageProtobuf.Msg.newBuilder();

        builder.setMsgId(RandomID.getStringID());
        builder.setMsgType(MessageProtobufType.FRIEND_APPLY.getMsgType());
        builder.setTimestamp(System.currentTimeMillis());
        builder.setFromId(IMApp.sharedInstance().getUserId());
        builder.setToId(friendId);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId",IMApp.sharedInstance().getUser().getId());
        jsonObject.put("displayName",IMApp.sharedInstance().getUser().getDisplayName());
        jsonObject.put("avatar",IMApp.sharedInstance().getUser().getAvatar());
        builder.setExtend(jsonObject.toString());

        return builder.build();
    }

    //好友申请回复
    public static MessageProtobuf.Msg buildFriendApplyResponse(String friendId,boolean accept) {
        MessageProtobuf.Msg.Builder builder = MessageProtobuf.Msg.newBuilder();

        builder.setMsgId(RandomID.getStringID());
        builder.setMsgType(MessageProtobufType.FRIEND_APPLY_RESPONSE.getMsgType());
        builder.setTimestamp(System.currentTimeMillis());
        builder.setFromId(IMApp.sharedInstance().getUserId());
        builder.setToId(friendId);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("accept",true);
        builder.setExtend(jsonObject.toString());

        return builder.build();
    }

    //
    public static MessageProtobuf.Msg buildCreateGroupMessage(String groupName) {
        MessageProtobuf.Msg.Builder builder = MessageProtobuf.Msg.newBuilder();

        builder.setMsgId(RandomID.getStringID());
        builder.setMsgType(MessageProtobufType.CREATE_GROUP.getMsgType());
        builder.setFromId(IMApp.sharedInstance().getUserId());
        builder.setTimestamp(System.currentTimeMillis());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("groupName",groupName);
        builder.setExtend(jsonObject.toString());

        return builder.build();
    }


    private static String timestampLongToString(long timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        return dateFormat.format(calendar);
    }
}
