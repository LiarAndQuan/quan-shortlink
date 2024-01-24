package online.aquan.shortlink.project.config;


import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 布隆过滤器配置
 */
@Configuration
public class RBloomFilterConfiguration {

    /**
     * 防止用户注册查询数据库的布隆过滤器
     */
    @Bean
    public RBloomFilter<String> shortLinkCachePenetrationBloomFilter(RedissonClient redissonClient) {
        RBloomFilter<String> cachePenetrationBloomFilter = redissonClient.getBloomFilter("shortLinkCachePenetrationBloomFilter");
        //设置布隆过滤器的预期容量和失误率,用于初始化
        cachePenetrationBloomFilter.tryInit(1000, 0.01);
        return cachePenetrationBloomFilter;
    }
}