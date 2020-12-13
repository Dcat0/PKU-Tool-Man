package edu.pku.PKUToolMan.DAO;

import edu.pku.PKUToolMan.Entity.User;

public interface UserDAO {
    public void create(User user);
    public User queryBy(int id);
    public User queryBy(String nickname);
    public void modifyWith(User user);
}
