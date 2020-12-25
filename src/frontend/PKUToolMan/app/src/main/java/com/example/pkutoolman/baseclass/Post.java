package com.example.pkutoolman.baseclass;

import android.util.JsonReader;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.PreparedStatement;

public class Post extends Thread {

    private JSONObject result;
    private String url, jsonMessage, token;

    public static JSONObject post(String url, String jsonMessage) {
        // 比如 url = "http://121.196.103.2:8080/user/login"
        // jsonMessage = "{\"username\":\"hello\",\"password\":\"1234\"}"

        Post t = new Post();
        t.url = url;
        t.jsonMessage = jsonMessage;
        t.token = null;
        t.start();
        try {
            t.join(); //主UI线程等待该线程执行完毕
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t.result;
    }

    public static JSONObject post(String url, String jsonMessage, String token) {
        // 比如 url = "http://121.196.103.2:8080/user/login"
        // jsonMessage = "{\"username\":\"hello\",\"password\":\"1234\"}"

        Post t = new Post();
        t.url = url;
        t.jsonMessage = jsonMessage;
        t.token = token;
        t.start();
        try {
            t.join(); //主UI线程等待该线程执行完毕
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t.result;
    }

    @Override
    public void run() {
        try {
            HttpURLConnection conn = (HttpURLConnection)new URL(url).openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
            if (token != null) conn.setRequestProperty("token", token);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();
            String params = jsonMessage;
            OutputStream out = conn.getOutputStream();
            out.write(params.getBytes());
            out.flush();
            out.close();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            rd.close();
            result = new JSONObject(sb.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

}