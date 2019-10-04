package sky.tool.spider.service;

import java.util.List;

import sky.tool.spider.dto.PicDto;
import sky.tool.spider.entity.PicPage;
import sky.tool.spider.entity.PicUrl;

public interface PictureService
{
	PicUrl insertPicUrl(PicUrl picUrl);
	
	List<PicUrl> getUnLoadPicUrl();
	
	boolean markPicUrlDownloaded(Long id, String path);
	
	List<PicDto> getLoadedPicDto(Long id);

	List<PicDto> getNextPicDto(Long id);

	void delePhoto(Long id);
	
	@SuppressWarnings("restriction")
	PicPage insertPicPage(PicPage picPage) throws com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

	List<PicPage> findAblePage();
	
	boolean setPicPageOpenAble(boolean openAble , PicPage picPage);
	
	int setPicPageCount(int picCount , PicPage picPage);
	
	PicPage getPicPageByWebId(Integer webId);
}
