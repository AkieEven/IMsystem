package com.example.example.messagehandler.event;

public interface ClientEventListener {
    void onEvent(String topic,int msgCode,int resultCode,Object obj);
}
