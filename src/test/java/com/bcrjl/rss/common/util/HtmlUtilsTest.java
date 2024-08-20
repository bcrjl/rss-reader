package com.bcrjl.rss.common.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static com.bcrjl.rss.common.constant.AppConstant.IMAGES_PATH;
import static com.bcrjl.rss.common.constant.AppConstant.USER_AGENT;

/**
 * Html 工具类测试
 *
 * @author yanqs
 */
class HtmlUtilsTest {

    /**
     * 获取html中的图片
     */
    @Test
    void extractImageUrls() {
        String str = "为什么温泉♨️水那么黄？ <img style=\"\" src=\"https://tvax1.sinaimg.cn/large/00759jQJly1hsg8uwgmavj31401hcdy4.jpg\" referrerpolicy=\"no-referrer\"><br><br><img style=\"\" src=\"https://tvax4.sinaimg.cn/large/00759jQJly1hsg8uvtqz7j31401hck91.jpg\" referrerpolicy=\"no-referrer\"><br><br><img style=\"\" src=\"https://tvax2.sinaimg.cn/large/00759jQJly1hsg8ux1psqj31401z44h1.jpg\" referrerpolicy=\"no-referrer\"><br><br><img style=\"\" src=\"https://tvax3.sinaimg.cn/large/00759jQJly1hsg8uxycmvj31401hc16o.jpg\" referrerpolicy=\"no-referrer\"><br><br><img style=\"\" src=\"https://tvax2.sinaimg.cn/large/00759jQJly1hsg8uyn21kj31401hc4gl.jpg\" referrerpolicy=\"no-referrer\"><br><br><img style=\"\" src=\"https://tvax3.sinaimg.cn/large/00759jQJly1hsg8uzeqdzj31401hck84.jpg\" referrerpolicy=\"no-referrer\"><br><br><img style=\"\" src=\"https://tvax4.sinaimg.cn/large/00759jQJly1hsg8uzyy3yj3140140gz4.jpg\" referrerpolicy=\"no-referrer\"><br><br><img style=\"\" src=\"https://tvax4.sinaimg.cn/large/00759jQJly1hsg8uv9nptj31401hctma.jpg\" referrerpolicy=\"no-referrer\"><br><br><img style=\"\" src=\"https://tvax3.sinaimg.cn/large/00759jQJly1hsg8v09z04j31401404by.jpg\" referrerpolicy=\"no-referrer\"><br><br>";
        List<String> strings = HtmlUtils.extractImageUrls(str);
        strings.forEach(System.out::println);
    }

    /**
     * 测试获取文件名
     */
    @Test
    void getFileName() {
        String imgSrc = "https://h5.sinaimg.cn/m/emoticon/icon/others/ct_kele-4ce616ef95.png";
        String videoSrc = "https://f.video.weibocdn.com/o0/H5AopYlvlx08hlyjma2A010412003vHq0E010.mp4?label=mp4_ld&amp;template=360x480.24.0&amp;ori=0&amp;ps=1CwnkDw1GXwCQx&amp;Expires=1724122135&amp;ssig=QYOiNA1GMH&amp;KID=unistore,video";
        System.out.println(HtmlUtils.getFileName(imgSrc));
        System.out.println(HtmlUtils.getFileName(videoSrc));
    }


    /**
     * 获取微博图片流
     */
    @Test
    void getWeiBoImagesHttpRequest() {

    }

    /**
     * 获取微博视频流
     */
    @Test
    void getWeiBoVideosHttpRequest() {
        String url = "https://f.video.weibocdn.com/o0/KjwJ3CYwlx08hlyj0a8001041200ah0h0E010.mp4" +
                "?label=mp4_720p&amp;template=720x960.24.0&amp;ori=0&amp;ps=1CwnkDw1GXwCQx&amp;Expires=1724122135&amp;ssig=Gd1gjIz05D&amp;KID=unistore,video";
        HttpRequest request = HttpRequest.get(url)
                .header(Header.REFERER, "https://weibo.com/")
                .header(Header.USER_AGENT, USER_AGENT)
                .timeout(20000);
        HttpResponse httpResponse = request.executeAsync();
        byte[] bytes = httpResponse.bodyBytes();
        FileUtil.writeBytes(bytes, new File(IMAGES_PATH + "KjwJ3CYwlx08hlyj0a8001041200ah0h0E010.mp4"));
    }
}
