package com.github.ograndebe.hdp.factories;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

@Configuration
public class RedisFactory {

    @Value("${server.redis}")
    private String redisServer;

    @Value("${server.redis.port}")
    private int redisPort;

    @Bean
    public Jedis jedis() {
        return new Jedis(redisServer,redisPort);
    }
}
