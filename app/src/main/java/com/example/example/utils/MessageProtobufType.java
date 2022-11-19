package com.example.example.utils;

public enum MessageProtobufType {

    /*
     * 握手消息
     */
    HANDSHAKE(1001),

    /*
     * 心跳消息
     */
    HEARTBEAT(1002),

    /*
     * 客户端提交的消息接收状态报告
     */
    CLIENT_MSG_RECEIVED_STATUS_REPORT(1009),

    /*
     * 服务端返回的消息发送状态报告
     */
    SERVER_MSG_SENT_STATUS_REPORT(1010),

    /**
     * 单聊消息
     */
    SINGLE_CHAT(2001),

    /**
     * 群聊消息
     */
    GROUP_CHAT(3001),
    /**
     * 注册消息
     */
    REGISTER(4001),

    /**
     * 在线状态同步消息
     * 客户端发送用于请求服务端同步在线数据
     * 服务器发送给客户端用于同步在线状态数据
     */
    FRIEND_STATUS_REQUEST(5001),
    FRIEND_STATUS_NOTICE(5002),

    FRIEND_INFO_REQUEST(5003),
    FRIEND_INFO_RESPONSE(5004),

    OFFLINE_MESSAGE_REQUEST(5005),

    FRIEND_SEARCH_REQUEST(6001),
    FRIEND_SEARCH_RESPONSE(6002),

    FRIEND_APPLY(6003),
    FRIEND_APPLY_RESPONSE(6004),

    CREATE_GROUP(6005),
    GROUP_INFO(6006)
    ;


    private final int msgType;

    MessageProtobufType(int type) {
        this.msgType = type;
    }

    public int getMsgType() {
        return msgType;
    }
}
