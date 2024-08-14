package com.bcrjl.rss.cache;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 缓存配置
 *
 * @author yanqs
 */
@Slf4j
@Component
public class ConfigCache {
    public static Cache<String, Object> fifoCache = CacheUtil.newFIFOCache(10);

    public static Object getConfig(String key) {
        return fifoCache.get(key);
    }

    public static void setConfig(String key, Object value) {
        fifoCache.put(key, value);
    }
}
