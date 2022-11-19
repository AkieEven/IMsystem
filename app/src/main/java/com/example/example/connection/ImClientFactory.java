package com.example.example.connection;

import com.example.example.connection.Interface.ImClientInterface;
import com.example.example.connection.tcp.NettyTcpClient;

public class ImClientFactory {
    public static ImClientInterface getClient() {
        return NettyTcpClient.getInstance();
    }
}
