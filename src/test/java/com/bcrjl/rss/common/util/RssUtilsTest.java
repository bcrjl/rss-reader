package com.bcrjl.rss.common.util;

import cn.hutool.core.lang.Console;
import com.bcrjl.rss.model.entity.RssEntity;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * RSSUtilsTest
 *
 * @author yanqs
 */
class RssUtilsTest {

    @Test
    void getRssList() {
        String url = "https://blog.yanqingshan.com/feed/";
        List<RssEntity> rssList = RssUtils.getRssList(url);
        rssList.forEach(obj -> {
            Console.log(obj);
        });
    }
}
