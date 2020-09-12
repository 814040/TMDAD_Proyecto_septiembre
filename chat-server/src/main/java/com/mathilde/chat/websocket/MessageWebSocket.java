package com.mathilde.chat.websocket;

import lombok.Data;

@Data
public class MessageWebSocket {
    private String date;
    private String from;
    private String text;
    
    // getters and setters by lombok

}