package com.cn.config;


import com.cn.DTO.DeptDTO;
import com.cn.DTO.UserDTO;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

@Configuration
public class MyRedisConfig {

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName("192.168.0.113");
        redisStandaloneConfiguration.setPort(6379);
        redisStandaloneConfiguration.setPassword("123456");
        JedisConnectionFactory factory = new JedisConnectionFactory(redisStandaloneConfiguration);
        return factory;
    }

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(10);
        jedisPoolConfig.setMinIdle(2);
        jedisPoolConfig.setMaxWaitMillis(6000);
        jedisPoolConfig.setMaxTotal(8);
        return jedisPoolConfig;
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        redisTemplate.setValueSerializer(redisSerializer);
        redisTemplate.setHashKeySerializer(redisSerializer);
        redisTemplate.setHashValueSerializer(redisSerializer);
        return redisTemplate;
    }

    @Bean
    public RedisTemplate<Object, DeptDTO> deptDTORedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, DeptDTO> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer<DeptDTO> ser = new Jackson2JsonRedisSerializer<>(DeptDTO.class);
        template.setDefaultSerializer(ser);
        return template;
    }

    @Bean
    public RedisTemplate<Object, UserDTO> userDTORedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, UserDTO> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer<UserDTO> ser = new Jackson2JsonRedisSerializer<>(UserDTO.class);
        template.setDefaultSerializer(ser);
        return template;
    }

    //1.x版本配置manager
//    @Bean
//    public RedisCacheManager userCacheManager(RedisTemplate<Object, UserDTO> userDTORedisTemplate) {
//        RedisCacheManager cacheManager = new RedisCacheManager(userDTORedisTemplate);
//        //默认将cachename作为前缀
//        cacheManager.setUsePrefix(true);
//        return cacheManager;
//    }
//
//    @Bean
//    public RedisCacheManager deptCacheManager(RedisTemplate<Object, DeptDTO> deptDTORedisTemplate) {
//        RedisCacheManager cacheManager = new RedisCacheManager(deptDTORedisTemplate);
//        //默认将cachename作为前缀
//        cacheManager.setUsePrefix(true);
//        return cacheManager;
//    }

    /**
     * SpringBoot 2.X 版本配置方式
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public CacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        //  解决查询缓存转换异常的问题
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        CacheKeyPrefix keyPrefix = new CacheKeyPrefix() {
            @Override
            public String compute(String cacheName) {
                return cacheName + "::";
            }
        };
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(3))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer))
                .disableCachingNullValues();

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(config)
                .build();
    }


    @Bean
    public RedisTemplate<Object, Object> defaultDTORedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer<Object> ser = new Jackson2JsonRedisSerializer<>(Object.class);
        template.setDefaultSerializer(ser);
        return template;
    }
//    @Primary
//    @Bean
//    public RedisCacheManager defaultCacheManager(RedisTemplate<Object, Object> defaultDTORedisTemplate) {
//        RedisCacheManager cacheManager = new RedisCacheManager(defaultDTORedisTemplate);
//        //默认将cachename作为前缀
//        cacheManager.setUsePrefix(true);
//        return cacheManager;
//    }
}