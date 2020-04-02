package sky.tool.spider.pipeline;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sky.tool.spider.entity.Sukebei;
import sky.tool.spider.service.SukebeiNyaaFunService;
import sky.tool.spider.utils.SpringUtil;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

@Component
public class SukebeiPipeline implements Pipeline
{
	private Logger logger = Logger.getLogger(SukebeiPipeline.class);
	
	@Autowired
	SukebeiNyaaFunService service;
	
	@Override
	public void process(ResultItems resultItems, Task task)
	{
		List<Map<String,String>> list = resultItems.get("list");
		
		for(Map<String, String> map : list)
		{
			if(!service.checkSukebeiExistence(Integer.valueOf(map.get("webId"))))
			{
				Calendar c = Calendar.getInstance();
				c.setTimeInMillis(Long.valueOf(map.get("publish")) * 1000);
				
				String fileName = SpringUtil.getFileName(map.get("magnet"));
				
				Sukebei s = new Sukebei(Integer.valueOf(map.get("webId")), 
						SpringUtil.getSukebeiType(map.get("type")), 
						map.get("title"), 
						map.get("nextPage"), 
						map.get("magnet"), 
						map.get("size"), 
						c, 
						fileName);
				logger.info("新增\t" + map.get("title"));
				try
				{
					service.save(s);
				}
				catch (org.hibernate.exception.ConstraintViolationException |org.springframework.dao.DataIntegrityViolationException e)
				{
					logger.info("已存在\t" + map.get("title"));
				}
			}
			else
			{
				logger.info("已存在\t" + map.get("title"));
			}
		}
		
	}
	
	
}
