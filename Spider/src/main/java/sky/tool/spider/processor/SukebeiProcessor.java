package sky.tool.spider.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

@Component
public class SukebeiProcessor implements PageProcessor
{
	private Site site = Site.me().setRetryTimes(3).setSleepTime(1500).setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");
	
	private Logger logger = Logger.getLogger(SukebeiProcessor.class);
	
	@Override
	public void process(Page page)
	{
		logger.info("开始处理" + page.getUrl());
		Document document = Jsoup.parse(page.getHtml().css("body > div.container > div > table").get());
		List<Map<String , String>> innerList = new ArrayList<Map<String,String>>();
		for(Element tr : document.getElementsByTag("tbody").get(0).getElementsByTag("tr"))
		{
			Map<String, String> innerMap = new HashMap<String, String>();
			List<Element> tds = tr.getElementsByTag("td");
			
			//<td style="padding:0 4px;"> <a href="/search/c_2_7_k_0"> <img src="https://search.pstatic.net/common?src=https://sukebei.nyaa.fun/static/img/icons/sukebei/2_7.png" alt="Art - Doujinshi"> </a> </td> 
			innerMap.put("type", tds.get(0).getElementsByTag("a").get(0).attr("href"));
			
			//<td> <a href="/view/2979900" title="+++ [HD Uncensored] TMHP-015 嘘つけないから">+++ [HD Uncensored] TMHP-015 嘘つけないから</a> </td> 
			Element lable = tds.get(1).getElementsByTag("a").get(0);
			innerMap.put("nextPage" , lable.attr("href"));
			innerMap.put("webId", lable.attr("href").substring(lable.attr("href").lastIndexOf("/") + 1));
			innerMap.put("title", lable.attr("title"));
			
			//<td class="text-center" style="white-space: nowrap;"> <a href="magnet:?xt=urn:btih:86dace38708da8d60b0bf435136e4d17e2f97770&amp;dn=%2B%2B%2B+%5BHD+Uncensored%5D+TMHP-015+%E5%98%98%E3%81%A4%E3%81%91%E3%81%AA%E3%81%84%E3%81%8B%E3%82%89&amp;tr=http%3A%2F%2Fsukebei.tracker.wf%3A8888%2Fannounce&amp;tr=udp%3A%2F%2Fopen.stealth.si%3A80%2Fannounce&amp;tr=udp%3A%2F%2Ftracker.opentrackr.org%3A1337%2Fannounce&amp;tr=udp%3A%2F%2Ftracker.coppersurfer.tk%3A6969%2Fannounce&amp;tr=udp%3A%2F%2Ftracker.leechers-paradise.org%3A6969%2Fannounce"><i class="fa fa-fw fa-magnet"></i></a> </td> 
			innerMap.put("magnet" , tds.get(2).getElementsByTag("a").get(0).attr("href"));
			
			//<td class="text-center">5.0 GiB</td>
			innerMap.put("size", tds.get(3).html());
			
			//<td class="text-center" data-timestamp="1585634690"></td> 
			innerMap.put("publish", tds.get(4).attr("data-timestamp"));
			innerList.add(innerMap);
		}
		
		page.putField("list", innerList);
		page.putField("url", page.getUrl().toString());
		
	}
	@Override
	public Site getSite()
	{
		return site;
	}

}
