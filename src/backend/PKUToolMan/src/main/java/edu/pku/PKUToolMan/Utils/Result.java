package edu.pku.PKUToolMan.Utils;

import com.alibaba.druid.support.json.JSONUtils;

import java.util.HashMap;
import java.util.Map;

public class Result {
    private String code;
    private String message;
    private Map<String, Object> data = new HashMap<>();
    private Result() {}

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Map<String, Object> getData() { return data; }
    public void setData(Map<String, Object> data) { this.data = data; }

    // common static variables
    public static Result SUCCESS() {
        Result r = new Result();
        r.setCode("200");
        r.setMessage("success");
        return r;
    }

    public static Result FORMAT_ERROR() {
        Result r = new Result();
        r.setCode("400");
        r.setMessage("format_error");
        return r;
    }

    public static Result TOKEN_ERROR() {
        Result r = new Result();
        r.setCode("401");
        r.setMessage("token_error");
        return r;
    }

    public static Result AUTH_ERROR() {
        Result r = new Result();
        r.setCode("403");
        r.setMessage("auth_error");
        return r;
    }

    public static Result RESPONSE_ERROR() {
        Result r = new Result();
        r.setCode("500");
        r.setMessage("response_error");
        return r;
    }

    // chain functions
    public Result code(String code) {
        this.setCode(code);
        return this;
    }

    public Result message(String message) {
        this.setMessage(message);
        return this;
    }

    public Result data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public Result data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }

    public String toJSONString() {
        Map<String, Object> JSONMap = new HashMap<>();
        JSONMap.put("code", code);
        JSONMap.put("message", message);
        if(data != null) {
            JSONMap.put("data", data);
        } else {
            JSONMap.put("data", "");
        }
        return JSONUtils.toJSONString(JSONMap);
    }
}
