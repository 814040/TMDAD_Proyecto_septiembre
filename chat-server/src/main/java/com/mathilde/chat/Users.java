package com.mathilde.chat;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public
class Users {

  private @Id @GeneratedValue Long iduser;
  private String username;
  private String password;

  Users() {}

  public Users(final String username, final String password) {
    this.username = username;
    this.password = password;
  }
}

