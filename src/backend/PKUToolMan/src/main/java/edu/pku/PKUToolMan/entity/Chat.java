package edu.pku.PKUToolMan.entity;

import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class Chat {

    private int orderId;
    private int senderId;
    private int receiverId;
    private LocalDateTime sendTime;
    private String message = null;
    private int status;

    public Chat(){

    }
    public Chat(int orderId,int receiverId){
        this.orderId = orderId;
        this.receiverId=receiverId;
        this.status=0;
    }
    public Chat(int orderId, int senderId, int receiverId,
                 String message) {
        this.orderId = orderId;
        this.senderId=senderId;
        this.receiverId=receiverId;
        LocalDateTime sendtime = LocalDateTime.now();
        this.sendTime=sendtime;
        this.message=message;
        this.status=0;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
