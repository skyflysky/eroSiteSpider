package sky.tool.spider.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import sky.tool.spider.service.NovelService;

@ConditionalOnProperty(prefix="work" , name = "mode" ,havingValue = "grab")
@Component

public class NovelUrlTask extends AbstractTask
{
	@Autowired
	NovelService novelService;

	@Override
	void doWork()
	{
		
	}

	@Override
	boolean isWork()
	{
		return true;
	}
	
}
