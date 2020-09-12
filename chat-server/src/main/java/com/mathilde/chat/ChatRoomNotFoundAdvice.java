package com.mathilde.chat;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class ChatRoomNotFoundAdvice {

  @ResponseBody
  @ExceptionHandler(ChatRoomNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String chatRoomNotFoundHandler(ChatRoomNotFoundException ex) {
    return ex.getMessage();
  }
}