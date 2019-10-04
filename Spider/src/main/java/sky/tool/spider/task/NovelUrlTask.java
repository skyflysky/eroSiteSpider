package sky.tool.spider.task;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@ConditionalOnProperty(prefix="work" , name = "mode" ,havingValue = "kk")
@Component

public class NovelUrlTask extends AbstractTask
{

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
