package com.example.example.bean;

import java.util.HashMap;
import java.util.UUID;

import cn.jiguang.imui.commons.models.IMessage;
import cn.jiguang.imui.commons.models.IUser;

public class Message implements IMessage {

    private String id;
    private String text;
    private IUser fromUser;
    private String timeString;
    private int type;
    private MessageStatus status = MessageStatus.CREATED;
    private String mediaFilePath;
    private long duration;

    public Message() {
    }

    public Message(String text, int type) {
        this.text = text;
        this.type = type;
        this.id = String.valueOf(UUID.randomUUID().getLeastSignificantBits());
    }

    /**
     * Message id.
     *
     * @return unique
     */
    @Override
    public String getMsgId() {
        return id;
    }
    public void setMsgId(String id) {
        this.id = id;
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

    public void setFromUser(IUser user) {
        this.fromUser = user;
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
    public void setTimeString(String timestamp) {
        this.timeString = timestamp;
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
    public void setType(int type) {
        this.type = type;
    }

    @Override
    public MessageStatus getMessageStatus() {
        return status;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
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
    public void setText(String text) {
        this.text = text;
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
