package com.tommy.springcacheabstractionexampleusingecacheandredis.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

@Slf4j
@Profile({"default"})
@Configuration
public class RedisConfigForTest {
  private RedisServer redisServer;

  public RedisConfigForTest() {
    this.redisServer = new RedisServer(6379);
  }

  @PostConstruct
  public void startRedis() throws IOException {
    redisServer.start();
  }

  @PreDestroy
  public void stopRedis() {
    redisServer.stop();
  }
}
