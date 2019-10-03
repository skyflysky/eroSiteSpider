package sky.tool.spider.service;

import sky.tool.spider.entity.PicPage;

public interface PicPageService
{
	@SuppressWarnings("restriction")
	PicPage insert(PicPage picPage) throws com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
}
