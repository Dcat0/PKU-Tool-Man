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
            // find user via nickname
            // if user exists
        // insert database
        // handle error
        // success
        return Result.SUCCESS();
    }

    @PostMapping("/login")
    public Result login(@RequestBody String username, @RequestBody String password) {
        User loggingUser = new User();
        // resolve username is email/phoneNum
        // find user via nickname
        // check password
        // handle error
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
        User queriedUser = new User();
        // find user via id in database
        // if not found
        // found
        return Result.SUCCESS().data("user", queriedUser);
    }

    @PostMapping("/modify")
    public Result modify(@RequestBody String nickname, @RequestBody String password,
                         @RequestBody String newPassword,
                         @RequestBody String email, @RequestBody String phoneNum) {
        // find user via nickname
        // check password
        // modify database
        // handle error
        // success
        return Result.SUCCESS();
    }
}
