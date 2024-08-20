package com.bcrjl.rss.common.util;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import cn.hutool.setting.Setting;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.bcrjl.rss.common.constant.AppConstant.*;

/**
 * AList工具类
 *
 * @author yanqs
 */
@Slf4j
public class AListUtils {

    /**
     * AList 登录获取 Token 接口路径
     */
    private static final String TOKEN_URL = "/api/auth/login";

    /**
     * 上传流 接口路径
     */
    private static final String UPLOAD_URL = "/api/fs/put";

    /**
     * 创建本地缓存
     */
    private static TimedCache<String, String> timedCache = CacheUtil.newTimedCache(86400000);

    /**
     * 获取AList Token
     *
     * @return token
     */
    private static String getToken() {
        try {
            String alistToken = timedCache.get("alist_token");
            if (StrUtil.isNotEmpty(alistToken)) {
                return alistToken;
            } else {
                Setting setting = new Setting(CONFIG_PATH, CharsetUtil.CHARSET_UTF_8, true);
                Setting systemSetting = setting.getSetting(SET_SYSTEM);
                Map<String, String> params = new HashMap<>(INIT_MAP);
                params.put("username", systemSetting.get(ALIST_USER));
                params.put("password", systemSetting.get(ALIST_PASS));
                String aListUrl = systemSetting.get(ALIST_URL);
                String body = HttpRequest.post(aListUrl + TOKEN_URL)
                        .header(Header.CONTENT_TYPE, ContentType.JSON.getValue())
                        .body(JSONUtil.toJsonStr(params))
                        .execute().body();
                String token = JSONUtil.parseObj(body).getJSONObject("data").getStr("token");
                timedCache.put("alist_token", token);
                return token;
            }
        } catch (Exception e) {
            log.error("获取AList Token异常：", e);
            return "";
        }
    }

    /**
     * 上传文件
     *
     * @param fileByte 文件字节
     * @param fileName 文件名
     */
    public static void uploadFile(byte[] fileByte, String fileName) {
        try {
            String time = DateUtil.format(new Date(), "yyyyMMdd");
            Setting setting = new Setting(CONFIG_PATH, CharsetUtil.CHARSET_UTF_8, true);
            Setting systemSetting = setting.getSetting(SET_SYSTEM);
            String aListUrl = systemSetting.get(ALIST_URL);
            String uploadPath = systemSetting.get(ALIST_UPLOAD_PATH);
            HttpResponse httpResponse = HttpRequest.put(aListUrl + UPLOAD_URL)
                    .header(Header.AUTHORIZATION, getToken())
                    .header(Header.CONTENT_TYPE, ContentType.MULTIPART.getValue())
                    .header("File-Path", uploadPath + "/" + time + "/" + fileName)
                    .body(fileByte)
                    .execute();
            if (httpResponse.isOk()) {
                String message = JSONUtil.parseObj(httpResponse.body()).getStr("message");
                if (!"success".equals(message)) {
                    log.info("AList上传失败：{}", message);
                }
            } else {
                log.info("AList上传失败,响应状态码:{}", httpResponse.getStatus());
            }
        } catch (Exception e) {
            log.error("AList上传文件异常：", e);
        }
    }
}
