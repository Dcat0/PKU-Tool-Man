package com.example.pkutoolman;

import com.example.pkutoolman.baseclass.Post;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.*;

public class PostTest extends TestCase {
    Post test = new Post();

    public void testSuccess() {
        try {
            String password = MD5.encrypt("u");
            String expectedReturn = "200";
            String jsonMessage = "{\"username\":\"s@s\",\"password\":\"" + password + "\"}";

            System.out.println(jsonMessage);
            JSONObject actualReturn = test.post("http://121.196.103.2:8080/user/login",jsonMessage);
            System.out.println(actualReturn);
            String result = (actualReturn.getString("code")).toString();
            assertEquals("test success", expectedReturn, result);
        } catch (Exception e) {
            System.out.println(3);
            System.out.println(e.toString());
        }
    }

    public void testNoUser() {
        try {
            String password = MD5.encrypt("u");
            String expectedReturn = "500";
            String jsonMessage = "{\"username\":\"s@2\",\"password\":\"" + password + "\"}";

            System.out.println(jsonMessage);
            JSONObject actualReturn = test.post("http://121.196.103.2:8080/user/login",jsonMessage);
            System.out.println(actualReturn);
            String result = (actualReturn.getString("code")).toString();
            assertEquals("test success", expectedReturn, result);
        } catch (Exception e) {
            System.out.println(3);
            System.out.println(e.toString());
        }
    }

    public void testWrongPassword() {
        try {
            String password = MD5.encrypt("u123");
            String expectedReturn = "500";
            String jsonMessage = "{\"username\":\"s@s\",\"password\":\"" + password + "\"}";

            System.out.println(jsonMessage);
            JSONObject actualReturn = test.post("http://121.196.103.2:8080/user/login",jsonMessage);
            System.out.println(actualReturn);
            String result = (actualReturn.getString("code")).toString();
            assertEquals("test success", expectedReturn, result);
        } catch (Exception e) {
            System.out.println(3);
            System.out.println(e.toString());
        }
    }
}