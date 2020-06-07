package sky.tool.spider.processor;

import java.util.UUID;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import sky.tool.spider.config.SkyDownloader;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

@Component
public class VideoUrlProcessor implements PageProcessor
{
	Logger logger = Logger.getLogger(VideoUrlProcessor.class);
	
	@Override
	public void process(Page page)
	{
		logger.info("正在下载" + page.getUrl().toString());
		
		Document d = Jsoup.parse(page.getHtml().css("#main-container").get());
		
		page.putField("type", d.getElementsByClass("row nav-row").first().getElementsByTag("a").last().html());
		page.putField("magnate", d.getElementsByClass("form-control input-sm fdm_down").first().attr("value"));
		page.putField("titlePic", "https://i0.hdslb.com/bfs/manga-static/manga-pc/img/c9219d816eacd.png");
		page.putField("url", page.getUrl().toString());
		Element elem = d.getElementById("shipin-detail-content-pull");
		if(elem != null)
		{
			page.putField("title", elem.getElementsByTag("h2").first().html());
			page.putField("uploadDate", elem.getElementsByTag("p").get(1).html());
		}
		else
		{
			page.putField("title", UUID.randomUUID().toString());
			page.putField("uploadDate", null);
		}
		
		logger.info("下载完成" + page.getUrl().toString());
	}

	@Override
	public Site getSite()
	{
		return SkyDownloader.site;
	}
	
}
