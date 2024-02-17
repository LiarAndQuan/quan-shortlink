package online.aquan.shortlink.project.common.constant;


public class RedisKeyConstant {

    /**
     * 短链接跳转的key
     */
    public static final String GOTO_LINK_KEY = "short_link:goto";
    

    /**
     * 锁key
     */
    public static final String LOCK_GOTO_LINK_KEY = "short_link:lock_goto";

    /**
     * 缓存穿透的key
     */
    public static final String GOTO_LINK_IS_NULL_KEY = "short_link:link_is_null";

    /**
     * 读写锁
     */
    public static final String LOCK_GID_UPDATE_KEY = "short-link_lock_update-gid";
    
    public static final String DELAY_QUEUE_STATS_KEY = "short-link_delay-queue:stats";

    /**
     * 短链接统计判断是否新用户缓存标识
     */
    public static final String SHORT_LINK_STATS_UV_KEY = "short-link:stats:uv:";

    /**
     * 短链接统计判断是否新 IP 缓存标识
     */
    public static final String SHORT_LINK_STATS_UIP_KEY = "short-link:stats:uip:";

    /**
     * 短链接监控消息保存队列 Topic 缓存标识
     */
    public static final String SHORT_LINK_STATS_STREAM_TOPIC_KEY = "short-link:stats-stream";

    /**
     * 短链接监控消息保存队列 Group 缓存标识
     */
    public static final String SHORT_LINK_STATS_STREAM_GROUP_KEY = "short-link:stats-stream:only-group";
}
