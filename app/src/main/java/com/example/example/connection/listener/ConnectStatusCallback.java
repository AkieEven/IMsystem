package com.example.example.connection.listener;

public interface ConnectStatusCallback {
    void OnConnecting();
    void OnConnectFailed();
    void OnConnectSuccess();
}
