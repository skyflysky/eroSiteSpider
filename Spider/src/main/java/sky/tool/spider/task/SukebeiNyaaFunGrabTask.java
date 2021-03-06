package sky.tool.spider.task;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import sky.tool.spider.pipeline.SukebeiPipeline;
import sky.tool.spider.processor.SukebeiProcessor;
import sky.tool.spider.service.SukebeiNyaaFunService;
import us.codecraft.webmagic.Spider;

@ConditionalOnProperty(prefix="work" , name = "mode" ,havingValue = "grab")
@Component
public class SukebeiNyaaFunGrabTask extends AbstractTask
{
	@Value("${sukebei.page}")
	Integer maxPage;
	
	@Autowired
	SukebeiProcessor processor;
	
	@Autowired
	SukebeiPipeline pipeline;
	
	@Autowired
	SukebeiNyaaFunService service;

	@Override
	void doWork()
	{
		logger.info("sukebei start");
		String base = "https://nyaa.fun/";
		List<String> urlList = new ArrayList<>();
		
		if(maxPage < 1)
		{
			pipeline.setLastTime(service.autoLastGrab());
			logger.info("自动抓取模式");
			urlList.add(base);
			StringBuilder sb = new StringBuilder(base);
			sb.append("page/");
			sb.append(1);
			sb.append("/");
			urlList.add(sb.toString());
		}
		else
		{
			logger.info("手动抓取模式，最大抓取页面:" + maxPage);
			for(int i = maxPage ; i >= 1 ; i --)
			{
				StringBuilder sb = new StringBuilder(base);
				sb.append("page/");
				sb.append(i);
				sb.append("/");
				urlList.add(sb.toString());
			}
			urlList.add(base);
		}
		String[] urls = new String[urlList.size()];
		urlList.toArray(urls);
		Spider.create(processor).addUrl(urls).addPipeline(pipeline).thread(spiderThredCount).setDownloader(downloader).run();
		logger.info("sukebei爬完了");
	}

	@Override
	boolean isWork()
	{
		return "sukebei".equals(grabMode);
	}

}
