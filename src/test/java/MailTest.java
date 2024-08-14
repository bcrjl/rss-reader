import cn.hutool.core.lang.Console;
import cn.hutool.extra.mail.MailAccount;
import com.bcrjl.rss.common.util.MailUtils;

/**
 * @author yanqs
 * @since 2024-08-09
 */
public class MailTest {
    public static void main(String[] args) {
        MailAccount mailAccount = MailUtils.initMailAccount();
        Console.log(mailAccount);
    }
}
