import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;

/**
 * RSS获取 测试类
 *
 * @author yanqs
 */
public class RSSReader {
    public static void main(String[] args) throws Exception {
        // RSS feed URL
        // URL rssUrl = new URL("https://rsshub.ys.bcrjl.com/weibo/user/6489032761");
        URL rssUrl = new URL("https://blog.yanqingshan.com/feed/");

        // Create a DocumentBuilder
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        // Parse the RSS file
        Document document = builder.parse(rssUrl.openStream());

        // Get all items
        NodeList items = document.getElementsByTagName("item");

        for (int i = 0; i < items.getLength(); i++) {
            Element item = (Element) items.item(i);
            Element title = (Element) item.getElementsByTagName("title").item(0);
            Element link = (Element) item.getElementsByTagName("link").item(0);

            // Print the title
            System.out.println(title.getTextContent());
            System.out.println(link.getTextContent());
        }
    }
}
