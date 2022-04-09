package com.example.example.model;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import cn.jiguang.imui.commons.models.IMessage;
import cn.jiguang.imui.commons.models.IUser;

public class Message implements IMessage {

    private long id;
    private String text;
    private IUser fromUser;
    private String timeString;
    private int type;
    private MessageStatus status;
    private String mediaFilePath;
    private long duration;

    public Message(String text, int type) {
        this.text = text;
        this.type = type;
        this.id = UUID.randomUUID().getLeastSignificantBits();
    }

    /**
     * Message id.
     *
     * @return unique
     */
    @Override
    public String getMsgId() {
        return String.valueOf(id);
    }

    /**
     * Get user info of message.
     *
     * @return UserInfo of message
     */
    @Override
    public IUser getFromUser() {
        return this.fromUser;
    }

    /**
     * Time string that display in message list.
     *
     * @return Time string
     */
    @Override
    public String getTimeString() {
        return timeString;
    }

    /**
     * Type of message
     *
     * @return integer
     */
    @Override
    public int getType() {
        return type;
    }

    @Override
    public MessageStatus getMessageStatus() {
        return status;
    }

    /**
     * Text of message.
     *
     * @return text
     */
    @Override
    public String getText() {
        return text;
    }

    /**
     * If message type is photo, voice, video or file,
     * get file path through this method.
     *
     * @return file path
     */
    @Override
    public String getMediaFilePath() {
        return mediaFilePath;
    }

    /**
     * If message type is voice or video, get duration through this method.
     *
     * @return duration of audio or video, TimeUnit: SECONDS.
     */
    @Override
    public long getDuration() {
        return duration;
    }

    @Override
    public String getProgress() {
        return null;
    }

    /**
     * Return extra key value of message
     *
     * @return {@link HashMap <>}
     */
    @Override
    public HashMap<String, String> getExtras() {
        return null;
    }
}
