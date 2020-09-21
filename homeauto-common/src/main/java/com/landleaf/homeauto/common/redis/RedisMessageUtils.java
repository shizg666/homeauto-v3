package com.landleaf.homeauto.common.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @author Yujiumin
 * @version 2020/9/21
 */
@Component
public class RedisMessageUtils {

    @Autowired
    private LettuceConnectionFactory lettuceConnectionFactory;

    /**
     * 发布消息
     *
     * @param channel 通道
     * @param message 消息内容
     */
    public void publishMessage(String channel, Object message) {
        RedisConnection redisConnection = lettuceConnectionFactory.getConnection();
        byte[] channelBytes = channel.getBytes(StandardCharsets.UTF_8);
        byte[] messageBytes = JSON.toJSONBytes(message, SerializerFeature.SkipTransientField);
        redisConnection.publish(channelBytes, messageBytes);
    }


}
