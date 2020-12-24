package edu.pku.PKUToolMan.entity;

import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class Order {
    private int orderId;
    private int userId;
    private int toolManId;
    private String place;
    private String destination;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String description;
    private int state;

    public Order() {
        this.toolManId = -1;  // 为了检验toolManId之前是否为空值，设定为-1
    }

    public Order(int userId, String place, String destination, String description) {  // startTime
        this.userId = userId;
        this.toolManId = -1;
        this.place = place;
        this.destination = destination;
        this.description = description;
    }

    public Order(int orderId, int userId, int toolManId, String place, String destination, LocalDateTime startTime,
                 LocalDateTime endTime, String description, int state) {
        this.orderId = orderId;
        this.userId = userId;
        this.toolManId = toolManId;
        this.place = place;
        this.destination = destination;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.state = state;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getToolManId() {
        return toolManId;
    }

    public void setToolManId(int toolManId) {
        this.toolManId = toolManId;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", userId=" + userId +
                ", toolManId=" + toolManId +
                ", place='" + place + '\'' +
                ", destination='" + destination + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", description='" + description + '\'' +
                ", state=" + state +
                '}';
    }
}