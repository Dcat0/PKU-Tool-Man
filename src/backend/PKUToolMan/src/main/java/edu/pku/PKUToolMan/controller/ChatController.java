package edu.pku.PKUToolMan.controller;

import edu.pku.PKUToolMan.entity.Chat;
import edu.pku.PKUToolMan.service.ChatServiceImpl;
import edu.pku.PKUToolMan.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatServiceImpl chatService;
    @PostMapping("/update")
    public Result updatechat(@RequestBody Map<String, Object> map) {

        //System.out.print("TEST1!!!!!");
        int orderId = (Integer)map.get("orderID");
        //System.out.print("TEST2!!!!!");
        int senderId = (Integer)map.get("senderID");
        //System.out.print("TEST3!!!!!");
        int receiverId = (Integer)map.get("receiverID");
        //System.out.print("TEST4!!!!!");
        String message = map.get("message").toString();
        //LocalDateTime sendtime= (LocalDateTime)map.get("sendertime") ;

        Chat chat = new Chat(orderId, senderId, receiverId ,message);
        //System.out.println(orderId);
        //System.out.println(senderId);
        //System.out.println(receiverId);

        try {
            //System.out.print("TEST5!!!!!");
            chatService.updateChat(chat);
            //System.out.print("TEST6!!!!!");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.RESPONSE_ERROR().message("create messsage failed, unable to insert");
        }
        return Result.SUCCESS();
    }

    @PostMapping("/query")
    public Result getchat(@RequestBody Map<String, Object> map) {
        int orderid = (Integer)map.get("orderID");
        List<Chat> chats;
        try {
            chats = chatService.queryChat(orderid);
        } catch (Exception e) {
            return Result.RESPONSE_ERROR().message("query message failed when getting all orderlist");
        }
        return Result.SUCCESS().data("chats", chats);
    }


}
