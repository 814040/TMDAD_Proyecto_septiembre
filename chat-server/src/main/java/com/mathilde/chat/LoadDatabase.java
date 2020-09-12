package com.mathilde.chat;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@Slf4j
class LoadDatabase {
  Users SuperUser = new Users("SuperUser", "xxxx");
  Users Mathilde = new Users("Mathilde", "xxxx");
  Users José = new Users("José", "xxxx");
  Users Francisco = new Users("Francisco", "xxxx");

  
  @Bean
  CommandLineRunner initDatabase(UserRepository userRepository, ChatRoomRepository chatRoomRepository, MessageRepository messageRepository) {
    return args -> {
      log.info("Preloading " + userRepository.save(SuperUser));
      log.info("Preloading " + userRepository.save(Mathilde));
      log.info("Preloading " + userRepository.save(José));
      log.info("Preloading " + userRepository.save(Francisco));
      log.info("Preloading " + chatRoomRepository.save(new ChatRoom(Mathilde.getIduser(), José.getIduser(), Francisco.getIduser())));
      };
  }
}