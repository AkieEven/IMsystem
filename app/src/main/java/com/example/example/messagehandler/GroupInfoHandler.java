package com.example.example.messagehandler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.example.UserData;
import com.example.example.bean.ContactGroup;
import com.example.example.bean.MessageProtobuf;
import com.example.example.messagehandler.event.ClientEventCenter;
import com.example.example.messagehandler.event.Event;

public class GroupInfoHandler implements IMessageHandler{
    @Override
    public void handle(MessageProtobuf.Msg msg) {
        JSONObject jsonObject = JSON.parseObject(msg.getExtend());

        //解析群组数据
        String[] groups = jsonObject.getString("groups").split(";");
        for (int i=0; i<groups.length; i++) {
            String[] arg = groups[i].split(":");
            if(UserData.getGroupPosition().containsKey(arg[0])) continue;
            UserData.getContactGroups().add(new ContactGroup(arg[1]));
            UserData.getGroupPosition().put(arg[0],UserData.getContactGroups().size()-1);
        }

        ClientEventCenter.dispatchEvent(Event.NEW_GROUP,0,0,null);
    }
}
