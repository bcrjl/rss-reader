package com.bcrjl.rss.job;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.setting.Setting;
import com.bcrjl.rss.cache.ConfigCache;
import com.bcrjl.rss.config.DynamicScheduleConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.bcrjl.rss.common.constant.AppConstant.*;

/**
 * 监控配置文件
 *
 * @author yanqs
 * @since 2024-08-06
 */
@Slf4j
@Component
public class FileMonitor {

    @Resource
    private DynamicScheduleConfig scheduledTask;

    @PostConstruct
    public void initConfig() {
        existFile("config.setting", "[system]\n" +
                "## 配置订阅频率\n" +
                "refresh = 5\n" +
                "## 保存微博图片\n" +
                "saveWeiBoImages=false" +
                "## 保存微博视频\n" +
                "saveWeiBoVideo=true\n" +
                "## 上传图片到AList\n" +
                "uploadAList=false\n" +
                "## AList Url\n" +
                "aListUrl=\n" +
                "## AList 账号\n" +
                "aListUser=\n" +
                "## AList 密码\n" +
                "aListPass=\n" +
                "## AList 上传路径\n" +
                "aListUploadPath=" +
                "[mail]\n" +
                "## 启用邮件推送\n" +
                "enable=false\n" +
                "## 启用邮件更新推送\n" +
                "sendUpdate=true\n" +
                "## 邮件服务器的SMTP地址，可选，默认为smtp.<发件人邮箱后缀>\n" +
                "host=smtp.qq.com\n" +
                "## 邮件服务器的SMTP端口，可选，默认25\n" +
                "port=465\n" +
                "## 发件人（必须正确，否则发送失败）\n" +
                "from=rss-reply<no-reply@***.com>\n" +
                "## 用户名，默认为发件人邮箱前缀\n" +
                "user=xxxxxxxxxxxxxx\n" +
                "## 密码（注意，某些邮箱需要为SMTP服务单独设置授权码，详情查看相关帮助）\n" +
                "pass=\n" +
                "## 接收人，多人以英文逗号分割\n" +
                "to=\n");
        Map<String, Object> dataJson = new HashMap<>();
        Map<String, Object> rssList = new HashMap<>();
        rssList.put("weibo",new ArrayList<>());
        rssList.put("other",new ArrayList<>());
        dataJson.put("rssList",rssList);
        existFile("data.json", JSONUtil.toJsonPrettyStr(dataJson));
        existFile("rss-data.json", "[]");

    }

    private void existFile(String fileName, String fileContent) {
        fileName = System.getProperty("user.dir") + "/config/" + fileName;
        File file = FileUtil.file(fileName);
        if (!FileUtil.exist(file)) {
            file = FileUtil.touch(fileName);
            FileWriter dataFile = new FileWriter(file);
            dataFile.write(fileContent, false);
            log.info("监听配置文件不存在，系统已自动创建");
        }
    }

    /**
     * 文件监听
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void configMonitor() {
        Setting setting = new Setting(CONFIG_PATH, CharsetUtil.CHARSET_UTF_8, true);
        Long refresh = Long.valueOf(setting.getByGroup(REFRESH, SET_SYSTEM));
        Long config = (Long) ConfigCache.getConfig(REFRESH);
        if (Objects.isNull(config)) {
            ConfigCache.setConfig(REFRESH, refresh);
            config = refresh;
        }
        if (!config.equals(refresh)) {
            log.info("配置变动，更新频率：{}分钟", refresh);
            scheduledTask.setTimer(refresh * 60 * 1000L);
            ConfigCache.setConfig(REFRESH, refresh);
        }
    }
}

