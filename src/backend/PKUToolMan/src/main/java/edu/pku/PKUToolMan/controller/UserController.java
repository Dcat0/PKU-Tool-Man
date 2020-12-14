package edu.pku.PKUToolMan.controller;

import edu.pku.PKUToolMan.entity.User;
import edu.pku.PKUToolMan.service.UserServiceImpl;
import edu.pku.PKUToolMan.utils.Result;
import edu.pku.PKUToolMan.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/register")
    public Result register(@RequestBody Map<String, Object> map) {
        String nickname = map.get("nickname").toString();
        String password = map.get("password").toString();
        String email = map.get("email").toString();
        String phoneNum = map.get("phoneNum").toString();
//            String nickname, String password,
//                           String email, String phoneNum) {
        System.out.println("nickname="+nickname+"\n"
                +"password="+password+"\n"
                +"email="+email+"\n"
                +"phoneNum="+phoneNum+"\n"
        );
        // check if conflicts database
        User user = null;
        try {
            user = userService.queryByNickname(nickname);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(user != null) {
            return Result.RESPONSE_ERROR().message("nickname used!");
        }
        try {
            user = userService.queryByEmail(email);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(user != null) {
            return Result.RESPONSE_ERROR().message("email used!");
        }
        try {
            user = userService.queryByPhoneNum(phoneNum);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(user != null) {
            return Result.RESPONSE_ERROR().message("phoneNum used!");
        }

        System.out.flush();
        System.out.println("CHECK THROUGH");
        System.out.println(user);
        System.out.flush();

        // insert database
        user = new User(nickname, password, email, phoneNum);
        try {
            userService.create(user);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.RESPONSE_ERROR().message("register failed, unable to insert");
        }
        Map<String, Object> logInfo = new HashMap<>();
        logInfo.put("username", phoneNum);
        logInfo.put("password", password);
        return login(logInfo);
    }

    @PostMapping("/login")
    public Result login(@RequestBody Map<String, Object> map) {
        String username = map.get("username").toString();
        String password = map.get("password").toString();
        System.out.flush();
        System.out.println("LOGIN CONTROLLER");
        System.out.println("    username:" + username);
        System.out.println("    password:" + password);
        System.out.flush();
        User loggingUser;
        // resolve username is email/phoneNum, and find
        try {
            if(username.contains("@")) {
                System.out.println("    QUERY_BY_EMAIL:" + username);
                System.out.flush();
                loggingUser = userService.queryByEmail(username);
            } else {
                System.out.println("    QUERY_BY_PHONENUM:" + username);
                System.out.flush();
                loggingUser = userService.queryByPhoneNum(username);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.RESPONSE_ERROR().message("database query error");
        }
        if(loggingUser == null) {
            return Result.RESPONSE_ERROR().message("user not exist");
        }
        // check password
        if(!loggingUser.getPassword().equals(password)) {
            return Result.RESPONSE_ERROR().message("password wrong!");
        }
        // success
        String token = TokenUtil.sign(loggingUser);
        return Result.SUCCESS().data("token", token).data("user", loggingUser);
    }

    /*
    * 使用了token，自带时间戳
    * 前端调用后端/user/logout接口成功后，应自行销毁token
    * */
    @PostMapping("/logout")
    public Result logout() {
        return Result.SUCCESS();
    }

    @PostMapping("/query")
    public Result query(@RequestBody Map<String, Object> map) {
        int id = Integer.parseInt(map.get("id").toString());
        System.out.println("QUERY: id: " + id);
        User user;
        // find user via id in database
        try {
            user = userService.queryById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.RESPONSE_ERROR().message("database query error");
        }
        // if not found
        if(user == null) {
            return Result.RESPONSE_ERROR().message("user not found");
        }
        // found
        return Result.SUCCESS().data("user", user);
    }

    @PostMapping("/modify")
    public Result modify(@RequestBody Map<String, Object> map) {
        int id = Integer.parseInt(map.get("id").toString());
        String nickname = map.get("nickname").toString();
        String password = map.get("password").toString();
        String newPassword = map.get("newPassword").toString();
        String email = map.get("email").toString();
        String phoneNum = map.get("phoneNum").toString();
        User user;
        // find user via nickname
        try {
            user = userService.queryById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.RESPONSE_ERROR().message("database query error");
        }
        if(user == null) {
            return Result.RESPONSE_ERROR().message("no such user");
        }
        // check password
        System.out.println(user);
        System.out.flush();
        if(!user.getPassword().equals(password)) {
            return Result.RESPONSE_ERROR().message("wrong password");
        }
        // modify database
        if(newPassword == null) newPassword = password; // if frontend rule is that?
        user = new User(user.getid(), nickname, newPassword, email, phoneNum);
        try {
            userService.modifyWith(user);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.RESPONSE_ERROR().message("update failed");
        }
        // success
        return Result.SUCCESS();
    }
}
