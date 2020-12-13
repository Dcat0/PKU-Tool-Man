package edu.pku.PKUToolMan.Controller;

import edu.pku.PKUToolMan.Entity.User;
import edu.pku.PKUToolMan.Service.UserService;
import edu.pku.PKUToolMan.Utils.Result;
import edu.pku.PKUToolMan.Utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(@RequestBody String nickname, @RequestBody String password,
                           @RequestBody String email, @RequestBody String phoneNum) {
        // check if conflicts database
        User user;
        user = userService.queryByNickname(nickname);
        if(user != null) {
            return Result.RESPONSE_ERROR().message("nickname used!");
        }
        user = userService.queryByEmail(email);
        if(user != null) {
            return Result.RESPONSE_ERROR().message("email used!");
        }
        user = userService.queryByPhoneNum(phoneNum);
        if(user != null) {
            return Result.RESPONSE_ERROR().message("phoneNum used!");
        }
        // insert database
        user = new User(nickname, password, email, phoneNum);
        try {
            userService.create(user);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.RESPONSE_ERROR().message("register failed, unable to insert");
        }
        return Result.SUCCESS();
    }

    @PostMapping("/login")
    public Result login(@RequestBody String username, @RequestBody String password) {
        User loggingUser;
        // resolve username is email/phoneNum, and find
        try {
            if(username.contains("@")) {
                loggingUser = userService.queryByEmail(username);
            } else {
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
        return Result.SUCCESS().data("token", token);
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
    public Result query(@RequestBody int id) {
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
    public Result modify(@RequestBody String nickname, @RequestBody String password,
                         @RequestBody String newPassword,
                         @RequestBody String email, @RequestBody String phoneNum) {
        User user;
        // find user via nickname
        try {
            user = userService.queryByNickname(nickname);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.RESPONSE_ERROR().message("database query error");
        }
        // check password
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
