package com.tommy.springcacheabstractionexampleusingecacheandredis.dto;

import com.tommy.springcacheabstractionexampleusingecacheandredis.model.Account;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountSignUpRequest {
  private String name;
  private Account.Gender gender;
  private String email;
  private String nickname;
  private String password;
  public static enum Gender {
    MALE, FEMALE;
  }
}
