package com.bcrjl.rss.common.util;

import cn.hutool.core.lang.Console;
import cn.hutool.extra.mail.MailAccount;
import org.junit.jupiter.api.Test;

/**
 * 邮件测试
 *
 * @author yanqs
 */
class MailUtilsTest {

    /**
     * 初始化邮件账号
     */
    @Test
    void initMailAccount() {
        MailAccount mailAccount = MailUtils.initMailAccount();
        Console.log(mailAccount);
    }
}
