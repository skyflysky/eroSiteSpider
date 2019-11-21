package sky.tool.spider.service;

import java.io.File;
import java.util.List;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import sky.tool.spider.entity.NovelPage;
import sky.tool.spider.entity.NovelUrl;

@SuppressWarnings("restriction")
public interface NovelService
{
	/**
	 * 添加小说列表页
	 * @param novelPage 小说列表页
	 * @return 被添加的小说列表页 
	 * @throws MySQLIntegrityConstraintViolationException 抛出这个异常代表webId重复 页面已经被添加过了
	 */
	NovelPage insertNovelPage(NovelPage novelPage) throws MySQLIntegrityConstraintViolationException;

	/**
	 * 找所有的没有被下载过或下载失败过的视频列表页
	 * @return
	 */
	List<NovelPage> findUnloadPage();

	/**
	 * 通过webId 找视频列表页
	 * @param webId
	 * @return
	 */
	NovelPage getNovelPageByWebId(Integer webId);
	
	/**
	 * 通过列表页找到详情页实体
	 * @param novelPage
	 * @return
	 */
	NovelUrl getNovelUrlByPage(NovelPage novelPage);

	/**
	 * 插入详情页 
	 * @param novelPage 列表页实体
	 * @param targetFile 详情页对应文件的硬盘位置
	 * @return
	 */
	NovelUrl novelUrlDownloed(NovelPage novelPage, File targetFile);
}
