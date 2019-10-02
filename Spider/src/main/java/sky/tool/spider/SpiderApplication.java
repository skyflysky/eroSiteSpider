package sky.tool.spider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpiderApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(SpiderApplication.class, args);
	}
	
	protected SpringApplicationBuilder configure(SpringApplicationBuilder springApplicationBuilder)
	{
		return springApplicationBuilder.sources(SpiderApplication.class);
	}
}
