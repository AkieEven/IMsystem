package com.example.example.messagehandler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.example.UserData;
import com.example.example.bean.Friendship;
import com.example.example.bean.MessageProtobuf;
import com.example.example.bean.User;
import com.example.example.database.AppDatabase;

public class FriendInfoMsgHandler implements IMessageHandler{
    @Override
    public void handle(MessageProtobuf.Msg msg) {
        //解析数据
        JSONObject jsonObject = JSON.parseObject(msg.getExtend());
        long friendshipId = jsonObject.getLongValue("friendshipId");
        String userId = Integer.toString(jsonObject.getIntValue("userId"));
        String friendId = Integer.toString(jsonObject.getIntValue("friendId"));
        long groupId = jsonObject.getLongValue("groupId");
        String friendName = jsonObject.getString("friendName");
        String friendAvatar = jsonObject.getString("friendAvatar");

        Friendship friendship = new Friendship(friendshipId,userId,friendId,groupId,friendName,friendAvatar);
        boolean status = jsonObject.getBoolean("status");

        //插入数据库并构建
        AppDatabase.getInstance().FriendshipDao().insertFriendship(friendship);
        User userPersist = new User(friendId,friendName,friendAvatar);
        AppDatabase.getInstance().UserDao().insertUser(userPersist);
        User user = new User(friendId,friendName,status,friendAvatar);
        UserData.getContactGroups().get(UserData.getGroupPosition().get(Long.toString(jsonObject.getLongValue("groupId")))).addGroupMember(user);

        //派发事件？
    }
}
