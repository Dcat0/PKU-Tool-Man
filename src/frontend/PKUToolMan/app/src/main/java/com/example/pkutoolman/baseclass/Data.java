package com.example.pkutoolman.baseclass;

import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Data {
    private static int userID;
    private static int orderID;
    private static int chatID;
    private static int toolmanID;
    private static String otherName;
    private static String nickName;
    private static String token;
    private static String baseURL = "http://121.196.103.2:8080";
    private static String Email;
    private static String PhoneNum;
    private static String Credit;
    private static List<Order> myOrderInfo = new ArrayList<Order>();

    public static void setUserID(int _userID) {
        userID = _userID;
    }
    public static int getUserID() {
        return userID;
    }

    public static void setOrderID(int _orderID) {
        orderID = _orderID;
    }
    public static int getOrderID() {
        return orderID;
    }

    public static void setChatID(int _chatID) {
        chatID = _chatID;
    }
    public static int getChatID() {
        return chatID;
    }

    public static void setToolmanID(int _toolmanID) {
        toolmanID = _toolmanID;
    }
    public static int getToolmanID() {
        return toolmanID;
    }

    public static void setOtherName(String name) { otherName = name; }
    public static String getOtherName() { return otherName; }

    public static void setNickName(String name) { nickName = name; }
    public static String getNickName() { return nickName; }

    public static void setEmail(String email) {Email = email;}
    public static String getEmail() {return Email;}

    public static void setPhoneNum(String phone) {PhoneNum = phone;}
    public static String getPhoneNum() {return PhoneNum;}

    public static void setCredit(String crdt) {Credit = crdt;}
    public static String getCredit() {return Credit;}

    public static void setToken(String _token){
        token = _token;
    }
    public static String getToken(){
        return token;
    }

    public static String getBaseURL() { return baseURL; }

    public static void setMyOrderInfo(List<Order> a) {
        myOrderInfo = a;
    }
    public static List<Order> getMyOrderInfo() {
        return myOrderInfo;
    }
}
