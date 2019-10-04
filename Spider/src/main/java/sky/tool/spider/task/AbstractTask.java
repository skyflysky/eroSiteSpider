package sky.tool.spider.task;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractTask implements ApplicationRunner
{
	Logger logger = Logger.getLogger(getClass());
	
	@Value("${pic.storage}")
	String storage;
	
	@Value("${download.mode}")
	String downloadMode;
	
	@Value("${target.domain}")
	String domain;
	
	@Value("${grab.mode}")
	String grabMode;
	
	abstract void doWork();
	
	abstract boolean isWork();
	
	@Override
	public void run(ApplicationArguments args) throws Exception
	{
		if(isWork())
		{
			doWork();
		}
	}
}
