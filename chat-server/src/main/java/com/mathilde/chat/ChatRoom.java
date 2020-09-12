package com.mathilde.chat;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Data
@Entity
public
class ChatRoom {

  private @Id @GeneratedValue Long idChatRoom;
  private Long user1ChatRoom=0L;
  private Long user2ChatRoom=0L;
  private Long user3ChatRoom=0L;
  private Long user4ChatRoom=0L;
  private Long user5ChatRoom=0L;


  ChatRoom() {}

  public ChatRoom(Long user1ChatRoom, Long user2ChatRoom) {
    this.user1ChatRoom = user1ChatRoom;
    this.user2ChatRoom = user2ChatRoom;
  }

  public ChatRoom(Long user1ChatRoom, Long user2ChatRoom, Long user3ChatRoom) {
    this.user1ChatRoom = user1ChatRoom;
    this.user2ChatRoom = user2ChatRoom;
    this.user3ChatRoom = user3ChatRoom;

  }

  public ChatRoom(Long user1ChatRoom, Long user2ChatRoom, Long user3ChatRoom, Long user4ChatRoom) {
    this.user1ChatRoom = user1ChatRoom;
    this.user2ChatRoom = user2ChatRoom;
    this.user3ChatRoom = user3ChatRoom;
    this.user4ChatRoom = user4ChatRoom;

  }

  public ChatRoom(Long user1ChatRoom, Long user2ChatRoom, Long user3ChatRoom, Long user4ChatRoom, Long user5ChatRoom) {
    this.user1ChatRoom = user1ChatRoom;
    this.user2ChatRoom = user2ChatRoom;
    this.user3ChatRoom = user3ChatRoom;
    this.user4ChatRoom = user4ChatRoom;
    this.user5ChatRoom = user5ChatRoom;
  }

}

