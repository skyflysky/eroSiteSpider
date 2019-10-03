package sky.tool.spider.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sky.tool.spider.dao.PicPageDao;
import sky.tool.spider.entity.PicPage;
import sky.tool.spider.service.PicPageService;

@Service
public class PicPageServiceImpl implements PicPageService
{

	@Autowired
	PicPageDao ppDao;
	
	@SuppressWarnings("restriction")
	@Override
	public PicPage insert(PicPage picPage) throws com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException , NumberFormatException
	{
		return ppDao.save(picPage);
	}
	
}
