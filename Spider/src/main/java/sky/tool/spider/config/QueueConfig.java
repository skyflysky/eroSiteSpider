package sky.tool.spider.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import sky.tool.spider.tool.PicDownloadTool;

@Configuration
public class QueueConfig
{
	@Value("${de.thredCount}")
	Integer thredCount;
	
	@Value("${de.timeout}")
	Long timeout;
	
	@Value("${de.multtime}")
	Integer multtime;
	
	@Bean(name = "de")
	public PicDownloadTool picDownloadTool()
	{
		return new PicDownloadTool(thredCount , timeout , multtime);
	}
}
