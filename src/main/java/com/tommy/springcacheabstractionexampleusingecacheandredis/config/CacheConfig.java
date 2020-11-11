package com.tommy.springcacheabstractionexampleusingecacheandredis.config;

import org.ehcache.spi.serialization.Serializer;
import org.ehcache.spi.serialization.SerializerException;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.cache.Caching;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

import static java.time.Duration.ofSeconds;
import static java.util.Collections.singletonMap;
import static org.springframework.data.redis.cache.RedisCacheConfiguration.defaultCacheConfig;

@Configuration
@EnableCaching // 캐싱 활성화
// 여러가지 캐시 저장소를 사용할 것이기 때문에, cacheManager를 등록해야함
public class CacheConfig {
  @Primary
  @Bean
  public JCacheCacheManager cacheManager() throws URISyntaxException {
    return new JCacheCacheManager(Caching.getCachingProvider().getCacheManager(
      getClass().getResource("/ehcache.xml").toURI(),
      getClass().getClassLoader()
    ));
  }

  @Bean
  public RedisCacheManager redisCacheManager(RedisConnectionFactory connectionFactory) {
    RedisCacheConfiguration redisCacheConfiguration = defaultCacheConfig().serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
      .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

    return RedisCacheManager.builder(connectionFactory)
      .cacheDefaults(redisCacheConfiguration)
      .withInitialCacheConfigurations(singletonMap("account4", redisCacheConfiguration.disableCachingNullValues().entryTtl(ofSeconds(3600L))))
      .build();
  }
}
