package sky.tool.spider.Test;

import java.io.File;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import sky.tool.spider.dao.PicUrlDao;
import sky.tool.spider.entity.PicUrl;
import sky.tool.spider.utils.SpringUtil;

@Component
@EnableAsync
public class Asy
{
	Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	PicUrlDao puDao;
	
	@Async
	public void check(PicUrl pu)
	{
		if(!StringUtils.isEmpty(pu.getLocalPath()))
		{
			File file = new File(pu.getLocalPath());
			try
			{
				if(file.exists())
				{
					pu.setSideways(SpringUtil.isPictureSideways(file));
					puDao.save(pu);
				}
				else
				{
					logger.info("没有文件:'" + pu.getId() +"'");
				}
			} catch (javax.imageio.IIOException |  java.io.EOFException |java.lang.ArrayIndexOutOfBoundsException e)
			{
				pu.setDawnload(false);
				pu.setLocalPath(null);
				file.delete();
				puDao.save(pu);
				logger.info("删除异常:'" + pu.getId() +"'");
			}
			catch (Exception e) 
			{
				logger.info("删除异常:'" + pu.getId() +"'" , e);
			}
		}
		else
		{
			logger.info("没有路径:'" + pu.getId() + "'");
		}
		
	}
}
