package com.bcrjl.rss.model.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author yanqs
 * @since 2024-08-03
 */
@Data
@Builder
public class RssEntity {
    /**
     * 订阅地址
     */
    private String url;
    /**
     * 源名称
     */
    private String webTitle;
    /**
     * 标题
     */
    private String title;
    /**
     * 链接
     */
    private String link;
    /**
     * 描述
     */
    private String description;

    private Date createTime;
}
