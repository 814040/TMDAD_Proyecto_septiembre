package com.mathilde.chat;

import java.sql.Date;
import java.text.SimpleDateFormat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

import lombok.Data;

@Data
public class MessageWebSocket {
    private String date;
    private String from;
    private String text;
    


    
}