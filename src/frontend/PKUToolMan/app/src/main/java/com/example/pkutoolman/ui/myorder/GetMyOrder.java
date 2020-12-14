package com.example.pkutoolman.ui.myorder;

import android.widget.RelativeLayout;

import com.example.pkutoolman.baseclass.Order;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class GetMyOrder {
    static int a=0;
    static public void getMyOrder(int userID, ArrayList<Order> publishList, ArrayList<Order> receiveList) {
        a++;
        System.out.printf("a=%d\n", a);

        // 测试内容
        for (int i=0; i<a; ++i) {
            publishList.add(new Order(123, 321, 123, "45乙", "45", "123"));
            receiveList.add(new Order(123, 321, 123, "45乙", "45", "123"));
        }

/*
        ArrayList<Order> orderList = new ArrayList<>();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String url = "192.168.1.1/order/myorderlist?userID=" + String.valueOf(userID);
                    InputStream is = new URL(url).openStream();
                    BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                    StringBuilder sb = new StringBuilder();
                    int cp;
                    while ((cp = rd.read()) != -1) {
                        sb.append((char) cp);
                    }
                    JSONObject obj = new JSONObject(sb.toString());
                    String code = obj.getString("code");
                    JSONArray data = new JSONArray(obj.getJSONObject("data"));
                    for (int i = 0; i < data.length(); ++i) {
                        JSONObject o = data.getJSONObject(i);
                        Order order = new Order(o.getInt("id"),
                                o.getInt("userID"),
                                o.getInt("toolmanID"),
                                o.getString("place"),
                                o.getString("destination"),
                                o.getString("description")
                        );
                        if (order.toolmanID == userID) receiveList.add(order);
                        if (order.userID == userID) publishList.add(order);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        try {
            t.join(); //主UI线程等待该线程执行完毕
        } catch (Exception e) {
            e.printStackTrace();
        }

 */

    }



}
