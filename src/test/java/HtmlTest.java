import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Html测试
 *
 * @author yanqs
 * @since 2024-08-10
 */
public class HtmlTest {
    public static void main(String[] args) throws IOException{
        String str="为什么温泉♨️水那么黄？ <img style=\"\" src=\"https://tvax1.sinaimg.cn/large/00759jQJly1hsg8uwgmavj31401hcdy4.jpg\" referrerpolicy=\"no-referrer\"><br><br><img style=\"\" src=\"https://tvax4.sinaimg.cn/large/00759jQJly1hsg8uvtqz7j31401hck91.jpg\" referrerpolicy=\"no-referrer\"><br><br><img style=\"\" src=\"https://tvax2.sinaimg.cn/large/00759jQJly1hsg8ux1psqj31401z44h1.jpg\" referrerpolicy=\"no-referrer\"><br><br><img style=\"\" src=\"https://tvax3.sinaimg.cn/large/00759jQJly1hsg8uxycmvj31401hc16o.jpg\" referrerpolicy=\"no-referrer\"><br><br><img style=\"\" src=\"https://tvax2.sinaimg.cn/large/00759jQJly1hsg8uyn21kj31401hc4gl.jpg\" referrerpolicy=\"no-referrer\"><br><br><img style=\"\" src=\"https://tvax3.sinaimg.cn/large/00759jQJly1hsg8uzeqdzj31401hck84.jpg\" referrerpolicy=\"no-referrer\"><br><br><img style=\"\" src=\"https://tvax4.sinaimg.cn/large/00759jQJly1hsg8uzyy3yj3140140gz4.jpg\" referrerpolicy=\"no-referrer\"><br><br><img style=\"\" src=\"https://tvax4.sinaimg.cn/large/00759jQJly1hsg8uv9nptj31401hctma.jpg\" referrerpolicy=\"no-referrer\"><br><br><img style=\"\" src=\"https://tvax3.sinaimg.cn/large/00759jQJly1hsg8v09z04j31401404by.jpg\" referrerpolicy=\"no-referrer\"><br><br>";
        List<String> strings = extractImageUrls(str);
        strings.forEach(obj->{
            System.out.println(obj);
        });

    }


    public static String readHtmlFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            content.append(line);
        }
        reader.close();
        return content.toString();
    }

    public static List<String> extractImageUrls(String htmlContent) {
        List<String> imageUrls = new ArrayList<>();
        String regex = "<img\\s+[^>]*?src\\s*=\\s*['\"]([^'\"]*?)['\"][^>]*?>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(htmlContent);
        while (matcher.find()) {
            String imageUrl = matcher.group(1);
            imageUrls.add(imageUrl);
        }
        return imageUrls;
    }
}



