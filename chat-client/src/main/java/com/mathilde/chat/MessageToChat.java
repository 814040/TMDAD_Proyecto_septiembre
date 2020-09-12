package com.mathilde.chat;

import lombok.Data;

@Data
public class MessageToChat {
    private String date;
    private String from;
    private Integer idFrom;
    private Integer idTo;
    private String text;
}
