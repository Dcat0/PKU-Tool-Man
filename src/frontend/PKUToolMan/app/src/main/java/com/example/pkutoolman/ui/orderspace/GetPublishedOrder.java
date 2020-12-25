package com.example.pkutoolman.ui.orderspace;

import com.example.pkutoolman.baseclass.Data;
import com.example.pkutoolman.baseclass.Order;
import com.example.pkutoolman.baseclass.Post;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetPublishedOrder {
    static int a=0;
    static public void getOrder(int userID, ArrayList<Order> publishList) {


        JSONObject obj = Post.post(Data.getBaseURL() + "/order/myorderlist", "{\"userID\":" + String.valueOf(userID) + "}");
        System.out.println(obj);
        try {
            JSONArray data = obj.getJSONObject("data").getJSONArray("orders");
            for (int i = 0; i < data.length(); ++i) {
                JSONObject o = data.getJSONObject(i);
                Order order = new Order(o.getInt("orderId"),
                        o.getInt("userId"),
                        o.getInt("toolManId"),
                        o.getString("endTime"),
                        o.getInt("state")
                );
                if (order.userID == userID) publishList.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
