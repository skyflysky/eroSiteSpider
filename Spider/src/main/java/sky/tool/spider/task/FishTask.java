package sky.tool.spider.task;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import sky.tool.spider.pipeline.FishPipeline;
import sky.tool.spider.processor.FishProcessor;
import us.codecraft.webmagic.Spider;

@ConditionalOnProperty(prefix="work" , name = "mode" ,havingValue = "grab")
@Component
public class FishTask extends AbstractTask
{
	@Autowired
	FishProcessor processor;
	
	@Autowired
	FishPipeline pipeline;
	
	private static final String end="0123456789abcdefghijklmnopqrstuvwxyz";
	private static final int[] changeArray = {2 , 1 , 0};

	@Override
	void doWork()
	{
		logger.info("fish spider start");
		
		List<String> urlList = new ArrayList<String>(3);
		for(int i = 0 ; i < 36020 ; i ++)
		{
			StringBuilder sb = new StringBuilder("https://avfish.xyz/movie/");
			sb.append(converter(i));
			urlList.add(sb.toString());
		}
		//urlList.add("https://avfish.xyz/page/1");
		String[] urls = new String[urlList.size()];
		urlList.toArray(urls);
		Spider.create(processor).addUrl(urls).addPipeline(pipeline).thread(spiderThredCount).setDownloader(downloader).run();
		
		logger.info("fish spider finished");
	}

	@Override
	boolean isWork()
	{
		return "fish".equals(grabMode);
	}
	
	private String converter(int b)
	{
		char[] dictionary = end.toCharArray();
		char[] resultArray = new char[changeArray.length];
		for(int i = 0 ;  i < 3 ;  i++)
		{
			resultArray[changeArray[i]] = dictionary[(int) ((b / Math.pow(dictionary.length, i)) % dictionary.length)];
		}
		return new String(resultArray);
	}

}
