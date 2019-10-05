package sky.tool.spider.task;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import sky.tool.spider.pipeline.VideoPagePipeline;
import sky.tool.spider.processor.VideoPageProcessor;
import us.codecraft.webmagic.Spider;
@ConditionalOnProperty(prefix="work" , name = "mode" ,havingValue = "grab")
@Component
@EnableScheduling
public class VideoPageTask extends AbstractTask
{
	@Autowired
	VideoPageProcessor vpProcessor;
	
	@Autowired
	VideoPagePipeline vpPipeline;
	
	private String[] thirdPaths = 
		{
			"%e4%ba%9a%e6%b4%b2%e7%94%b5%e5%bd%b1",
			"%e6%ac%a7%e7%be%8e%e7%94%b5%e5%bd%b1",
			"%e5%88%b6%e6%9c%8d%e4%b8%9d%e8%a2%9c",
			"%e5%bc%ba%e5%a5%b8%e4%b9%b1%e4%bc%a6",
			"%e5%8f%98%e6%80%81%e5%8f%a6%e7%b1%bb",
			"%e6%88%90%e4%ba%ba%e5%8a%a8%e6%bc%ab"
		};
	
	boolean isWork()
	{
		return "vpage".equals(grabMode);
	}
	
	public void doWork()
	{
		logger.info("开始爬视频列表页");
		List<String> urlList = new ArrayList<String>();
		urlList.add("https://www." + domain +"/index/home.html");
		for (String thirdPath : thirdPaths)
		{
			for (int i = 1; i <= 2; i++)
			{
				StringBuilder sb = new StringBuilder("https://");
				sb.append(domain);
				sb.append("/xiazai/list-");
				sb.append(thirdPath);
				sb.append("-");
				sb.append(i);
				sb.append(".html");
				urlList.add(sb.toString());
			}
		}
		String[] urls = new String[urlList.size()];
		urlList.toArray(urls);
		Spider.create(vpProcessor).addUrl(urls).addPipeline(vpPipeline).thread(10).run();
		logger.info("视频列表页爬完了");
	}
}
