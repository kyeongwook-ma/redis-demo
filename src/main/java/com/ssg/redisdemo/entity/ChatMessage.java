package com.ssg.redisdemo.entity;

import java.io.Serializable;

public class ChatMessage implements Serializable{
    private String roomId;
    private String senderId;

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;

    @Override
    public String toString() {
        return "ChatMessage{" +
                "roomId='" + roomId + '\'' +
                ", senderId='" + senderId + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
