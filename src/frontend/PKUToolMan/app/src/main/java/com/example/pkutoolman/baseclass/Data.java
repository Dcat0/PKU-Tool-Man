package com.example.pkutoolman.baseclass;

import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Data {
    private static int userID;
    private static String nickName;
    private static List<Order> myOrderInfo = new ArrayList<Order>();

    public static int getUserID() {
        return userID;
    }
    public static void setUserID(int _userID) {
        userID = _userID;
    }

    public static void setNickName(String name) { nickName = name; }
    public static String getNickName() { return nickName; }

    public static void setMyOrderInfo(List<Order> a) {
        myOrderInfo = a;
    }
    public static List<Order> getMyOrderInfo() {
        return myOrderInfo;
    }
}
