package sky.tool.spider.intercepter;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class LogIntercepterConfiguration extends WebMvcConfigurationSupport
{
	@Override
	public void addInterceptors(InterceptorRegistry registry)
	{
		registry.addInterceptor(new LogIntercepter()).addPathPatterns("/**");
		super.addInterceptors(registry);
	}
}
