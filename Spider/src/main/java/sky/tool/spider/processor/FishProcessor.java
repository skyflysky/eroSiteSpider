package sky.tool.spider.processor;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

@Component
public class FishProcessor implements PageProcessor
{
	Logger logger = Logger.getLogger(FishProcessor.class);
	
	private Site site = Site.me().setRetryTimes(3).setSleepTime(10000).setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

	@Override
	public void process(Page page)
	{
		//https://avfish.xyz/movie/rq9
	}

	@Override
	public Site getSite()
	{
		return site;
	}

}
