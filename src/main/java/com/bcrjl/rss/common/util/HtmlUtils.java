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
public class HtmlUtils {
    /**
     * 获取html中的图片
     *
     * @param htmlContent html内容
     * @return
     */
    public static List<String> extractImageUrls(String htmlContent) {
        String regex = "<img\\s+[^>]*?src\\s*=\\s*['\"]([^'\"]*?)['\"][^>]*?>";
        return extractUrls(htmlContent, regex);
    }

    /**
     * 获取html中的视频
     *
     * @param htmlContent html内容
     * @return
     */
    public static List<String> extractVideoUrls(String htmlContent) {
        String regex = "<source\\s+[^>]*?src\\s*=\\s*['\"]([^'\"]*?)['\"][^>]*?>";
        return extractUrls(htmlContent, regex);
    }

    /**
     * 获取url中的文件名称
     *
     * @param url url
     * @return 文件名称
     */
    public static String getFileName(String url) {
        int lastSlashIndex = url.lastIndexOf('/');
        // 如果找到了斜杠，就从斜杠后面截取字符串
        String fileName = url.substring(lastSlashIndex + 1);
        int queryIndex = fileName.indexOf('?');
        if (queryIndex == -1) {
            return fileName;
        } else {
            return fileName.substring(0, queryIndex);
        }
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


    /**
     * 根据正则获取html中的内容
     *
     * @param htmlContent html内容
     * @param regex       正则
     * @return urls
     */
    private static List<String> extractUrls(String htmlContent, String regex) {
        List<String> urls = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(htmlContent);
        while (matcher.find()) {
            String url = matcher.group(1);
            urls.add(url);
        }
        return urls;
    }
}
