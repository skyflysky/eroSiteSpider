package sky.tool.spider.task;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import sky.tool.spider.pipeline.PicPagePipeline;
import sky.tool.spider.processor.PicPageProcessor;
import us.codecraft.webmagic.Spider;

@Component
public class PicPageTask implements ApplicationRunner
{
	private static Logger logger = Logger.getLogger(VideoPageTask.class);

	@Value("${target.domain}")
	private String domain;

	@Value("${work.mode}")
	private String workMode;

	@Value("${grab.mode}")
	private String grabMode;

	@Autowired
	PicPageProcessor ppProcessor;

	@Autowired
	PicPagePipeline ppPipeline;
	
	private String[] thirdPaths = 
		{
			"%e8%87%aa%e6%8b%8d%e5%81%b7%e6%8b%8d",
			"%e4%ba%9a%e6%b4%b2%e8%89%b2%e5%9b%be",
			"%e6%ac%a7%e7%be%8e%e8%89%b2%e5%9b%be",
			"%e7%be%8e%e8%85%bf%e4%b8%9d%e8%a2%9c",
			"%e6%b8%85%e7%ba%af%e5%94%af%e7%be%8e",
			"%e4%b9%b1%e4%bc%a6%e7%86%9f%e5%a5%b3",
			"%e5%8d%a1%e9%80%9a%e5%8a%a8%e6%bc%ab"
		};
	
	@Override
	public void run(ApplicationArguments args) throws Exception
	{
		if(workMode.equals("grab") && grabMode.equals("ppage"))
		{
			doSpider();
		}
	}

	public void doSpider()
	{
		logger.info("开始爬图片列表页");
		List<String> urlList = new ArrayList<String>();
		for (String thirdPath : thirdPaths)
		{
			for (int i = 1; i <= 50; i++)
			{
				StringBuilder sb = new StringBuilder("https://");
				sb.append(domain);
				sb.append("/tupian/list-");
				sb.append(thirdPath);
				sb.append("-");
				sb.append(i);
				sb.append(".html");
				urlList.add(sb.toString());
			}
		}
		String[] urls = new String[urlList.size()];
		urlList.toArray(urls);
		Spider.create(ppProcessor).addUrl(urls).addPipeline(ppPipeline).thread(15).run();
		logger.info("图片列表页爬完了");
	}

}