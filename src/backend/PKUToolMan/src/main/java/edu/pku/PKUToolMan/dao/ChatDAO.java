package edu.pku.PKUToolMan.dao;

import edu.pku.PKUToolMan.entity.Chat;

import java.util.List;

public interface ChatDAO {
    void updateChat(Chat chat);
    List<Chat> queryChat(Chat chat);
    void updatestatus(Chat chat);
    Boolean checkstatus(Chat chat);
}
