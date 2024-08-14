package com.bcrjl.rss.common.util;

import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.bcrjl.rss.common.constant.AppConstant.USER_AGENT;

/**
 * Html 工具类
 *
 * @author yanqs
 */
@Slf4j
public class HtmlParseUtils {
    /**
     * 获取html中的图片
     *
     * @param htmlContent html内容
     * @return
     */
    public static List<String> extractImageUrls(String htmlContent) {
        List<String> imageUrls = new ArrayList<>();
        String regex = "<img\\s+[^>]*?src\\s*=\\s*['\"]([^'\"]*?)['\"][^>]*?>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(htmlContent);
        while (matcher.find()) {
            String imageUrl = matcher.group(1);
            imageUrls.add(imageUrl);
        }
        return imageUrls;
    }

    /**
     * 获取微博图片流文件
     *
     * @param fileName 微博图片名称
     * @return HttpResponse
     */
    public static HttpResponse getWeiBoImagesHttpRequest(String fileName) {
        try {
            String url = "https://tvax3.sinaimg.cn/large/" + fileName;
            HttpRequest request = HttpRequest.get(url)
                    .header(Header.REFERER, "https://weibo.com/")
                    .header(Header.USER_AGENT, USER_AGENT)
                    .timeout(20000);
            return request.executeAsync();
        } catch (Exception e) {
            log.error("获取微博图片数据异常：", e);
            return null;
        }
    }
}
