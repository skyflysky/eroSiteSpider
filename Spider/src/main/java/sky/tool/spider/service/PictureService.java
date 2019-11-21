package sky.tool.spider.service;

import java.util.List;

import sky.tool.spider.dto.PicDto;
import sky.tool.spider.entity.PicPage;
import sky.tool.spider.entity.PicUrl;

public interface PictureService
{
	/**
	 * 插入新的图片详情
	 * @param picUrl
	 * @return
	 */
	PicUrl insertPicUrl(PicUrl picUrl);
	
	/**
	 * 获取所有未被下载的图片详情
	 * @return
	 */
	List<PicUrl> getUnLoadPicUrl();
	
	/**
	 * 标记当前id的图片详情已经被下载
	 * @param id 
	 * @param path 下载在本地的路径
	 * @return
	 */
	boolean markPicUrlDownloaded(Long id, String path);
	
	/**
	 * 获取指定列表页对应的已经下载的详情页集合
	 * @param id 列表页的id列表页的id列表页的id列表页的id列表页的id列表页的id列表页的id列表页的id列表页的id列表页的id列表页的id
	 * @return
	 */
	List<PicDto> getLoadedPicDto(Long id);

	/**
	 * 获取下一个列表页的对应的已经下载的详情页集合
	 * @param id 列表页的id列表页的id列表页的id列表页的id列表页的id列表页的id列表页的id列表页的id列表页的id列表页的id列表页的id
	 * @return
	 */
	List<PicDto> getNextPicDto(Long id);

	/**
	 * 将当前详情页标记为未下载
	 * @param id
	 */
	void delePhoto(Long id);
	
	/**
	 * 插入新的详情页
	 * @param picPage
	 * @return
	 * @throws MySQLIntegrityConstraintViolationException 抛出这个异常代表webId重复 
	 */
	@SuppressWarnings("restriction")
	PicPage insertPicPage(PicPage picPage) throws com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

	/**
	 * 
	 * @return
	 */
	List<PicPage> findAblePage();
	
	boolean setPicPageOpenAble(boolean openAble , PicPage picPage);
	
	int setPicPageCount(int picCount , PicPage picPage);
	
	PicPage getPicPageByWebId(Integer webId);
}
