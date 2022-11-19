package com.example.example.messagehandler;

import com.example.example.bean.MessageProtobuf;

public interface IMessageHandler {
    void handle(MessageProtobuf.Msg msg);
}
