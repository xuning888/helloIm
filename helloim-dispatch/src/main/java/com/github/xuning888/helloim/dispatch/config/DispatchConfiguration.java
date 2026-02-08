package com.github.xuning888.helloim.dispatch.config;

import com.github.xuning888.helloim.contract.contant.CommonConstant;
import com.github.xuning888.helloim.contract.gateway.DownMsgSvcProperties;
import com.github.xuning888.helloim.contract.kafka.KafkaProperties;
import com.github.xuning888.helloim.contract.util.IdGenerator;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author xuning
 * @date 2025/8/2 22:25
 */
@Configuration
public class DispatchConfiguration {

    @Bean
    public IdGenerator idGenerator(RedisTemplate<String, Object> redisTemplate) {
        return new IdGenerator(redisTemplate, CommonConstant.WorkerIdKey.dispatchWorkerIdKey);
    }

    @Bean
    @ConfigurationProperties(prefix = "im.kafka")
    public KafkaProperties kafkaProperties() {
        return new KafkaProperties();
    }

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        RedisSerializer keySerializer = new StringRedisSerializer();
        RedisSerializer valueSerializer = new GenericJackson2JsonRedisSerializer();
        redisTemplate.setKeySerializer(keySerializer);
        redisTemplate.setValueSerializer(valueSerializer);
        redisTemplate.setHashKeySerializer(keySerializer);
        redisTemplate.setHashValueSerializer(valueSerializer);
        return redisTemplate;
    }

    @Bean
    @ConfigurationProperties(prefix = "im.downmsgsvc")
    public DownMsgSvcProperties downMsgSvcProperties() {
        return new DownMsgSvcProperties();
    }
}