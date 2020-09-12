package com.mathilde.chat;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;




@Data
@Entity
public
class Message {

  private @Id @GeneratedValue Long idmessage;
  private Long idSender;
  private Long idReceiver;
  private String date;
  private String textmessage;

  Message() {}

  public Message(Long idSender, Long idReceiver, String date, String textmessage) {
    this.idSender = idSender;
    this.idReceiver = idReceiver;
    this.date = date;
    this.textmessage = textmessage;
  }
}

