package com.mathilde.chat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import lombok.Data;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;


public class WebSocketEventSender {
    private static String URL = "ws://localhost:8080/chat";
    private static StompSession session;
    
    public static void connectWebSocket(){
        WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        
        StompSessionHandler sessionHandler = new MyStompSessionHandler();
        stompClient.connect(URL, sessionHandler);

        try {
            session = stompClient
                    .connect(URL,
                            new StompSessionHandlerAdapter() {})
                    .get();
        } catch (InterruptedException  | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static StompSession getSession(){
        return session;
    }

    public static void addMessage(int idReceiver, int idSender, String sender, String date, String message){
        if (idReceiver==-1){// Send to all client
            MessageWebSocket msg = new MessageWebSocket();
            msg.setFrom(sender);
            msg.setText(message);
            msg.setDate(date);
            session.send("/app/public", msg);
        }
        else{
            MessageToChat msg = new MessageToChat();
            msg.setFrom(sender);
            msg.setText(message);
            msg.setDate(date);
            msg.setIdTo(idReceiver);
            msg.setIdFrom(idSender);
            session.send("/app/message/"+Integer.toString(idReceiver), msg);
        }
    }

    /*public static void createUser(String username, String password){
        System.out.println("sending user");
        ArrayList<String> userAttributes = new ArrayList<String>(Arrays.asList(username, password));
        System.out.println(userAttributes);

        MessageWebSocket msg = new MessageWebSocket();
        msg.setFrom(username);
        msg.setText(password);
        msg.setDate(password);
        System.out.println("message created user");
        System.out.println(msg);

        session.send("/app/addUser", msg);
    }*/


    public static void createChat(ArrayList<Long> idUserChat){
        session.send("/app/addChat"+Integer.toString(idUserChat.size()), idUserChat);
    }



}