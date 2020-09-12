package com.mathilde.chat;


import lombok.var;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MyStompSessionHandler extends StompSessionHandlerAdapter {

    private static Logger logger = LogManager.getLogger(MyStompSessionHandler.class);

    @Override 
    public void handleTransportError(StompSession session,Throwable exception){
        logger.error("Got a transport error", exception);
    }


    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        //logger.info("Subscribed to /topic/chat");
        session.subscribe("/topic/public", this);
        logger.info("Subscribed to /topic/public");
        //Subscribe to own topic
        session.subscribe("/topic/message/"+Integer.toString(MyCommands.getIdClient()), this);
        logger.info("Subscribed to /topic/message/"+Integer.toString(MyCommands.getIdClient()));
        // Suscribe to other chats
        ArrayList<Integer> idChatList = (ArrayList<Integer>) ReadHttpClient.subscribeChatList(MyCommands.getIdClient());
        int i = 0;
        while (i < idChatList.size()) {
            session.subscribe("/topic/message/"+idChatList.get(i), this);
            logger.info("Subscribed to /topic/message/"+idChatList.get(i));
            i++;
        }
        session.send("/app/public", getConnectionMessage());
        logger.info("Connection message sent to websocket server");
    }

    static void disconnect(StompSession session){
        session.send("/app/public", getDisconnectionMessage());
        logger.info("Disconnection message sent to websocket server");
    }
    
    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        logger.error("Got an exception", exception);
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return MessageWebSocket.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        MessageWebSocket msg = (MessageWebSocket) payload;
        logger.info("\nMessage : " + msg.getText() + "\nFrom : " + msg.getFrom() + "\nAt : " + msg.getDate()+"\n--------------------------------");
    }

    private MessageWebSocket getConnectionMessage() {
        MessageWebSocket msg = new MessageWebSocket();
        msg.setFrom("CLIENT");
        msg.setText(ReadHttpClient.getUserById(MyCommands.getIdClient())+ " is connected !");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date time = new Date();
        String date = formatter.format(time);
        msg.setDate(date);
        return msg;
    }

    private static MessageWebSocket getDisconnectionMessage() {
        MessageWebSocket msg = new MessageWebSocket();
        msg.setFrom("CLIENT");
        msg.setText(ReadHttpClient.getUserById(MyCommands.getIdClient())+ " disconnected !");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date time = new Date();
        String date = formatter.format(time);
        msg.setDate(date);
        return msg;
    }
}