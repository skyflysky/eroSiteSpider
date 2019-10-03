package sky.tool.spider.service;

import java.util.List;

import sky.tool.spider.entity.PicPage;

public interface PicPageService
{
	@SuppressWarnings("restriction")
	PicPage insert(PicPage picPage) throws com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

	List<PicPage> findAblePage();
	
	boolean setOpenAble(boolean openAble , PicPage picPage);
	
	int setPicCount(int picCount , PicPage picPage);
	
	PicPage getPicPageByWebId(Integer webId);
}
