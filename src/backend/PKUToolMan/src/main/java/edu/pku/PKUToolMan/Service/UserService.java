package edu.pku.PKUToolMan.Service;

import edu.pku.PKUToolMan.Entity.User;

public interface UserService {
    public void create(User user);
    public User queryBy(int id);
    public User queryBy(String nickname);
    public void modifyWith(String nickname, String password, String email, String phoneNum);
}
