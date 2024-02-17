package online.aquan.shortlink.project.mq.producer;


import cn.hutool.core.lang.UUID;
import lombok.RequiredArgsConstructor;
import online.aquan.shortlink.project.dto.biz.LinkStatsRecordDto;
import org.redisson.api.RBlockingDeque;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static online.aquan.shortlink.project.common.constant.RedisKeyConstant.DELAY_QUEUE_STATS_KEY;

@Component
@RequiredArgsConstructor
public class DelayLinkStatsProducer {
    
    private final RedissonClient redissonClient;

    /**
     * 发送延迟消费短链接统计
     *
     * @param statsRecord 短链接统计实体参数
     */
    public void send(LinkStatsRecordDto statsRecord) {
        statsRecord.setKeys(UUID.fastUUID().toString());
        RBlockingDeque<LinkStatsRecordDto> blockingDeque = redissonClient.getBlockingDeque(DELAY_QUEUE_STATS_KEY);
        RDelayedQueue<LinkStatsRecordDto> delayedQueue = redissonClient.getDelayedQueue(blockingDeque);
        delayedQueue.offer(statsRecord, 5, TimeUnit.SECONDS);
    }
}
