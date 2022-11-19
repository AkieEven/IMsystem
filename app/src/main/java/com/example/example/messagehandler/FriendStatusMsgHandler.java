package com.example.example.messagehandler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.example.IMApp;
import com.example.example.UserData;
import com.example.example.bean.ContactGroup;
import com.example.example.bean.MessageProtobuf;
import com.example.example.bean.User;
import com.example.example.connection.IMClientBootstrap;
import com.example.example.database.AppDatabase;
import com.example.example.database.entity.FriendshipToUser;
import com.example.example.utils.MessageUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FriendStatusMsgHandler implements IMessageHandler{
    @Override
    public void handle(MessageProtobuf.Msg msg) {
        //解析在线状态数据
        JSONObject jsonObject = JSON.parseObject(msg.getExtend());
        String[] onlineFriends = jsonObject.getString("onlineFriends").split(",");
        String[] offlineFriends = jsonObject.getString("offlineFriends").split(",");

        //解析群组数据
        String[] groups = jsonObject.getString("groups").split(";");
        for (int i=0; i<groups.length; i++) {
            String[] arg = groups[i].split(":");
            UserData.getGroupPosition().put(arg[0],i);
            UserData.getContactGroups().add(new ContactGroup(arg[1]));
        }


        //本地数据库查询
        FriendshipToUser tmpFriendship = null;
        for (String friendId: onlineFriends) {
            try {
                tmpFriendship = AppDatabase.getInstance().FriendshipDao().selectFriendById(IMApp.sharedInstance().getUserId(),friendId);
            } finally {
                if(tmpFriendship == null) {
                    IMClientBootstrap.getInstance().sendMsg(MessageUtil.buildFriendInfoRequestMessage(friendId),false);
                } else {
                    User tmpUser = new User(tmpFriendship.friendId,tmpFriendship.friendName,true,tmpFriendship.friendAvatar);
                    UserData.getContactGroups().get(UserData.getGroupPosition().get(Long.toString(tmpFriendship.groupId))).addGroupMember(tmpUser);
                    tmpFriendship = null;
                }
            }
        }

        for (String friendId: offlineFriends) {
            try {
                tmpFriendship = AppDatabase.getInstance().FriendshipDao().selectFriendById(IMApp.sharedInstance().getUserId(),friendId);
            } finally {
                if(tmpFriendship == null) {
                    IMClientBootstrap.getInstance().sendMsg(MessageUtil.buildFriendInfoRequestMessage(friendId),false);
                } else {
                    User tmpUser = new User(tmpFriendship.friendId,tmpFriendship.friendName,false,tmpFriendship.friendAvatar);
                    UserData.getContactGroups().get(UserData.getGroupPosition().get(Long.toString(tmpFriendship.groupId))).addGroupMember(tmpUser);
                    tmpFriendship = null;
                }
            }
        }

        //拉取好友状态信息后拉取离线消息
        IMClientBootstrap.getInstance().sendMsg(MessageUtil.buildOfflineMessageRequestMessage());
    }
}
