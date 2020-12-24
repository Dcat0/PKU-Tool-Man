package edu.pku.PKUToolMan.service;

import edu.pku.PKUToolMan.dao.ChatDAO;
import edu.pku.PKUToolMan.entity.Chat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ChatServiceImpl implements ChatService {
    @Autowired
    private ChatDAO chatDAO;

    @Override
    public void updateChat(Chat chat) {
        chatDAO.updateChat(chat);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Chat> queryChat(int orderid) {

        return chatDAO.queryChat(orderid);
    }


}
