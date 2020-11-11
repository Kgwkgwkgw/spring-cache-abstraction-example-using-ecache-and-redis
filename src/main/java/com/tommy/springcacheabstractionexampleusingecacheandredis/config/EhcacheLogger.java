package com.tommy.springcacheabstractionexampleusingecacheandredis.config;

import lombok.extern.slf4j.Slf4j;
import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
@Slf4j
public class EhcacheLogger implements CacheEventListener<Object, Object> {

  @Override
  public void onEvent(CacheEvent<?, ?> event) {
    log.info("EventType: '{}', Key: '{}', Old value : '{}, New value : '{}'", event.getType(), event.getKey(), event.getOldValue(), event.getNewValue());
  }
}
