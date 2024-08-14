package com.bcrjl.rss.common.util;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.setting.Setting;
import lombok.extern.slf4j.Slf4j;

import static com.bcrjl.rss.common.constant.AppConstant.*;

/**
 * 邮件工具
 *
 * @author yanqs
 * @since 2024-08-07
 */
@Slf4j
public class MailUtils {
    public static MailAccount initMailAccount() {
        Setting setting = new Setting(CONFIG_PATH, CharsetUtil.CHARSET_UTF_8, true);
        Setting emailSetting = setting.getSetting(SET_MAIL);
        if (emailSetting.size() >= MAIL_CONFIG_SIZE) {
            MailAccount mailAccount = new MailAccount();
            mailAccount.setHost(emailSetting.getWithLog("host"));
            mailAccount.setPort(Integer.valueOf(emailSetting.getWithLog("port")));
            mailAccount.setAuth(true);
            mailAccount.setFrom(emailSetting.getWithLog("from"));
            mailAccount.setUser(emailSetting.getWithLog("user"));
            mailAccount.setPass(emailSetting.getWithLog("pass"));
            mailAccount.setSslEnable(true);
            mailAccount.setDebug(false);
            return mailAccount;
        } else {
            log.error("请配置邮箱信息");
            return null;
        }
    }
}
