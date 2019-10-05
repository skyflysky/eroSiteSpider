package sky.tool.spider.task;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import sky.tool.spider.pipeline.NovelPagePipeline;
import sky.tool.spider.processor.NovelPageProcessor;
import sky.tool.spider.service.NovelService;
import us.codecraft.webmagic.Spider;

@ConditionalOnProperty(prefix="work" , name = "mode" ,havingValue = "grab")
@Component

public class NovelPageTask extends AbstractTask
{
	@Autowired
	NovelService novelService;
	
	private String[] thridPaths=
		{
			"%e9%83%bd%e5%b8%82%e6%bf%80%e6%83%85",
			"%e4%ba%ba%e5%a6%bb%e4%ba%a4%e6%8d%a2",
			"%e6%a0%a1%e5%9b%ad%e6%98%a5%e8%89%b2",
			"%e5%ae%b6%e5%ba%ad%e4%b9%b1%e4%bc%a6",
			"%e6%83%85%e8%89%b2%e7%ac%91%e8%af%9d",
			"%e6%80%a7%e7%88%b1%e6%8a%80%e5%b7%a7",
			"%e6%ad%a6%e4%be%a0%e5%8f%a4%e5%85%b8",
			"%e5%8f%a6%e7%b1%bb%e5%b0%8f%e8%af%b4"
		};

	@Autowired
	NovelPageProcessor novelPageProcessor;
	
	@Autowired
	NovelPagePipeline novelPagePipLine;
	
	@Override
	void doWork()
	{
		logger.info("开始爬小说列表页");
		List<String> urlList = new ArrayList<String>();
		for(String thirdPath : thridPaths)
		{
			for(int i = 55 ; i >= 1 ; i--)
			{
				StringBuilder sb = new StringBuilder("https://");
				sb.append(domain);
				sb.append("/xiaoshuo/list-");
				sb.append(thirdPath);
				sb.append("-");
				sb.append(i);
				sb.append(".html");
				urlList.add(sb.toString());
			}
		}
		String[] urls = new String[urlList.size()];
		urlList.toArray(urls);
		
		Spider.create(novelPageProcessor).addUrl(urls).addPipeline(novelPagePipLine).thread(25).run();
		
		logger.info("小说列表页爬完了");
	}

	@Override
	boolean isWork()
	{
		return "npage".equals(grabMode);
	}
	
}
