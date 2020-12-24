package com.example.pkutoolman;

public class ChatData {

    private int id;//id

    private String name;//姓名

    private String chatMessage;//聊天内容

    private String time;//姓名

    private boolean isMeSend;//是否为本人发送

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getChatMessage() {
        return chatMessage;
    }
    public void setChatMessage(String chatMessage) {
        this.chatMessage = chatMessage;
    }
    public boolean isMeSend() {
        return isMeSend;
    }
    public void setMeSend(boolean isMeSend) {
        this.isMeSend = isMeSend;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public ChatData(int id, String name, String chatMessage, boolean isMeSend, String time) {
        super();
        this.id = id;
        this.name = name;
        this.chatMessage = chatMessage;
        this.isMeSend = isMeSend;
        this.time = time;
    }
    public ChatData() {
        super();
    }


}
