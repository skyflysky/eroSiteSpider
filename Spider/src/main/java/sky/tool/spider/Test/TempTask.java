package sky.tool.spider.Test;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import sky.tool.spider.dao.PicUrlDao;
import sky.tool.spider.entity.PicUrl;

//@Component
public class TempTask implements ApplicationRunner
{
	@Autowired
	PicUrlDao puDao;
	
	@Autowired
	Asy asy;
	
	private Logger logger = Logger.getLogger(getClass());

	@Override
	public void run(ApplicationArguments args) throws Exception
	{
		logger.info("开始");
		List<PicUrl> list = puDao.finddDownloadedAndNoSideways();
		for(PicUrl pu : list)
		{
			asy.check(pu);
		}
		logger.info("结束");
	}

}
