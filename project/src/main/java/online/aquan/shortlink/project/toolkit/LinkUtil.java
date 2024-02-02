package online.aquan.shortlink.project.toolkit;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;

import java.util.Date;
import java.util.Optional;

import static online.aquan.shortlink.project.common.constant.LinkConstant.DEFAULT_CACHE_VALID_TIME;

public class LinkUtil {


    /**
     * 获取缓存预热的有效期
     *
     * @param validDate 过期时间,如果传入的是null那么表示永久有效
     * @return 秒数
     */
    public static Long getValidCacheTime(Date validDate) {
        return Optional.ofNullable(validDate).map(each ->
                        DateUtil.between(new Date(), each, DateUnit.SECOND))
                .orElse(DEFAULT_CACHE_VALID_TIME);
    }
}
