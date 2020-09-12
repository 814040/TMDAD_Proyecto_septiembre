package com.mathilde.chat.websocket;

import com.mathilde.chat.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Controller
public class ChatController {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    /*@MessageMapping("/addUser")
    @SendTo("/topic/public")
    public MessageWebSocket addUser(MessageWebSocket msg/*ArrayList<String> connectionAttributes) {
        System.out.println("Creating user");
        //Users user = new Users(connectionAttributes.get(0), connectionAttributes.get(1));
        Users user = new Users(msg.getFrom(), msg.getText());
        UserController.newUser(user);
        System.out.println("Created user");
        MessageWebSocket messageWS = new MessageWebSocket();
        messageWS.setText("New User Created : "+user.getUsername()+"\nN° id : "+user.getIduser());
        messageWS.setFrom("SERVEUR");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date time = new Date();
        String date = formatter.format(time);
        messageWS.setDate(date);

        return messageWS;
    }*/

    @MessageMapping("/addChat3")
    @SendTo("/topic/public")
    public MessageWebSocket addChatRoom2(ArrayList<Long> idChatUser) {
        ChatRoom chatRoom = new ChatRoom(idChatUser.get(0), idChatUser.get(1), idChatUser.get(2));
        ChatRoomController.newChatRoom(chatRoom);
        MessageWebSocket messageWS = new MessageWebSocket();
        messageWS.setText("New Chat Created n° "+chatRoom.getIdChatRoom()+"\nMembers : "+idChatUser.get(0)+", "+idChatUser.get(1)+" and "+idChatUser.get(2));
        messageWS.setFrom("SERVEUR");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date time = new Date();
        String date = formatter.format(time);
        messageWS.setDate(date);

        return messageWS;
    }
    @MessageMapping("/addChat4")
    @SendTo("/topic/public")
    public MessageWebSocket addChatRoom3(ArrayList<Long> idChatUser) {
        ChatRoom chatRoom = new ChatRoom(idChatUser.get(0), idChatUser.get(1), idChatUser.get(2), idChatUser.get(3));
        ChatRoomController.newChatRoom(chatRoom);
        MessageWebSocket messageWS = new MessageWebSocket();
        messageWS.setText("New Chat Created n° "+chatRoom.getIdChatRoom()+"\nMembers : "+idChatUser.get(0)+", "+idChatUser.get(1)+", "+idChatUser.get(2)+" and "+idChatUser.get(3));
        messageWS.setFrom("SERVEUR");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date time = new Date();
        String date = formatter.format(time);
        messageWS.setDate(date);
        return messageWS;
    }

    @MessageMapping("/addChat5")
    @SendTo("/topic/public")
    public MessageWebSocket addChatRoom4(ArrayList<Long> idChatUser) {
        ChatRoom chatRoom = new ChatRoom(idChatUser.get(0), idChatUser.get(1), idChatUser.get(2), idChatUser.get(3), idChatUser.get(4));
        ChatRoomController.newChatRoom(chatRoom);
        MessageWebSocket messageWS = new MessageWebSocket();
        messageWS.setText("New Chat Created n° "+chatRoom.getIdChatRoom()+"\nMembers : "+idChatUser.get(0)+", "+idChatUser.get(1)+", "+idChatUser.get(2)+", "+idChatUser.get(3)+" and "+idChatUser.get(4));
        messageWS.setFrom("SERVEUR");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date time = new Date();
        String date = formatter.format(time);
        messageWS.setDate(date);
        return messageWS;
    }

    @MessageMapping("/public")
    @SendTo("/topic/public")
    public MessageWebSocket send(MessageWebSocket message) throws Exception {
        if (message.getFrom().equals("SuperUser")){ // If this message is sent by SuperUser to Every User
            Message msg = new Message(1L,0L, message.getDate(), message.getText());
            MessageController.newMessage(msg);
        };
        return message;
    }

    @MessageMapping("/message/{idToStr}")
    @SendTo("/topic/message/{idToStr}")
    public MessageWebSocket simple(@DestinationVariable String idToStr, MessageToChat receivedMsg) {
        System.out.println("send new message");
        // record message in database
        Message msg = new Message(Long.valueOf(receivedMsg.getIdFrom().longValue()),Long.valueOf(receivedMsg.getIdTo().longValue()), receivedMsg.getDate(), receivedMsg.getText());
        MessageController.newMessage(msg);
        return convertMessage(receivedMsg);
    }

    public MessageWebSocket convertMessage(MessageToChat receivedMsg){
        MessageWebSocket message = new MessageWebSocket();
        message.setDate(receivedMsg.getDate());
        message.setFrom(receivedMsg.getFrom());
        message.setText(receivedMsg.getText());
        return message;
    }

}


