package com.example.serverdemo.entity;

import lombok.Data;

@Data
public class Message {
    private long messageId;
    private int status;
    private int messageType;
    private int fromId;
    private int toId;
    private String text;
    private String timestamp;
    private String mediaFilePath;
    private String duration;
}
