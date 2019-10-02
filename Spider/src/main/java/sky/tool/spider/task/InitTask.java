package sky.tool.spider.task;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class InitTask
{
	@Value("${target.domain}")
	private String domain;
	
	private Logger logger = Logger.getLogger(getClass());
	
	//@PostConstruct
	public void postc()
	{
		logger.info("start");
		//Spider.create(new InitProcessor()).addUrl("http://www.cnblogs.com/justcooooode").addPipeline(new ConsolePipeline()).run();
		logger.info(domain);
		
		
		logger.info("end");
	}
	
	
}
