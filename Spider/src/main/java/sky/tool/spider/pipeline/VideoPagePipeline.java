package sky.tool.spider.pipeline;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import sky.tool.spider.entity.VideoPage;
import sky.tool.spider.service.VideoService;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

@SuppressWarnings("restriction")
@Component
public class VideoPagePipeline implements Pipeline
{
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	VideoService videoService;
	
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
					//直接将获取到的数据插入数据库
					vp = videoService.insertVideoPage(url);
					if(vp != null && vp.getId() != null)
					{
						logger.info("页面" + vp.getUrl() +"抓取成功");
					}
				} 
				//webId  唯一约束
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
}
