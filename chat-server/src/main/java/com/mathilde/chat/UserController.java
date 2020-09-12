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
class UserController {

  private static UserRepository userRepository;

  UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  List<Users> list_users() {
    return userRepository.findAll();
  }
  // Aggregate root

  @GetMapping("/user")
  List<Users> all() {
    return userRepository.findAll();
  }

  @PostMapping("/user")
  public static Users newUser(@RequestBody Users newUser) {
    return userRepository.save(newUser);
  }

  // Single item

  @GetMapping("/user/{id}")
  Users one(@PathVariable Long id) {

    return userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException(id));
  }

  @PutMapping("/user/{id}")
  Users replaceUsers(@RequestBody Users newUser, @PathVariable Long id) {

    return userRepository.findById(id)
      .map(user -> {
        user.setUsername(newUser.getUsername());
        user.setPassword(newUser.getPassword());
        return userRepository.save(user);
      })
      .orElseGet(() -> {
        newUser.setIduser(id);
        return userRepository.save(newUser);
      });
  }

  @DeleteMapping("/user/{id}")
  void deleteUser(@PathVariable Long id) {
    userRepository.deleteById(id);
  }
}