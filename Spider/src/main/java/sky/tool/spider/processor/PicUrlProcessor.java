package sky.tool.spider.processor;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

@Component
public class PicUrlProcessor implements PageProcessor
{
	private Site site = Site.me().setRetryTimes(3).setSleepTime(1500).setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");
	
	private Logger logger = Logger.getLogger(getClass());
	@Override
	public void process(Page page)
	{
		logger.info("开始下载:'" + page.getUrl().toString() + "'");
		
		List<String> imgs = page.getHtml().css("img.videopic").all();
		page.putField("imgs", imgs);
		page.putField("url", page.getUrl().toString());
		
		logger.info("下载完成:'" + page.getUrl().toString() + "'");
	}

	@Override
	public Site getSite()
	{
		return this.site;
	}

}
