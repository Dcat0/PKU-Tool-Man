package com.example.pkutoolman.ui.myorder;

import android.util.JsonReader;
import android.widget.RelativeLayout;

import com.example.pkutoolman.baseclass.Order;
import com.example.pkutoolman.baseclass.Post;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
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


        //ArrayList<Order> orderList = new ArrayList<>();
        JSONObject obj = Post.post("http://121.196.103.2:8080/user/login", "{\"username\":\"hello\",\"password\":\"1234\"}");
        System.out.println(obj);
                    /*String code = obj.getString("code");
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
                    }*/

    }



}
