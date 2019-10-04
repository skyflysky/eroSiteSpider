package sky.tool.spider.service;

import java.util.List;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import sky.tool.spider.entity.VideoPage;
import sky.tool.spider.entity.VideoUrl;

@SuppressWarnings("restriction")
public interface VideoService
{
	VideoPage insertVideoPage(String url) throws MySQLIntegrityConstraintViolationException , NumberFormatException;
	
	List<VideoPage> findAblePage();
	
	boolean setVideoOpenAble(boolean openable , VideoPage videoPage);
	
	VideoPage getVideoPageByWebId(Integer webId);
	
	VideoUrl insert(VideoUrl videoUrl);
}
