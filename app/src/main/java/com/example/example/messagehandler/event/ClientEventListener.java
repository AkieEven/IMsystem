package com.example.example.event;

public interface ClientEventListener {
    void onEvent(String topic,int msgCode,int resultCode,Object obj);
}
