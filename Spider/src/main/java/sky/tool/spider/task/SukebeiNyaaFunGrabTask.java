package sky.tool.spider.task;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import sky.tool.spider.pipeline.SukebeiPipeline;
import sky.tool.spider.processor.SukebeiProcessor;
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

	@Override
	void doWork()
	{
		logger.info("sukebei start");
		String base = "https://sukebei.nyaa.fun/";
		List<String> urlList = new ArrayList<>();
		for(int i = maxPage ; i >= 1 ; i --)
		{
			StringBuilder sb = new StringBuilder(base);
			sb.append("page/");
			sb.append(i);
			sb.append("/");
			urlList.add(sb.toString());
		}
		urlList.add(base);
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
