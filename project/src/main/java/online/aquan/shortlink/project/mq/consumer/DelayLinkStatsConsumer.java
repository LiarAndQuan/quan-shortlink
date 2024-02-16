package online.aquan.shortlink.project.mq.consumer;

import lombok.RequiredArgsConstructor;
import online.aquan.shortlink.project.dto.biz.LinkStatsRecordDto;
import online.aquan.shortlink.project.service.LinkService;
import org.redisson.api.RBlockingDeque;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.locks.LockSupport;

import static online.aquan.shortlink.project.common.constant.RedisKeyConstant.DELAY_QUEUE_STATS_KEY;


@Component
@RequiredArgsConstructor
public class DelayLinkStatsConsumer implements InitializingBean {


    private final RedissonClient redissonClient;
    private final LinkService LinkService;

    public void onMessage() {
        Executors.newSingleThreadExecutor(
                        runnable -> {
                            Thread thread = new Thread(runnable);
                            thread.setName("delay_short-link_stats_consumer");
                            thread.setDaemon(Boolean.TRUE);
                            return thread;
                        })
                .execute(() -> {
                    RBlockingDeque<LinkStatsRecordDto> blockingDeque = redissonClient.getBlockingDeque(DELAY_QUEUE_STATS_KEY);
                    RDelayedQueue<LinkStatsRecordDto> delayedQueue = redissonClient.getDelayedQueue(blockingDeque);
                    for (; ; ) {
                        try {
                            LinkStatsRecordDto statsRecord = delayedQueue.poll();
                            if (statsRecord != null) {
                                LinkService.linkStats(null, null, statsRecord);
                                continue;
                            }
                            LockSupport.parkUntil(500);
                        } catch (Throwable ignored) {
                        }
                    }
                });
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        onMessage();
    }
}
