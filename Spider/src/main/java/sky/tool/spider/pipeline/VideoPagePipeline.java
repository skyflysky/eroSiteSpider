package sky.tool.spider.pipeline;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import sky.tool.spider.entity.VideoPage;
import sky.tool.spider.service.VideoPageService;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

@SuppressWarnings("restriction")
@Component
public class VideoPagePipeline implements Pipeline
{
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	VideoPageService videoPageService;
	
	@Override
	public void process(ResultItems resultItems, Task task)
	{
		logger.info("开始处理数据");
		Map<String, Object> resultMap = resultItems.getAll();
		for(String key : resultMap.keySet())
		{
			@SuppressWarnings("unchecked")
			List<String> result = (List<String>) resultMap.get(key);
			for(String url : result)
			{
				VideoPage vp = null;
				try
				{
					vp = videoPageService.insertVideoPage(url);
					if(vp != null && vp.getId() != null)
					{
						logger.info("页面" + vp.getUrl() +"抓取成功");
					}
					else
					{
						logger.error("页面" + url + "抓取失败");
					}
				} 
				catch (MySQLIntegrityConstraintViolationException | org.hibernate.exception.ConstraintViolationException |org.springframework.dao.DataIntegrityViolationException  e)
				{
					logger.info("网址:'" + url + "'已经被记录过了");
					continue;
				} 
				catch (NumberFormatException e)
				{
					logger.error("网址:'" + url + "'不是标准webId格式的网页");
					continue;
				}
				
			}
		}
		logger.info("数据处理结束");
	}
	
	public VideoPage gdsg(String url)
	{
		logger.info(url);
		try
		{
			return videoPageService.insertVideoPage(url);
		} catch (MySQLIntegrityConstraintViolationException e)
		{
			e.printStackTrace();
		} catch (NumberFormatException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	//@PostConstruct
	public void asd()
	{
		gdsg("https://db532.com/xiazai/63853.html");
	}
}
