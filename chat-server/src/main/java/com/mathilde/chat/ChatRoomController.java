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
class ChatRoomController {

  private static ChatRoomRepository chatRoomRepository;

  ChatRoomController(final ChatRoomRepository repository) {
    this.chatRoomRepository = repository;
  }

  // Aggregate root

  @GetMapping("/chatroom")
  List<ChatRoom> all() {
    return chatRoomRepository.findAll();
  }

  @PostMapping("/chatroom")
  public static ChatRoom newChatRoom(@RequestBody final ChatRoom newChatRoom) {
    return chatRoomRepository.save(newChatRoom);
  }

  // Single item

  @GetMapping("/chatroom/{id}")
  ChatRoom one(@PathVariable final Long id) {

    return chatRoomRepository.findById(id).orElseThrow(() -> new ChatRoomNotFoundException(id));
  }

  @PutMapping("/chatroom/{id}")
  ChatRoom replaceChatRoom(@RequestBody ChatRoom newChatRoom,@PathVariable Long id) {

    return chatRoomRepository.findById(id)
      .map(chatRoom -> {
        chatRoom.setIdChatRoom(newChatRoom.getIdChatRoom());
        chatRoom.setUser1ChatRoom(newChatRoom.getUser1ChatRoom());
        chatRoom.setUser2ChatRoom(newChatRoom.getUser2ChatRoom());
        return chatRoomRepository.save(chatRoom);
      })
      .orElseGet(() -> {
        newChatRoom.setIdChatRoom(id);
        return chatRoomRepository.save(newChatRoom);
      });
  }

  @DeleteMapping("/chat/{id}")
  void deleteMessage(@PathVariable final Long id) {
    chatRoomRepository.deleteById(id);
  }
}