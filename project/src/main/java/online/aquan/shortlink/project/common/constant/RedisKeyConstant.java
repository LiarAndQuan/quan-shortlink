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
}
