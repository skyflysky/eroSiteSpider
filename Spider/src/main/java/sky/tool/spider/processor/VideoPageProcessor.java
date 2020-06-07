package sky.tool.spider.processor;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import sky.tool.spider.config.SkyDownloader;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

@Component
public class VideoPageProcessor implements PageProcessor
{
	private Logger logger = Logger.getLogger(getClass());

	@Override
	public Site getSite()
	{
		return SkyDownloader.site;
	}

	@Override
	public void process(Page page)
	{
		logger.info("开始处理页面:'" + page.getUrl() + "'");
		if(!page.getUrl().toString().contains("index/home"))
		{
			String input = page.getHtml().css(".video-box").get();
			if(StringUtils.isEmpty(input))
			{
				input = page.getHtml().css(".movie_list").get();
			}
			Document d = Jsoup.parse(input);
			List<String> urls = new ArrayList<String>();
			for(Element ul : d.getElementsByTag("ul"))
			{
				for(Element li : ul.getElementsByTag("li"))
				{
					urls.add(li.getElementsByTag("a").first().attr("href"));
				}
			}
			page.putField("urls", urls);
		}
		else
		{
			List<String> urls = new ArrayList<String>();
			Document d = Jsoup.parse(page.getHtml().css("#main-container").get());
			for(Element ul : d.getElementsByTag("ul"))
			{
				for(Element li : ul.getElementsByTag("li"))
				{
					urls.add(li.getElementsByTag("a").first().attr("href"));
				}
			}
			page.putField("urls", urls);
		}
		logger.info("页面:'" + page.getUrl() + "'处理完成");
	}
	
}
