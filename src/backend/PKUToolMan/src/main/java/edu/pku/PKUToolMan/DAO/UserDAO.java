package edu.pku.PKUToolMan.DAO;

import edu.pku.PKUToolMan.Entity.User;

public interface UserDAO {
    void create(User user);
    User queryById(int id);
    User queryByNickname(String nickname);
    User queryByEmail(String email);
    User queryByPhoneNum(String phoneNum);
    void modifyWith(User user);
}
