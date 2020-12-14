package edu.pku.PKUToolMan.service;

import edu.pku.PKUToolMan.entity.User;

public interface UserService {
    void create(User user);
    User queryById(int id);
    User queryByNickname(String nickname);
    User queryByEmail(String email);
    User queryByPhoneNum(String phoneNum);
    void modifyWith(User user);
}
