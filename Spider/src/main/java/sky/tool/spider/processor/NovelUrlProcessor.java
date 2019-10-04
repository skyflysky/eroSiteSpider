package sky.tool.spider.processor;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

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
		
	}

	@Override
	public Site getSite()
	{
		return null;
	}

}
