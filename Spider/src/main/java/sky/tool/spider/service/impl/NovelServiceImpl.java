package sky.tool.spider.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import sky.tool.spider.dao.NovelPageDao;
import sky.tool.spider.entity.NovelPage;
import sky.tool.spider.service.NovelService;

@SuppressWarnings("restriction")
@Service
public class NovelServiceImpl implements NovelService
{
	Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	NovelPageDao npDao;
	
	@Override
	public NovelPage insertNovelPage(NovelPage novelPage) throws MySQLIntegrityConstraintViolationException
	{
		return npDao.save(novelPage);
	}

}
