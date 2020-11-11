package com.tommy.springcacheabstractionexampleusingecacheandredis.service;


import com.tommy.springcacheabstractionexampleusingecacheandredis.model.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class AccountService {
  List<Account> database;

  public AccountService() {
    database = new ArrayList<>();
    for (int i = 0; i < 50; i++) {
      database.add(Account.builder().name("user" + i + 1).email("user" + i + 1 + "@mail.com").gender(Account.Gender.MALE).password("pw").build());
    }

  }

  // SimpleKey.EMPTY가 키로 사용됌
  @Cacheable(cacheNames = "accounts")
  public List<Account> getAccounts() {
    log.info("get Accounts");
    return database;
  }

  // 기본 규칙에 의해 선언하지 않아도 됌
  @Cacheable(cacheNames = "account", key = "#id", sync = true)
  public Account getAccount(Long id) {
    log.info("cache request id : {}", id);
    return database.get(id.intValue() - 1);
  }

  @CacheEvict(cacheNames = "account", key = "#id")
  public void evictAccountByIdInMemoery(Long id) {
    log.info("evict request id : {}", id);
  }

  @Cacheable(cacheNames = "account3", key = "#account.id", condition = "#account.id > 5")
  public Account getAccountIfMoreThanFive(Account account) {
    log.info("cache request id if more than five : {}", account.getId());
    return database.get(account.getId().intValue() - 1);
  }

  @CacheEvict(cacheNames = "account3", key = "#account.id")
  public void evictAccountIfMoreThanFiveInMemory(Account account) {
    log.info("evict cache request id if more than five : {}", account.getId());
    return;
  }

  @Cacheable(cacheNames = "account4", key = "#account.id", cacheManager = "redisCacheManager", sync = true)
  public Account getAccountCachingRedis(Account account) {
    log.info("request id (reids): {}", account.getId());
    return database.get(account.getId().intValue() - 1);
  }

  @CacheEvict(cacheNames = "account4", key = "#account.id", cacheManager = "redisCacheManager")
  public void evictAccountInRedis(Account account) {
    log.info("evict request id (reids): {}", account.getId());
    return;
  }

}
