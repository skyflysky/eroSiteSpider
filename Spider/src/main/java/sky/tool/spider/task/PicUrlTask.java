package sky.tool.spider.task;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import sky.tool.spider.entity.PicPage;
import sky.tool.spider.pipeline.PicUrlPipeLine;
import sky.tool.spider.processor.PicUrlProcessor;
import sky.tool.spider.service.PicPageService;
import us.codecraft.webmagic.Spider;
@ConditionalOnProperty(prefix="work" , name = "mode" ,havingValue = "grab")
@Component
public class PicUrlTask implements ApplicationRunner
{
	private static Logger logger = Logger.getLogger(VideoUrlTask.class);
	
	@Value("${target.domain}")
	private String domain;
	
	@Value("${grab.mode}")
	private String grabMode;
	
	@Autowired
	PicUrlPipeLine puPipeLine;
	
	@Autowired
	PicUrlProcessor puProcessor;
	
	@Autowired
	PicPageService ppService;

	@Override
	public void run(ApplicationArguments args) throws Exception
	{
		if(grabMode.equals("purl"))
		{
			doSpider();
		}
	}
	
	public void doSpider()
	{
		logger.info("开始抓取图片详情");
		List<PicPage> readList = ppService.findAblePage(); 
		List<String> urlList = new ArrayList<String>();
		for(PicPage pp : readList)
		{
			if(pp.getWebId() > 0)
			{
				StringBuilder sb = new StringBuilder("https://");
				sb.append(domain);
				sb.append("/tupian/");
				sb.append(pp.getWebId());
				sb.append(".html");
				urlList.add(sb.toString());
			}
		}
		String[] urls = new String[urlList.size()];
		urlList.toArray(urls);
		Spider.create(puProcessor).addPipeline(puPipeLine).addUrl(urls).thread(10).run();
		logger.info("图片详情抓取完毕");
	}

}
