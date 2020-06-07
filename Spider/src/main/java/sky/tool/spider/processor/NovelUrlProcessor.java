package sky.tool.spider.processor;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import sky.tool.spider.config.SkyDownloader;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

@Component
public class NovelUrlProcessor implements PageProcessor
{
	Logger logger = Logger.getLogger(getClass());
	
	@Override
	public void process(Page page)
	{
		String url = page.getUrl().toString();
		logger.info("开始处理" + page.getUrl());
		page.putField("url", url);
		String body = page.getHtml().css(".content").toString();
		page.putField("body", body);
		logger.info(page.getUrl() + "处理完成");
	}

	@Override
	public Site getSite()
	{
		return SkyDownloader.site;
	}

}
