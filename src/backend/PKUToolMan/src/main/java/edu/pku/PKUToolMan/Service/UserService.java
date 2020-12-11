package edu.pku.PKUToolMan.Service;

import edu.pku.PKUToolMan.Entity.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public interface UserService {
    public void save(User user);
    public User query(int id);
    public void change();
}
