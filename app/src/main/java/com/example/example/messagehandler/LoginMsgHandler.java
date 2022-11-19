package com.example.example.messagehandler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.example.IMApp;
import com.example.example.bean.MessageProtobuf;
import com.example.example.connection.IMClientBootstrap;
import com.example.example.event.ClientEventCenter;
import com.example.example.event.Event;
import com.example.example.utils.MessageUtil;

public class LoginMsgHandler implements IMessageHandler{
    @Override
    public void handle(MessageProtobuf.Msg msg) {
        //提取数据
        JSONObject info = JSON.parseObject(msg.getExtend());
        int status = info.getIntValue("status");

        //登录失败
        if (status == 0) {
            String error = info.getString("error");
            ClientEventCenter.dispatchEvent(Event.LOGIN_EVENT,0,0,msg);
            return;
        }

        //登录成功后，标记id
        ClientEventCenter.dispatchEvent(Event.LOGIN_EVENT,0,0,msg);

        //拉取好友在线信息
        IMClientBootstrap.getInstance().sendMsg(MessageUtil.buildFriendStatusRequestMessage(),false);
    }
}
