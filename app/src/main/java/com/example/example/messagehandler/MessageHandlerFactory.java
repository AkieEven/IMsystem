package com.example.example.messagehandler;

import android.util.SparseArray;

import com.example.example.utils.MessageProtobufType;

import cn.jiguang.imui.commons.models.IMessage;

public class MessageHandlerFactory {
    private MessageHandlerFactory() {

    }

    private static final SparseArray<IMessageHandler> HANDLERS = new SparseArray<>();

    static {
        HANDLERS.put(MessageProtobufType.REGISTER.getMsgType(),new RegisterMsgHandler());
        HANDLERS.put(MessageProtobufType.HANDSHAKE.getMsgType(), new LoginMsgHandler());
        HANDLERS.put(MessageProtobufType.FRIEND_STATUS_NOTICE.getMsgType(), new FriendStatusMsgHandler());
        HANDLERS.put(MessageProtobufType.FRIEND_INFO_RESPONSE.getMsgType(), new FriendInfoMsgHandler());
        HANDLERS.put(MessageProtobufType.SINGLE_CHAT.getMsgType(), new SingleMessageHandler());
        HANDLERS.put(MessageProtobufType.FRIEND_SEARCH_RESPONSE.getMsgType(), new FriendSearchResponseHandler());
        HANDLERS.put(MessageProtobufType.FRIEND_APPLY.getMsgType(), new FriendApplyHandler());
        HANDLERS.put(MessageProtobufType.GROUP_INFO.getMsgType(), new GroupInfoHandler());
    }

    public static IMessageHandler getHandlerByMsgType(int type) {
        return HANDLERS.get(type);
    }
}
