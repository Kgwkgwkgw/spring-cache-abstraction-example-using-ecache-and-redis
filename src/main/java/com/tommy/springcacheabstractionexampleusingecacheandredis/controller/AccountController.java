package com.tommy.springcacheabstractionexampleusingecacheandredis.controller;

import com.tommy.springcacheabstractionexampleusingecacheandredis.model.Account;
import com.tommy.springcacheabstractionexampleusingecacheandredis.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/accounts")
@RestController
@Slf4j
public class AccountController {
  final
  AccountService accountService;

  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  @GetMapping("")
  public List<Account> getAccounts() {
    return accountService.getAccounts();
  }

  @GetMapping("/{accountId}")
  public String getAccount(@PathVariable Long accountId) {
    log.info("accountId : {}", accountId);
    accountService.getAccount(accountId);
    accountService.getAccountIfMoreThanFive(Account.builder().id(accountId).build());
    accountService.getAccountCachingRedis(Account.builder().id(accountId).build());
    return "OK";
  }
  @GetMapping("/{accountId}/evict")
  public String evictAccount(@PathVariable Long accountId) {
    log.info("accountId : {}", accountId);
    accountService.evictAccountByIdInMemoery(accountId);
    accountService.evictAccountIfMoreThanFiveInMemory(Account.builder().id(accountId).build());
    accountService.evictAccountInRedis(Account.builder().id(accountId).build());
    return "OK";
  }
}
