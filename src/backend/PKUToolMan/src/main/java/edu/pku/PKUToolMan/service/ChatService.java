package edu.pku.PKUToolMan.service;

import edu.pku.PKUToolMan.entity.Chat;

import java.util.List;

public interface ChatService {

    void updateChat(Chat chat);
    List<Chat> queryChat(int orderId);
}
