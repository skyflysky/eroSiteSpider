package sky.tool.spider.processor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class InitProcessor implements PageProcessor
{
	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

	public void setSite(Site site)
	{
		this.site = site;
	}

	@Override
	public Site getSite()
	{
		return this.site;
	}

	@Override
	public void process(Page page)
	{
		if (!page.getUrl().regex("http://www.cnblogs.com/[a-z 0-9 -]+/p/[0-9]{7}.html").match())
		{
			page.addTargetRequests(page.getHtml().xpath("//*[@id=\"mainContent\"]/div/div/div[@class=\"postTitle\"]/a/@href").all());
		} else
		{
			page.putField(page.getHtml().xpath("//*[@id=\"cb_post_title_url\"]/text()").toString(),page.getHtml().xpath("//*[@id=\"cb_post_title_url\"]/@href").toString());
		}
	}

}
