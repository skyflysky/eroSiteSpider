package sky.tool.spider.processor;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

@Component
public class VideoUrlProcessor implements PageProcessor
{
	Logger logger = Logger.getLogger(VideoUrlProcessor.class);
	
	private Site site = Site.me().setRetryTimes(3).setSleepTime(1500).setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

	@Override
	public void process(Page page)
	{
		logger.info("正在下载" + page.getUrl().toString());
		page.putField("type", page.getHtml().css("div.pull-left:nth-child(2) > div:nth-child(2) > p:nth-child(1)").toString());
		page.putField("uploadDate", page.getHtml().css("div.pull-left:nth-child(2) > div:nth-child(3) > p:nth-child(1)").toString());
		page.putField("magnate", page.getHtml().css("#lin1k1").toString());
		page.putField("titlePic", page.getHtml().css(".lazy").toString());
		page.putField("title", page.getHtml().css("h2.c_pink").toString());
		page.putField("url", page.getUrl().toString());
		logger.info("下载完成" + page.getUrl().toString());
	}

	@Override
	public Site getSite()
	{
		return this.site;
	}
	
}
