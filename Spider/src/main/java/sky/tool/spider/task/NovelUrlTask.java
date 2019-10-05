package sky.tool.spider.task;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import sky.tool.spider.entity.NovelPage;
import sky.tool.spider.pipeline.NovelUrlPipeline;
import sky.tool.spider.processor.NovelUrlProcessor;
import sky.tool.spider.service.NovelService;
import us.codecraft.webmagic.Spider;

@ConditionalOnProperty(prefix="work" , name = "mode" ,havingValue = "grab")
@Component

public class NovelUrlTask extends AbstractTask
{
	@Autowired
	NovelService novelService;

	@Autowired
	NovelUrlProcessor novelUrlProcessor;
	
	@Autowired
	NovelUrlPipeline novelUrlPipline;
	
	
	@Override
	void doWork()
	{
		logger.info("开始抓取文章详情页");
		List<NovelPage> unloadPages = novelService.findUnloadPage();
		List<String> urlList = new ArrayList<String>();
		for(NovelPage np : unloadPages)
		{
			StringBuilder sb = new StringBuilder("https://");
			sb.append(domain);
			sb.append(np.getPageUrl());
			urlList.add(sb.toString());
			
		}
		String[] urls = new String[urlList.size()];
		urlList.toArray(urls);
		Spider.create(novelUrlProcessor).addUrl(urls).addPipeline(novelUrlPipline).thread(25).run();
		logger.info("文章详情页抓取完毕");
	}

	@Override
	boolean isWork()
	{
		return "nurl".equals(grabMode);
	}
	
}
