package sky.tool.spider.service;

import java.util.List;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import sky.tool.spider.entity.VideoPage;

@SuppressWarnings("restriction")
public interface VideoPageService
{
	VideoPage insertVideoPage(String url) throws MySQLIntegrityConstraintViolationException , NumberFormatException;
	
	List<VideoPage> findAblePage();
	
	boolean setOpenAble(boolean openable , VideoPage videoPage);
	
	VideoPage getVideoPageByWebId(Integer webId);
}
