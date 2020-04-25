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
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

@Component
public class SukebeiPipeline implements Pipeline
{
	private Logger logger = Logger.getLogger(SukebeiPipeline.class);
	
	/**
	 * 数据库中最大的publish时间
	 */
	private long lastTime = Long.MIN_VALUE;
	
	@Autowired
	SukebeiNyaaFunService service;
	
	@Override
	public void process(ResultItems resultItems, Task task)
	{
		List<Map<String,String>> list = resultItems.get("list");
		
		//当前页面最大的publish时间
		long time = Long.MIN_VALUE;
		
		for(Map<String, String> map : list)
		{
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(Long.valueOf(map.get("publish")) * 1000);
			time = Long.max(c.getTimeInMillis() , time);
			
			if(!service.checkSukebeiExistence(Integer.valueOf(map.get("webId"))))
			{
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
		
		Spider spider = (Spider) task;
		if(time < lastTime)
		{ 
			logger.info("检测到停止条件在页面" + resultItems.get("url"));
		}
		else if(lastTime > Long.MIN_VALUE)
		{
			String url = resultItems.get("url");
			url = url.substring(0, url.length() - 1);
			try
			{
				Integer page = Integer.valueOf(url.substring(url.lastIndexOf("/") + 1));
				StringBuilder sb = new StringBuilder(url.substring(0 , url.lastIndexOf("/")));
				sb.append("/");
				sb.append(page + 1);
				sb.append("/");
				spider.getScheduler().push(new Request(sb.toString()), task);
			}
			catch (NumberFormatException e)
			{
				logger.info("字符串截取出错:" + url);
			}
		}
		
	}
	
	public void setLastTime(Long lastTime)
	{
		this.lastTime = lastTime;
		logger.info("上次列表页最新的时间戳:" + lastTime);
	}
}
