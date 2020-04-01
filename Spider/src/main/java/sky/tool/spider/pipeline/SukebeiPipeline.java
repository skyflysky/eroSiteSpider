package sky.tool.spider.pipeline;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import sky.tool.spider.constant.SukebeiType;
import sky.tool.spider.entity.Sukebei;
import sky.tool.spider.service.SukebeiNyaaFunService;
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
			if(!service.checkExistence(Integer.valueOf(map.get("webId"))))
			{
				Calendar c = Calendar.getInstance();
				c.setTimeInMillis(Long.valueOf(map.get("publish")) * 1000);
				
				String magnet = map.get("magnet");
				int indexdn = magnet.indexOf("dn=");
				String fileName = null;
				if(indexdn > -1)
				{
					try
					{
						magnet = magnet.substring(indexdn + 3);
						int indexand = magnet.indexOf("&");
						if(indexand > -1)
						{
							fileName = URLDecoder.decode(magnet.substring(0 ,indexand) , "utf-8");
						}
						else
						{
							fileName = URLDecoder.decode(magnet , "utf-8");
						}
					}
					catch (UnsupportedEncodingException e)
					{
						logger.info("error" , e);
					}
				}
				
				Sukebei s = new Sukebei(Integer.valueOf(map.get("webId")), 
						getSukebeiType(map.get("type")), 
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
	
	public SukebeiType getSukebeiType(String url)
	{
		switch (url)
		{
			case "/search/c_1_1_k_0":
				return SukebeiType.ANIME;
			case "/search/c_1_2_k_0":
				return SukebeiType.DOUJINSHI;
			case "/search/c_1_3_k_0":
				return SukebeiType.GAMES;
			case "/search/c_1_4_k_0":
				return SukebeiType.MANGA;
			case "/search/c_1_5_k_0":
				return SukebeiType.PICTURES;
			case "/search/c_2_6_k_0":
				return SukebeiType.PHOTO;
			case "/search/c_2_7_k_0":
				return SukebeiType.VIDEO;
		}
		return null;
	}
}
