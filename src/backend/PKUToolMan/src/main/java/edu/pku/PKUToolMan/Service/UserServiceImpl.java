package edu.pku.PKUToolMan.Service;

import edu.pku.PKUToolMan.DAO.UserDAO;
import edu.pku.PKUToolMan.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;

    @Override
    public void create(User user) {

    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public User queryBy(int id) {
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public User queryBy(String nickname) {
        return null;
    }

    @Override
    public void modifyWith(String nickname, String password, String email, String phoneNum) {

    }
}
