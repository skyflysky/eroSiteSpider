package sky.tool.spider.processor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import sky.tool.spider.config.SkyDownloader;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

@Component
public class SukebeiPageProcessor implements PageProcessor
{
	Logger logger = Logger.getLogger(getClass());
	
	@Override
	public void process(Page page)
	{
		logger.info("解析" + page.getUrl().get() + "开始");
		if(new Integer(200).equals(page.getStatusCode()))
		{
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
		else
		{
			logger.info("返回" + page.getStatusCode() +":\t" + page.getUrl().get());
			page.putField("hasinfo", false);
			page.putField("webId", Integer.valueOf(page.getUrl().get().substring(page.getUrl().get().lastIndexOf("/") + 1)));
			page.putField("code", page.getStatusCode());
		}
	}

	@Override
	public Site getSite()
	{
		Set<Integer> iSet = new HashSet<>();
		
		iSet.add(200);
		iSet.add(500);
		iSet.add(502);
		
		Site site = SkyDownloader.sukeibeiSite.setAcceptStatCode(iSet);
		return site;
	}

}
