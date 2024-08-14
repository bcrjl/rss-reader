package com.bcrjl.rss.common.constant;

/**
 * 应用常量
 *
 * @author yanqs
 */
public interface AppConstant {

    int INIT_MAP = 16;

    /**
     * 系统配置路径
     */
    String CONFIG_PATH = System.getProperty("user.dir") + "/config/config.setting";
    /**
     * RSS数据路径
     */
    String RSS_DATA_PATH = System.getProperty("user.dir") + "/config/rss-data.json";

    /**
     * RSS订阅源路径
     */
    String RSS_CONFIG_PATH = System.getProperty("user.dir") + "/config/data.json";
    String IMAGES_PATH = System.getProperty("user.dir") + "/images/";

    String SET_SYSTEM = "system";
    String SET_MAIL = "mail";
    /**
     * 配置RSS订阅频率
     */
    String REFRESH = "refresh";

    /**
     * 保存微博图片
     */
    String SAVE_WEIBO_IMAGES = "saveWeiBoImages";

    /**
     * 上传图片到AList
     */
    String UPLOAD_ALIST = "uploadAList";

    /**
     * AList Url
     */
    String ALIST_URL = "aListUrl";
    /**
     * AList 账号
     */
    String ALIST_USER = "aListUser";
    /**
     * AList 密码
     */
    String ALIST_PASS = "aListPass";

    String ALIST_UPLOAD_PATH = "aListUploadPath";

    Integer MAIL_CONFIG_SIZE = 7;

    String MAIL_CONFIG_ENABLE = "enable";

    String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36";

}
