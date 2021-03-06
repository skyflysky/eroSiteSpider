package sky.tool.spider.processor;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import sky.tool.spider.config.SkyDownloader;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;


@Component
public class NovelPageProcessor implements PageProcessor
{
	Logger logger = Logger.getLogger(getClass());
	
	@Override
	public void process(Page page)
	{
		logger.info("开始处理页面:'" + page.getUrl() + "'");
		List<String>tragetList = page.getHtml().css(".list > ul:nth-child(1) > li > a:nth-child(1)").all();
		page.putField("links", tragetList);
		String type = page.getHtml().css(".cat_pos_l > a:nth-child(3)").toString();
		page.putField("type", type);
		page.putField("url", page.getUrl());
		logger.info("页面:'" + page.getUrl() +"'处理完成");
	}

	@Override
	public Site getSite()
	{
		return SkyDownloader.site;
	}

}
