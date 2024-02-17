package online.aquan.shortlink.project.mq.producer;


import lombok.RequiredArgsConstructor;
import online.aquan.shortlink.project.common.constant.RedisKeyConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@RequiredArgsConstructor
@Component
public class LinkStatsSaveProducer {

    private final StringRedisTemplate stringRedisTemplate;


    public void send(Map<String, String> produceMap) {
        stringRedisTemplate.opsForStream().add(RedisKeyConstant.SHORT_LINK_STATS_STREAM_TOPIC_KEY, produceMap);
    }

}
