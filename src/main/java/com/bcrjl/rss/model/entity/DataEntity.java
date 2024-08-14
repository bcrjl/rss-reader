package com.bcrjl.rss.model.entity;

import lombok.Data;

import java.util.List;

/**
 * @author yanqs
 * @since 2024-08-03
 */
@Data
public class DataEntity {
    /**
     * rss订阅链接（必填）
     */
    private RssListEntity rssList;

    /**
     * rss订阅更新时间间隔，单位分钟（必填）
     */
    private Long refresh;
}

class RssListEntity {
    private List<String> weibo;
    private List<String> other;
}
