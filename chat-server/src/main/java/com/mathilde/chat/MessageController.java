package com.mathilde.chat;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public
class MessageController {

  private static MessageRepository messageRepository;

  MessageController(final MessageRepository repository) {
    this.messageRepository = repository;
  }

  // Aggregate root

  @GetMapping("/message")
  List<Message> all() {
    return messageRepository.findAll();
  }

  @PostMapping("/message")
  public static Message newMessage(@RequestBody final Message newMessage) {
    return messageRepository.save(newMessage);
  }

  // Single item

  @GetMapping("/message/{id}")
  Message one(@PathVariable final Long id) {

    return messageRepository.findById(id).orElseThrow(() -> new MessageNotFoundException(id));
  }

  @PutMapping("/message/{id}")
  Message replaceMessage(@RequestBody Message newMessage,@PathVariable Long id) {

    return messageRepository.findById(id)
      .map(message -> {
        message.setIdSender(newMessage.getIdSender());
        message.setIdReceiver(newMessage.getIdReceiver());
        message.setTextmessage(newMessage.getTextmessage());
        message.setDate(newMessage.getDate());
        return messageRepository.save(message);
      })
      .orElseGet(() -> {
        newMessage.setIdmessage(id);
        return messageRepository.save(newMessage);
      });
  }

  @DeleteMapping("/message/{id}")
  void deleteMessage(@PathVariable final Long id) {
    messageRepository.deleteById(id);
  }
}