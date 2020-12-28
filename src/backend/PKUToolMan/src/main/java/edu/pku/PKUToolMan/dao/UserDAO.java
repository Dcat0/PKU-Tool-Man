package edu.pku.PKUToolMan.dao;

import edu.pku.PKUToolMan.entity.User;

public interface UserDAO {
    void create(User user);
    User queryById(int id);
    User queryByNickname(String nickname);
    User queryByEmail(String email);
    User queryByPhoneNum(String phoneNum);
    void modifyWith(User user);
}
