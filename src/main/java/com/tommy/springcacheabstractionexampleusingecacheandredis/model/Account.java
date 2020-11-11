package com.tommy.springcacheabstractionexampleusingecacheandredis.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Account {
  private Long id;
  private String name;
  private Gender gender;
  private String email;
  private String nickname;
  private String password;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Account account = (Account) o;

    return id != null ? id.equals(account.id) : account.id == null;
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }

  public static enum Gender {
    MALE, FEMALE;
  }
}
