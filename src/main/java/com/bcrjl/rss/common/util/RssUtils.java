package com.bcrjl.rss.common.util;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.bcrjl.rss.model.entity.RssEntity;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * RSS 订阅工具类
 *
 * @author yanqs
 */
@Slf4j
public class RssUtils {

    public static List<RssEntity> getRssList(String url) {
        List<RssEntity> rssList = new ArrayList<>();
        log.info("开始订阅：{}", url);
        try {
            //URL rssUrl = new URL(url);
            HttpResponse httpResponse = HttpRequest.get(url).executeAsync();
            if(httpResponse.isOk()){
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.parse(httpResponse.bodyStream());
                NodeList items = document.getElementsByTagName("item");
                Node webTitle = document.getElementsByTagName("title").item(0);
                for (int i = 0; i < items.getLength(); i++) {
                    Element item = (Element) items.item(i);
                    Element title = (Element) item.getElementsByTagName("title").item(0);
                    Element link = (Element) item.getElementsByTagName("link").item(0);
                    Element description = (Element) item.getElementsByTagName("description").item(0);
                    Element pubDate = (Element) item.getElementsByTagName("pubDate").item(0);
                    rssList.add(RssEntity.builder()
                            .webTitle(webTitle.getTextContent())
                            .url(url)
                            .title(title.getTextContent())
                            .link(link.getTextContent())
                            .description(description.getTextContent())
                            .createTime(DateUtil.parse(pubDate.getTextContent(), DatePattern.HTTP_DATETIME_FORMAT))
                            .build());
                }
            }


        } catch (Exception e) {
            log.error("地址：{}获取RSS订阅内容异常：", url, e);
        }
        return rssList;
    }
}
