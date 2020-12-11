package edu.pku.PKUToolMan.Controller;

import edu.pku.PKUToolMan.Entity.User;
import edu.pku.PKUToolMan.Utils.Result;
import edu.pku.PKUToolMan.Utils.TokenUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @PostMapping("/register")
    public Result register(@RequestBody String nickname, @RequestBody String password,
                           @RequestBody String email, @RequestBody String phoneNum) {
        // check if conflicts database
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
