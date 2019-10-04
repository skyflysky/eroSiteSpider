package sky.tool.spider.task;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import sky.tool.spider.entity.PicPage;
import sky.tool.spider.pipeline.PicUrlPipeLine;
import sky.tool.spider.processor.PicUrlProcessor;
import sky.tool.spider.service.PictureService;
import us.codecraft.webmagic.Spider;
@ConditionalOnProperty(prefix="work" , name = "mode" ,havingValue = "grab")
@Component
public class PicUrlTask extends AbstractTask
{
	@Autowired
	PicUrlPipeLine puPipeLine;
	
	@Autowired
	PicUrlProcessor puProcessor;
	
	@Autowired
	PictureService ppService;
	
	boolean isWork()
	{
		return grabMode.equals("purl");
	}
	
	public void doWork()
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
