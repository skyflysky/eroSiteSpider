package sky.tool.spider.processor;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import sky.tool.spider.config.SkyDownloader;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

@Component
public class PicPageProcessor implements PageProcessor
{
	private Logger logger = Logger.getLogger(getClass());

	@Override
	public void process(Page page)
	{
		logger.info("开始处理页面:'" + page.getUrl() + "'");
		List<String> infos = page.getHtml().css("#tpl-img-content > li ").all();
		page.putField("infos", infos);
		page.putField("type", page.getHtml().css(".cat_pos_l > a:nth-child(3)").get());
		logger.info("页面:'" + page.getUrl() +"'处理完成");
	}

	@Override
	public Site getSite()
	{
		return SkyDownloader.site;
	}
	
}
