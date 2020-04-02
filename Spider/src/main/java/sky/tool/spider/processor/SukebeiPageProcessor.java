package sky.tool.spider.processor;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

@Component
public class SukebeiPageProcessor implements PageProcessor
{
	Logger logger = Logger.getLogger(getClass());
	
	private Site site = Site.me().setRetryTimes(3).setSleepTime(1500).setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");
	
	@Override
	public void process(Page page)
	{
		logger.info("解析" + page.getUrl().get() + "开始");
		Document d = Jsoup.parse(page.getHtml().css("body > div.container").get());
		boolean hasinfo = (d.getElementsByClass("panel panel-default").size() > 0);
		page.putField("hasinfo", hasinfo);
		page.putField("webId", Integer.valueOf(page.getUrl().get().substring(page.getUrl().get().lastIndexOf("/") + 1)));
		if(hasinfo)
		{
			Element panal = d.getElementsByClass("panel panel-default").first();
			page.putField("webName", panal.getElementsByTag("h3").html());
			
			Element panalBody = d.getElementsByClass("panel-body").first();
			Element bodyRow1 = panalBody.getElementsByClass("row").get(0);
			page.putField("type", bodyRow1.getElementsByTag("a").get(1).attr("href"));
			page.putField("publish", bodyRow1.getElementsByClass("col-md-5").last().attr("data-timestamp"));
			page.putField("hash", panalBody.getElementsByTag("kbd").html());
			
			Element panalFoot = d.getElementsByClass("panel-footer clearfix").first();
			List<String> magnets = new ArrayList<String>(panalFoot.getElementsByTag("a").size());
			for(Element a : panalFoot.getElementsByTag("a"))
			{
				magnets.add(a.attr("href"));
			}
			page.putField("magnets", magnets);
		}
	}

	@Override
	public Site getSite()
	{
		return site;
	}

}
