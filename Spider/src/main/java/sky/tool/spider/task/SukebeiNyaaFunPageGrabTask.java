package sky.tool.spider.task;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import sky.tool.spider.entity.SukebeiPage;
import sky.tool.spider.pipeline.SukebeiPagePipeline;
import sky.tool.spider.processor.SukebeiPageProcessor;
import sky.tool.spider.service.SukebeiNyaaFunService;
import us.codecraft.webmagic.Spider;


@ConditionalOnProperty(prefix="work" , name = "mode" ,havingValue = "grab")
@Component
public class SukebeiNyaaFunPageGrabTask extends AbstractTask
{
	@Autowired
	SukebeiPageProcessor processor;
	
	@Autowired
	SukebeiPagePipeline pipeline;
	
	@Autowired
	SukebeiNyaaFunService service;
	
	@Value("${sukebeipage.max}")
	Integer max;
	
	@Value("${sukebeipage.min}")
	Integer min;
	
	@Override
	void doWork()
	{
		logger.info("sukebeipage start");
		String base = "https://sukebei.nyaa.fun/view/";
		if(max >= min)
		{
			List<String> urlList = new ArrayList<>();
			logger.info("开始准备url");
			List<Integer> webIds = new LinkedList<>();
			for(int i = min ; i <= max ; i++)
			{
				webIds.add(i);
			}
			List<SukebeiPage> sukebeiPages = service.getSukebeiPageByWebId(webIds);
			for(SukebeiPage sp : sukebeiPages)
			{
				webIds.remove(sp.getWebId());
			}
			for(int i : webIds)
			{
				StringBuilder sb = new StringBuilder(base);
				sb.append(i);
				urlList.add(sb.toString());
			}
			urlList.add(base);
			String[] urls = new String[urlList.size()];
			urlList.toArray(urls);
			Spider.create(processor).addUrl(urls).addPipeline(pipeline).thread(spiderThredCount).setDownloader(downloader).run();
		}
		else
		{
			logger.error("最大值、最小值设置错误");
		}
		logger.info("sukebeipage 爬完了");
	}

	@Override
	boolean isWork()
	{
		return "sukebeipage".equals(grabMode);
	}

}
