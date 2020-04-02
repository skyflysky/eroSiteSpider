package sky.tool.spider.pipeline;

import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;

import sky.tool.spider.entity.SukebeiPage;
import sky.tool.spider.service.SukebeiNyaaFunService;
import sky.tool.spider.utils.SpringUtil;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

@Component
public class SukebeiPagePipeline implements Pipeline
{
	Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	SukebeiNyaaFunService service;

	@SuppressWarnings("unchecked")
	@Override
	public void process(ResultItems resultItems, Task task)
	{
		logger.info("webId:" + resultItems.get("webId"));
		logger.info("hasinfo:" + resultItems.get("hasinfo"));
		if((boolean)resultItems.get("hasinfo"))
		{
			logger.info("webName:" + resultItems.get("webName"));
			logger.info("type:" + resultItems.get("type"));
			logger.info("publish:" + resultItems.get("publish"));
			logger.info("hash:" + resultItems.get("hash"));
			
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis((Long.valueOf(resultItems.get("publish")) * 1000));
			
			List<String> magnets = (List<String>)resultItems.get("magnets");
			JSONArray ja = new JSONArray();
			String fileName = null;
			for(String s : magnets)
			{
				ja.add(s);
				String filename = SpringUtil.getFileName(s);
				if(filename != null)
				{
					fileName = filename;
				}
			}
			
			SukebeiPage sp = new  SukebeiPage(
					(Integer)resultItems.get("webId"), 
					SpringUtil.getSukebeiType(resultItems.get("type")), 
					resultItems.get("webName"), 
					resultItems.get("hash"), 
					ja.toJSONString(), 
					c, 
					fileName);
			if(!service.checkSukebeiPageExistence((Integer)resultItems.get("webId")))
			{
				try
				{
					service.save(sp);
				}
				catch (org.hibernate.exception.ConstraintViolationException |org.springframework.dao.DataIntegrityViolationException e)
				{
					logger.info("已存在\t" + resultItems.get("webName"));
				}
				catch (Exception e)
				{
					logger.error(e);
				}
				logger.info("新增\t" + resultItems.get("webName"));
			}
			else
			{
				logger.info("已存在\t" + resultItems.get("webName"));
			}
		}
	}
}
