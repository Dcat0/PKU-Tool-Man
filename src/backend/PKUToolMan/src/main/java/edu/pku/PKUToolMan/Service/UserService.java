package edu.pku.PKUToolMan.Service;

import edu.pku.PKUToolMan.Entity.User;

public interface UserService {
    void create(User user);
    User queryById(int id);
    User queryByNickname(String nickname);
    User queryByEmail(String email);
    User queryByPhoneNum(String phoneNum);
    void modifyWith(User user);
}
