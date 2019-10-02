package sky.tool.spider.pipeline;

import org.apache.log4j.Logger;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class InitPipline implements Pipeline
{
	Logger logger = Logger.getLogger(getClass());
	
	@Override
	public void process(ResultItems resultItems, Task task)
	{
		
	}

}
