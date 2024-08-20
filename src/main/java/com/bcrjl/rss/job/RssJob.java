package com.bcrjl.rss.job;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.setting.Setting;
import com.bcrjl.rss.common.util.AListUtils;
import com.bcrjl.rss.common.util.HtmlUtils;
import com.bcrjl.rss.common.util.MailUtils;
import com.bcrjl.rss.common.util.RssUtils;
import com.bcrjl.rss.model.entity.RssEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.bcrjl.rss.common.constant.AppConstant.*;

/**
 * RSS 任务
 *
 * @author yanqs
 */
@Slf4j
@Component
public class RssJob {

    /**
     * RSS 订阅
     */
    public void subscribe() {
        if (!FileUtil.exist(RSS_DATA_PATH)) {
            FileUtil.touch(RSS_DATA_PATH);
            FileWriter dataFile = new FileWriter(RSS_DATA_PATH);
            dataFile.write("[]");
        }
        JSONObject rssListObj = JSONUtil.parseObj(ResourceUtil.readUtf8Str(RSS_CONFIG_PATH)).getJSONObject("rssList");
        if (Objects.nonNull(rssListObj)) {
            List<RssEntity> dbList = JSONUtil.toList(ResourceUtil.readUtf8Str(RSS_DATA_PATH), RssEntity.class);
            List<String> weiboRssList = JSONUtil.toList(rssListObj.getStr("weibo"), String.class);
            List<RssEntity> saveList = new ArrayList<>();
            weiboRssList.forEach(obj -> {
                List<RssEntity> rssList = RssUtils.getRssList(obj);
                saveList.addAll(rssList);
            });
            List<String> otherRssList = JSONUtil.toList(rssListObj.getStr("other"), String.class);
            otherRssList.forEach(obj -> {
                List<RssEntity> rssList = RssUtils.getRssList(obj);
                saveList.addAll(rssList);
            });
            if (CollUtil.isEmpty(dbList)) {
                FileWriter dataFile = new FileWriter(RSS_DATA_PATH);
                dataFile.write(JSONUtil.toJsonPrettyStr(saveList));
                sendEmailReply(saveList);
            } else {
                List<String> dbIds = dbList.stream().map(RssEntity::getLink).collect(Collectors.toList());
                // 获取库中不包含的数据 则为新增数据
                List<RssEntity> insertList = saveList.stream()
                        .filter(t -> !dbIds.contains(t.getLink())).collect(Collectors.toList());
                dbList.addAll(insertList);
                FileWriter dataFile = new FileWriter(RSS_DATA_PATH);
                dataFile.write(JSONUtil.toJsonPrettyStr(dbList));
                sendEmailReply(insertList);
                log.info("本次新增了{}条推送内容", insertList.size());
            }
        } else {
            log.info("未配置订阅信息");
        }
        log.info("订阅数据结束！");
    }

    /**
     * 发送邮件通知
     *
     * @param list rssList
     */
    private void sendEmailReply(List<RssEntity> list) {
        if (CollUtil.isNotEmpty(list)) {
            Setting setting = new Setting(CONFIG_PATH, CharsetUtil.CHARSET_UTF_8, true);
            Setting emailSetting = setting.getSetting(SET_MAIL);
            saveWeiBoImagesOrUpdateAList(list);
            if (emailSetting.getBool(MAIL_CONFIG_ENABLE) && emailSetting.getBool("sendUpdate")) {
                // 如果邮箱开启且发送更新邮件开启 则推送通知
                StringBuffer stringBuffer = new StringBuffer();
                list.forEach(obj -> {
                    stringBuffer.append("<div>");
                    stringBuffer.append("<a href='").append(obj.getLink()).append("'>").append(obj.getWebTitle()).append("--").append(obj.getTitle()).append("</a></br>");
                    stringBuffer.append("</div>");
                });
                MailAccount mailAccount = MailUtils.initMailAccount();
                List<String> toList = Arrays.asList(emailSetting.getStr("to").split(","));
                if (CollUtil.isNotEmpty(toList)) {
                    MailUtil.send(mailAccount, toList, "RSS订阅推送", stringBuffer.toString(), true);
                }
            }
        }
    }

    /**
     * 保存微博图片到本地且上传AList
     */
    private void saveWeiBoImagesOrUpdateAList(List<RssEntity> list) {
        Setting setting = new Setting(CONFIG_PATH, CharsetUtil.CHARSET_UTF_8, true);
        Setting systemSetting = setting.getSetting(SET_SYSTEM);
        boolean saveImages = Boolean.parseBoolean(systemSetting.get(SAVE_WEIBO_IMAGES));
        boolean saveVideo = Boolean.valueOf(systemSetting.get(SAVE_WEIBO_VIDEO));
        boolean uploadAList = Boolean.valueOf(systemSetting.get(UPLOAD_ALIST));
        if (saveImages) {
            // 保存图片
            list.forEach(obj -> {
                List<String> imgList = HtmlUtils.extractImageUrls(obj.getDescription());
                imgList.forEach(imgObj -> {
                    if (imgObj.contains("sinaimg") && !imgObj.contains("timeline_card") && !imgObj.contains("qixi2018")) {
                        String fileName = HtmlUtils.getFileName(imgObj);
                        HttpResponse weiBoImagesHttpRequest = HtmlUtils.getWeiBoImagesHttpRequest(fileName);
                        byte[] bytes = weiBoImagesHttpRequest.bodyBytes();
                        FileUtil.writeBytes(bytes, new File(IMAGES_PATH + fileName));
                        if (uploadAList) {
                            AListUtils.uploadFile(bytes, fileName);
                        }
                    }
                });
            });
        }
        if(saveVideo){
            // 保存视频
            list.forEach(obj -> {
                List<String> videoList = HtmlUtils.extractVideoUrls(obj.getDescription());
                if(CollUtil.isNotEmpty(videoList)){
                    videoList.forEach(videoObj -> {
                        String fileName = HtmlUtils.getFileName(videoObj);
                        HttpResponse weiBoImagesHttpRequest = HtmlUtils.getWeiBoVideoHttpRequest(videoObj);
                        byte[] bytes = weiBoImagesHttpRequest.bodyBytes();
                        FileUtil.writeBytes(bytes, new File(VIDEO_PATH + fileName));
                        if (uploadAList) {
                           AListUtils.uploadFile(bytes, fileName);
                        }
                    });
                }
            });
        }
    }

}
