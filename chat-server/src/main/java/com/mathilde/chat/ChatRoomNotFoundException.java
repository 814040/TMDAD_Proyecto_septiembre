package com.mathilde.chat;

class ChatRoomNotFoundException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  ChatRoomNotFoundException(Long id) {
    super("Could not find chat " + id);
  }
}