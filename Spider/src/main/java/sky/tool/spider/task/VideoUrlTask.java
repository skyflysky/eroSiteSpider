package sky.tool.spider.task;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import sky.tool.spider.entity.VideoPage;
import sky.tool.spider.pipeline.VideoUrlPipeline;
import sky.tool.spider.processor.VideoUrlProcessor;
import sky.tool.spider.service.VideoService;
import us.codecraft.webmagic.Spider;
@ConditionalOnProperty(prefix="work" , name = "mode" ,havingValue = "grab")
@Component
public class VideoUrlTask extends AbstractTask
{
	@Autowired
	VideoService videoSerivce;
	
	@Autowired
	VideoUrlProcessor vuProcessor;
	
	@Autowired
	VideoUrlPipeline vuPipeline;
	
	public void doWork()
	{
		logger.info("开始爬具体的网页页面");
		List<VideoPage> vpList = videoSerivce.findAblePage();
		List<String> urlList = new ArrayList<String>();
		for (VideoPage vp : vpList)
		{
			StringBuilder sb = new StringBuilder("https://");
			sb.append(domain);
			if (vp.getWebId() > 0)
			{
				sb.append("/xiazai/");
				sb.append(vp.getWebId());
			}
			else
			{
				sb.append("/shipin/play-");
				sb.append(Math.abs(vp.getWebId()));
			}
			sb.append(".html");
			urlList.add(sb.toString());
			
		}
		String[] urls = new String[urlList.size()];
		urlList.toArray(urls);
		Spider.create(vuProcessor).addUrl(urls).addPipeline(vuPipeline).thread(spiderThredCount).setDownloader(downloader).run();
		logger.info("具体的网页页面爬完了");
	}

	boolean isWork()
	{
		return "vurl".equals(grabMode);
	}
}
